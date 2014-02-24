package org.ictclas4j.segment;

import java.util.*;
import org.ictclas4j.bean.Atom;
import org.ictclas4j.bean.Dictionary;
import org.ictclas4j.bean.MidResult;
import org.ictclas4j.bean.SegNode;
import org.ictclas4j.bean.SegResult;
import org.ictclas4j.bean.Sentence;
import org.ictclas4j.utility.POSTag;
import org.ictclas4j.utility.Utility;
import org.ictclas4j.bean.Data;

public class SegTag {
	private Dictionary coreDict;
	private Dictionary bigramDict;
	private PosTagger personTagger;
	private PosTagger transPersonTagger;
	private PosTagger placeTagger;
	private PosTagger lexTagger;

	private int segPathCount = 1;// 分词路径的数目

	public SegTag(int segPathCount) {
		this.segPathCount = segPathCount;
		coreDict = new Dictionary(controller.process.GotyaConst.dicPath+"coreDictOK.dct");
		
//		coreDict.addItem("雷娜图", 28160, 14);
//		coreDict.save("data\\coreDictOK.dct");
		
		bigramDict = new Dictionary(controller.process.GotyaConst.dicPath+"bigramDict.dct");
		personTagger = new PosTagger(Utility.TAG_TYPE.TT_PERSON, controller.process.GotyaConst.dicPath+"nr", coreDict);
		transPersonTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, controller.process.GotyaConst.dicPath+"tr", coreDict);
		placeTagger = new PosTagger(Utility.TAG_TYPE.TT_TRANS_PERSON, controller.process.GotyaConst.dicPath+"ns", coreDict);
		lexTagger = new PosTagger(Utility.TAG_TYPE.TT_NORMAL, controller.process.GotyaConst.dicPath+"lexical", coreDict);
	}

	public SegResult split(String src) {
		SegResult sr = new SegResult(src);// 分词结果
		String finalResult = null;

		if (src != null) {
			finalResult = "";
			int index = 0;
			String midResult = null;
			sr.setRawContent(src);
			SentenceSeg ss = new SentenceSeg(src);
			ArrayList<Sentence> sens = ss.getSens();
			
			for (Sentence sen : sens) {
				long start=System.currentTimeMillis();
				MidResult mr = new MidResult();
				mr.setIndex(index++);
				mr.setSource(sen.getContent());
				if (sen.isSeg()) {
					// 原子分词
					AtomSeg as = new AtomSeg(sen.getContent());
					ArrayList<Atom> atoms = as.getAtoms();
					mr.setAtoms(atoms); 
					println2Err("[atom time]:"+(System.currentTimeMillis()-start));
					start=System.currentTimeMillis();
					
					// 生成分词图表,先进行初步分词，然后进行优化，最后进行词性标记
					SegGraph segGraph = GraphGenerate.generate(atoms, coreDict);
					mr.setSegGraph(segGraph.getSnList());
					// 生成二叉分词图表
					SegGraph biSegGraph = GraphGenerate.biGenerate(segGraph, coreDict, bigramDict);
					
					mr.setBiSegGraph(biSegGraph.getSnList());
					println2Err("[graph time]:"+(System.currentTimeMillis()-start));
					start=System.currentTimeMillis();
					
					// 求N最短路径
					NShortPath nsp = new NShortPath(biSegGraph, segPathCount);
					ArrayList<ArrayList<Integer>> bipath = nsp.getPaths();
					mr.setBipath(bipath);
					println2Err("[NSP time]:"+(System.currentTimeMillis()-start));
					start=System.currentTimeMillis();
					
					for (ArrayList<Integer> onePath : bipath) {
						// 得到初次分词路径
						ArrayList<SegNode> segPath = getSegPath(segGraph, onePath);
						ArrayList<SegNode> firstPath = AdjustSeg.firstAdjust(segPath);
						String firstResult = outputResult(firstPath);
						mr.addFirstResult(firstResult);
						println2Err("[first time]:"+(System.currentTimeMillis()-start));
						start=System.currentTimeMillis();

						// 处理未登陆词，进对初次分词结果进行优化
						SegGraph optSegGraph = new SegGraph(firstPath);
						ArrayList<SegNode> sns = clone(firstPath);
						personTagger.recognition(optSegGraph, sns);
						transPersonTagger.recognition(optSegGraph, sns);
						placeTagger.recognition(optSegGraph, sns);
						mr.setOptSegGraph(optSegGraph.getSnList());
						println2Err("[unknown time]:"+(System.currentTimeMillis()-start));
						start=System.currentTimeMillis();

						// 根据优化后的结果，重新进行生成二叉分词图表
						SegGraph optBiSegGraph = GraphGenerate.biGenerate(optSegGraph, coreDict, bigramDict);
						mr.setOptBiSegGraph(optBiSegGraph.getSnList());

						// 重新求取N－最短路径
						NShortPath optNsp = new NShortPath(optBiSegGraph, segPathCount);
						ArrayList<ArrayList<Integer>> optBipath = optNsp.getPaths();
						mr.setOptBipath(optBipath);
						
						// 生成优化后的分词结果，并对结果进行词性标记和最后的优化调整处理
						ArrayList<SegNode> adjResult = null;
						for (ArrayList<Integer> optOnePath : optBipath) {
							ArrayList<SegNode> optSegPath = getSegPath(optSegGraph, optOnePath);
							lexTagger.recognition(optSegPath);
							String optResult = outputResult(optSegPath);
							mr.addOptResult(optResult);
							adjResult = AdjustSeg.finaAdjust(optSegPath, personTagger, placeTagger);
							String adjrs = outputResult(adjResult);
							println2Err("[last time]:"+(System.currentTimeMillis()-start));
							start=System.currentTimeMillis();
							if (midResult == null)
								midResult = adjrs;
							break;
						}
					}
					sr.addMidResult(mr);
				} else
					midResult = sen.getContent();
				finalResult += midResult;
				midResult = null;
			}

			sr.setFinalResult(finalResult);
		}
		return sr;
	}

	private ArrayList<SegNode> clone(ArrayList<SegNode> sns) {
		ArrayList<SegNode> result = null;
		if (sns != null && sns.size() > 0) {
			result = new ArrayList<SegNode>();
			for (SegNode sn : sns)
				result.add(sn.clone());
		}
		return result;
	}

	// 根据二叉分词路径生成分词路径
	private ArrayList<SegNode> getSegPath(SegGraph sg, ArrayList<Integer> bipath) {
		ArrayList<SegNode> path = null;

		if (sg != null && bipath != null) {
			ArrayList<SegNode> sns = sg.getSnList();
			path = new ArrayList<SegNode>();

			for (int index : bipath)
				path.add(sns.get(index));
		}
		return path;
	}

	// 根据分词路径生成分词结果
	private String outputResult(ArrayList<SegNode> wrList) {
		String result = null;
		String temp=null;
		char[] pos = new char[2];
		if (wrList != null && wrList.size() > 0) {
			result = "";
			for (int i = 0; i < wrList.size(); i++) {
				SegNode sn = wrList.get(i);
				if (sn.getPos() != POSTag.SEN_BEGIN && sn.getPos() != POSTag.SEN_END) {
					int tag = Math.abs(sn.getPos());
					pos[0] = (char) (tag / 256);
					pos[1] = (char) (tag % 256);
					temp=""+pos[0];
					if(pos[1]>0)
						temp+=""+pos[1];
					result += sn.getSrcWord() + "/" + temp + " ";
				}
			}
		}

		return result;
	}

	public void setSegPathCount(int segPathCount) {
		this.segPathCount = segPathCount;
	}
	
	private static void println2Err(String str) {
		//System.err.println(str);		
	}

	public static ArrayList<Data> segment(String content){
		SegTag segTag = new SegTag(1);
		ArrayList<Data> result=new ArrayList<Data>();
		if (content!=null) {
			try { 
				SegResult seg_res=segTag.split(content);
				//分词整理
				String tempresult=seg_res.getFinalResult();
				int start=0;
				int end=0;
				for(int i=0;i<tempresult.length();i++){
					while(tempresult.charAt(i)!='/'){
						i++;
					}
					end=i;
					String tempword=tempresult.substring(start, end);
					boolean mark=false;
					for(int j=0;j<result.size();j++){
						if(result.get(j).getWord().equals(tempword)){
							result.get(j).setFreq(result.get(j).getFreq()+1);
							mark=true;
							break;
						}
					}
					if(mark){
						while(tempresult.charAt(i)!=' '){
							i++;
						}
					}
					else{
						Data tempdata=new Data();
						tempdata.setWord(tempword.trim());
						i++;
						start=i;
						while(tempresult.charAt(i)!=' '){
							i++;
						}
						end=i;
						tempdata.setType(tempresult.substring(start, end));
						result.add(tempdata);
					}
					if(i==tempresult.length()-1){
						break;
					}
					else{
						i++;
						start=i;
						end=i;
					}
				}
				
				//删除常见的无用词
				for(int i=0;i<result.size();i++){					
					if (result.get(i).getWord().length()==1 
							|| result.get(i).getType().equals("m")
							|| result.get(i).getType().equals("t")
							|| result.get(i).getType().equals("v")
							|| result.get(i).getType().equals("a")
							|| result.get(i).getType().equals("w")
							|| result.get(i).getType().equals("ad")
							|| result.get(i).getType().equals("an")
							|| result.get(i).getType().equals("f")
							|| result.get(i).getType().equals("r")
							|| result.get(i).getType().equals("p")
							|| result.get(i).getType().equals("d")
							|| result.get(i).getType().equals("c")
							|| result.get(i).getType().equals("ad")
							|| result.get(i).getType().equals("q")
							|| result.get(i).getType().equals("u")
							|| result.get(i).getType().equals("vd")
							|| result.get(i).getType().equals("b")
							|| result.get(i).getType().equals("z")
							|| result.get(i).getType().equals("o")
							|| result.get(i).getType().equals("y")
							|| result.get(i).getType().equals("e")){
						result.remove(i);
						i--;
						continue;
					}else if (result.get(i).getType().equals("nr")){
						result.get(i).setFreq(result.get(i).getFreq()*controller.process.GotyaConst.nrWeight);
					}else if (result.get(i).getType().equals("nz")){
						result.get(i).setFreq(result.get(i).getFreq()*controller.process.GotyaConst.nzWeight);
					}else if (result.get(i).getType().equals("vn")){
						result.get(i).setFreq(result.get(i).getFreq()*controller.process.GotyaConst.vnWeight);
					}else if (result.get(i).getType().equals("nx")){
						if (result.get(i).getWord().indexOf(":")!=-1
								||result.get(i).getWord().indexOf("/")!=-1
								||result.get(i).getWord().indexOf("\\")!=-1
								||result.get(i).getWord().indexOf("~")!=-1
								||result.get(i).getWord().indexOf(".")!=-1
								||result.get(i).getWord().indexOf("*")!=-1
								||result.get(i).getWord().indexOf("_")!=-1
								||result.get(i).getWord().indexOf("&")!=-1
								||result.get(i).getWord().indexOf(";")!=-1
								||result.get(i).getWord().indexOf("<")!=-1
								||result.get(i).getWord().indexOf(">")!=-1){
							result.remove(i);
							i--;
							continue;
						}else{
							result.get(i).setFreq(result.get(i).getFreq()*controller.process.GotyaConst.nxWeight);
						}
					}
					
					for(int j=0;j<Utility.DELETE_WORDS.length;j++){
						if(result.get(i).getWord().equals(Utility.DELETE_WORDS[j])){
							result.remove(i);
							i--;
							break;
						}
					}
					
				}
			} 
			catch (Throwable t) {
				t.printStackTrace();
				controller.process.Log.printErr(t);
			}
		}
		return result;
	}

}
