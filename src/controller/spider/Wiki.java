package controller.spider;

import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Wiki {
	private String searchWord;

	public Wiki(String _searchWord) {
		searchWord = _searchWord;
	}

	public String getHtml() {
		String html = "网络连接错误，请稍后再试。";
		try {
			String url = "http://baike.baidu.com/searchword/?word="
					+ URLEncoder.encode(searchWord, "gb2312")
					+ "&pic=1%sub=1&enc=gbk";

			Document doc = null;
			doc = Jsoup.connect(url).post();

			Element meta = doc.select("meta[http-equiv=Refresh]").first();
			String realUrl = "http://baike.baidu.com"
					+ meta.attr("content").substring(6);

			doc = Jsoup.connect(realUrl).post();
			Elements content = doc.select("div[class=content-bd main-body]");
			content.select("h1").remove();
			content.select("span[class=editable-lemma]").remove();

			Elements img = doc.select("img");
			for (Element e : img) {
				String src = e.attr("src");
				e.attr("src",controller.process.GotyaConst.AntiAntiLeech + src);
			}

			String wikibody = content.toString();
			if (wikibody.length() == 0) {
				wikibody = "该词条不存在。";
			}

			html = "<!DOCTYPE html><html>\n"
					+ "<head>\n"
					+ "<meta content=\"text/html; charset=gb2312\" http-equiv=\"Content-Type\"/>\n"
					+ "<meta content=\"IE=7\" http-equiv=\"X-UA-Compatible\"/>\n"
					+ "<script src=\"http://img.baidu.com/js/tangram-1.3.4.js\" type=\"text/javascript\"></script>\n"
					+ "<link href=\"http://baike.bdimg.com/css/bk.lemma.css\" rel=\"stylesheet\" type=\"text/cssscript src=\"http://baike.bdimg.com/js/bk.lemma.js\" type=\"text/javascript\"></script>\n"
					+ "<style type=\"text/css\"></style>\n" + "</head>\n"
					+ "<body id=\"view\">" + wikibody + "</body>\n" + "</html>";

		} catch (Exception e) {
			e.printStackTrace();
			controller.process.Log.printErr(e);
		}
		return html;
	}
}
