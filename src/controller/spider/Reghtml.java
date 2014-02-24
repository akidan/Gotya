package controller.spider;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.regex.*;

public class Reghtml {
	public static ArrayList<PicInfo> spiderText(String html) {
		ArrayList<PicInfo> picVec = new ArrayList<PicInfo>();
		String nothing = new String("<p class=\"nothing\">");

		html = toEntity(html);
		if (html.indexOf(nothing) != -1) {
			// 没有相似图片
		} else {
			// 有相似图片
			Pattern div1 = Pattern.compile("thumbURL:\"");
			String picArr[] = div1.split(html);
			for (int i = 1; i < picArr.length; i++) {
				int k[] = new int[12];
				PicInfo temppic = new PicInfo();
				k[0] = picArr[i].indexOf("\",objURL:\"");
				k[1] = picArr[i].indexOf("\",curNum:");
				k[2] = picArr[i].indexOf(",objURLEnc:\"");
				k[3] = picArr[i].indexOf("\",fromURLEnc:\"");
				k[4] = picArr[i].indexOf("\",fromURLHost:\"");
				k[5] = picArr[i].indexOf("\",width:");
				k[6] = picArr[i].indexOf(",height:");
				k[7] = picArr[i].indexOf(",objId:");
				k[8] = picArr[i].indexOf(",objType:\"");
				k[9] = picArr[i].indexOf("\",fileSize:\"");
				k[10] = picArr[i].indexOf("\",fromPageTitle:\"");
				k[11] = picArr[i].indexOf("\",textHost:\"");

				temppic.setThumbURL(picArr[i].substring(0, k[0]));

				temppic.setCurNum(picArr[i].substring(k[1] + 9, k[2]));
				try {
					temppic.setObjURL(URLDecoder.decode(
							controller.process.GotyaConst.AntiAntiLeech
									+ picArr[i].substring(k[2] + 12, k[3])
											.trim(), "gb2312"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					controller.process.Log.printErr(e);
				}

				temppic.setFromURL(picArr[i].substring(k[3] + 14, k[4]));
				temppic.setFromURLHost(picArr[i].substring(k[4] + 15, k[5]));
				temppic.setWidth(picArr[i].substring(k[5] + 8, k[6]));
				temppic.setHeight(picArr[i].substring(k[6] + 8, k[7]));
				temppic.setType(picArr[i].substring(k[8] + 10, k[9]));
				temppic.setFileSize(picArr[i].substring(k[9] + 12, k[10]));
				temppic.setFromPageTitle(picArr[i].substring(k[10] + 17, k[11])
						.trim());
				temppic.setTextHost(picArr[i].substring(k[11] + 12,
						picArr[i].length() - 7).trim()
						+ "...");
				// temppic.setCurPage
				int t = temppic.getObjURL().lastIndexOf('/');
				temppic.setPicName(temppic.getObjURL().substring(t + 1));

				picVec.add(temppic);
			}
		}
		return picVec;
	}

	public static ArrayList<PicInfo> recognizeText(String html) {
		ArrayList<PicInfo> picVec = new ArrayList<PicInfo>();
		String nothing = new String("<p class=\"nothing\">");

		html = toEntity(html);
		if (html.indexOf(nothing) != -1) {
			// 没有相似图片
		} else {
			// 有相似图片
			Pattern div1 = Pattern.compile("\"thumbURL\":");
			String picArr[] = div1.split(html);
			for (int i = 1; i < picArr.length; i++) {
				int k[] = new int[12];
				PicInfo temppic = new PicInfo();
				k[0] = picArr[i].indexOf("\",\"pageNum\":");
				k[1] = picArr[i].indexOf(",\"objURL\":");
				k[2] = picArr[i].indexOf("\",\"fromURL\":");
				k[3] = picArr[i].indexOf("\",\"fromURLHost\":");
				k[4] = picArr[i].indexOf("\",\"currentIndex\":");
				k[5] = picArr[i].indexOf("\",\"width\":");
				k[6] = picArr[i].indexOf(",\"height\":");
				k[7] = picArr[i].indexOf(",\"type\":");
				k[8] = picArr[i].indexOf("\",\"filesize\":");
				k[9] = picArr[i].indexOf("\",\"bdSrcType\":");
				k[10] = picArr[i].indexOf(",\"fromPageTitle\":");
				k[11] = picArr[i].indexOf("\",\"token\":");

				temppic.setThumbURL(picArr[i].substring(1, k[0]).trim());
				try {
					temppic
							.setObjURL(URLDecoder
									.decode(
											(controller.process.GotyaConst.AntiAntiLeech + picArr[i]
													.substring(k[1] + 11, k[2])
													.trim()), "gb2312"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					controller.process.Log.printErr(e);
				}
				temppic.setFromURL(picArr[i].substring(k[2] + 13, k[3]).trim());
				temppic.setFromURLHost(picArr[i].substring(k[3] + 17, k[4])
						.trim());

				temppic.setWidth(picArr[i].substring(k[5] + 10, k[6]));
				temppic.setHeight(picArr[i].substring(k[6] + 10, k[7]));
				temppic.setType(picArr[i].substring(k[7] + 9, k[8]));
				temppic.setFileSize(picArr[i].substring(k[8] + 14, k[9]));

				// 2011.3.29 去除错误的strong标签
				String s = picArr[i].substring(k[10] + 19, k[11]).trim();
				s = s.replaceAll("</strong>", "</b></font>").replaceAll(
						"[<]?strong>", "<font color=\"#044A85\"><b>");
				temppic.setFromPageTitle(s);

				int t = temppic.getObjURL().lastIndexOf('/');
				temppic.setPicName(temppic.getObjURL().substring(t + 1));

				picVec.add(temppic);
			}
		}
		return picVec;
	}

	/**
	 * 转义符转化为实体符号
	 * 
	 * @param html
	 * @return
	 */
	public static String toEntity(String html) {
		html = html.replaceAll("&lt;", "<"); // 或&#60;
		html = html.replaceAll("&gt;", ">"); // 或&#62;
		html = html.replaceAll("&amp;", "&"); // 或&#38;
		html = html.replaceAll("&quot;", "\""); // 或&#34;
		html = html.replaceAll("&#91;", "[");
		html = html.replaceAll("&#93;", "]");
		html = html.replaceAll("&#160;", " "); // 或&nbsp;
		html = html.replaceAll("&nbsp;", " ");
		// 以下为保留转义符,为了提高程序效率,对不常用的符号没有放入检索
		/*
		 * String regHtmlEx1 = "&reg;";//R 已注册 String regHtmlEx2 = "&copy;";//C
		 * 版权 String regHtmlEx3 = "&trade;";//T 商标 String regHtmlEx4 =
		 * "&ensp;";//半个空白位 String regHtmlEx5 = "&emsp;";//一个空白位 String
		 * regHtmlEx6 = "&nbsp;";//不断行的空白
		 */
		return html;
	}
}
