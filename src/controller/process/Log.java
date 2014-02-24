package controller.process;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
	public static void print(String s) {
		try {
			FileWriter fw = new FileWriter(GotyaConst.logPath, true);

			Date date = new Date();
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String times = from.format(date) + " ";

			String printString = times + s + "\n";

			fw.write(printString, 0, printString.length());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.printErr(e);
		}
	}

	public static void printErr(Exception e) {
		try {
			FileWriter fw = new FileWriter(GotyaConst.logErrPath, true);

			Date date = new Date();
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String times = from.format(date) + " ";

			String printString = times + "\n" + e.toString() + "\n";

			fw.write(printString, 0, printString.length());
			fw.flush();
			fw.close();
		} catch (Exception e2) {

		}
	}

	public static void printErr(Throwable t) {
		try {
			FileWriter fw = new FileWriter(GotyaConst.logErrPath, true);

			Date date = new Date();
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String times = from.format(date) + " ";

			String printString = times + "\n" + t.toString() + "\n";

			fw.write(printString, 0, printString.length());
			fw.flush();
			fw.close();
		} catch (Exception e2) {

		}
	}

	public static void printErr(String s) {
		try {
			FileWriter fw = new FileWriter(GotyaConst.logErrPath, true);

			Date date = new Date();
			SimpleDateFormat from = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String times = from.format(date) + " ";

			String printString = times + "\n" + s + "\n";

			fw.write(printString, 0, printString.length());
			fw.flush();
			fw.close();
		} catch (Exception e2) {

		}
	}

	public static String createWikiHtml(String html, String md5) {
		try {
			FileWriter fw = new FileWriter(GotyaConst.wikiLocalPath + md5
					+ ".html", false);

			fw.write(html, 0, html.length());
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			Log.printErr(e);
		}
		return GotyaConst.wikiInternetPath + md5 + ".html";
	}
}
