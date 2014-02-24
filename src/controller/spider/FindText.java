package controller.spider;

import org.jsoup.nodes.Element;

public class FindText {
	
	public static String above(Element ele) {
		String text = new String();
		if (ele.select("a[href]").size()!=0){
			text="";
		}else {
			text = ele.text();
		}
		for (int i=ele.children().size()-1;i>=0;i--){
			text = above(ele.children().get(i));
			if (!text.equals("")) {
				break;
			}
		}
		if (text.equals("")) {
			if (ele.previousElementSibling() != null) {
				ele = ele.previousElementSibling();
			} else {
				while (ele.parent().previousElementSibling() == null
						&& (ele.select("html").size()==0)) {
					ele = ele.parent();
				}
				if (ele.select("html").size()!=0){
					return text;
				}
				ele = ele.parent().previousElementSibling();
			}
			text = above(ele);
		}
		return text;
	}

	public static String follow(Element ele) {
		String text = new String();
		if (ele.select("a[href]").size()!=0){
			text="";
		}else {
			text = ele.text();
		}
		for (Element child : ele.children()) {
			if (child.select("a[href]").size()!=0){
				text="";
				continue;
			}
			text = follow(child);
			if (!text.equals("")) {
				break;
			}
		}
		if (text.equals("")) {
			if (ele.nextElementSibling() != null) {
				ele = ele.nextElementSibling();
			} else {
				while (ele.parent().nextElementSibling() == null
						&& (ele.select("html").size()==0)) {
					ele = ele.parent();
				}
				if (ele.select("html").size()!=0){
					return text;
				}
				ele = ele.parent().nextElementSibling();
				
			}
			text = follow(ele);
		}
		return text;
	}
}
