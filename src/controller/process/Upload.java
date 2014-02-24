package controller.process;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Upload {
	private byte[] content;
	private String filename;
	private String sufname;

	public Upload(byte[] _content, String _filename) {
		content = _content;
		filename = _filename;
		/**
		 * ��ȡ�ļ���׺��
		 */
		int i = filename.lastIndexOf('.');
		if (i != -1) {
			sufname = "." + filename.substring(i + 1).toLowerCase();
		} else {
			System.out.println("�ļ���׺������");
			Log.print("�ļ���׺������");
		}
	}

	public String uploadFile() throws Exception {
		try {
			/**
			 * �����ļ���Ϊ��ǰʱ��
			 */
			Date date = new Date();
			SimpleDateFormat from = new SimpleDateFormat("yyyyMMddHHmmss");
			String times = from.format(date);
			String times2 = times.substring(0, 4) + "-" + times.substring(4, 6)
					+ "-" + times.substring(6, 8) + " "
					+ times.substring(8, 10) + ":" + times.substring(10, 12)
					+ ":" + times.substring(12, 14);
			filename = times + sufname;

			/**
			 * ���ļ���д������
			 */
			File file = new File(GotyaConst.uploadLocalPath + filename);
			FileOutputStream stream = new FileOutputStream(file);
			if (content != null)
				stream.write(content);
			stream.close();
			return times2;
		} catch (Exception e) {
			e.printStackTrace();
			Log.printErr(e);
		}
		return null;
	}

	public String getFileName() {
		return filename;
	}
}
