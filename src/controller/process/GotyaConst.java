package controller.process;

public class GotyaConst {
	/**
	 * Gotya���ȫ�ֱ���
	 */
	// public static int segThread = 0;
	public static String wordSearchPath = "http://image.baidu.com/i?ct=201326592&s=0&cl=2&lm=-1&tn=baiduimage&z=0&pv=&word=";
	

	/**
	 * Upload��ĳ���
	 */
	// �����ڱ���/3G
	// public static String uploadLocalPath="D:\\Workspaces\\Java\\.metadata\\.me_tcat\\webapps\\Gotya\\upload\\";

	// �����ڱ���
	// public static String uploadTestInternetPath="http://gotya.buaa3806.com/test/i9000.jpg";

	// ������3G
	// public static String uploadInternetPath="http://1.203.239.48/Gotya/upload/";

	// ������Linux
	public static String uploadLocalPath = "/opt/tomcat/webapps/Gotya/upload/";
	public static String uploadInternetPath = "http://gotya.buaa3806.com/upload/";

	/**
	 * Spider��ĳ���
	 */
	public static String spiderTenPics = "http://stu.baidu.com/i?ct=1&rn=1&tn=baiduimage&rt=0&objurl=";
	public static String tutuHost = "http://tutu.baidu.com";
	public static String spiderAnyPics = "http://stu.baidu.com/i?ct=3";
	public static String spiderAnyPics2 = "&tn=baiduimage&rt=0&sign=";
	public static String showPicIndex = "0";
	public static String showPicNum = "30";
	public static int trySpider = 3;

	/**
	 * DeepSpider��ĳ���
	 */
	public static long maxMillsWaitDeepSpider = 10000;

	/**
	 * Segment��ĳ���
	 */
	public static float titleWeight = 0.8f;
	public static float titleNewsWeight = 1.0f;
	public static float aboveTextWeight = 0.6f;
	public static float followTextWeight = 0.85f;
	public static float followCenterWeight = 1.0f;
	public static float nrWeight = 2.5f;//ר������
	public static float nzWeight = 2.5f;//�̱�Ʒ��
	public static float nxWeight = 1.9f;//Ӣ��
	public static float vnWeight = 0.8f;//���ʶ���
	/**
	 * SegTag��ĳ���
	 */
	// �����ڱ���/3G
	// public static String dicPath="D:\\Workspaces\\Java\\.metadata\\.me_tcat\\webapps\\Gotya\\WEB-INF\\classes\\Data\\";

	// ������Linux
	public static String dicPath = "/opt/tomcat/webapps/Gotya/WEB-INF/classes/Data/";

	/**
	 * Recognize��ĳ���
	 */
	public static String recognizePath = "http://image.baidu.com/i?ct=201326592&s=0&cl=1&lm=-1&tn=baiduimage&z=0&pv=&word=";
	public static int searchWordsNum = 4;
	public static String rcnPicIndex = "0";
	public static String rcnPicNum = "50";
	public static String AntiAntiLeech = "http://www.buaa3806.com/showPic.php?website=gotya&picurl=";

	/**
	 * DBConnect��ĳ���
	 */
	public static String jdbcURL = "jdbc:mysql://localhost:3306/gotya?useUnicode=true&characterEncoding=utf-8";
	public static String sqlUser = "root";
	//�����ڱ���/3G
	// public static String sqlPassword = "123456";
	
	//������Linux
	public static String sqlPassword = "zhaolong";

	/**
	 * Log��ĳ���
	 */
	// �����ڱ���/3G
	// public static String logPath="D:\\Workspaces\\Java\\.metadata\\.me_tcat\\webapps\\Gotya\\log\\run.log";
	// public static String logErrPath="D:\\Workspaces\\Java\\.metadata\\.me_tcat\\webapps\\Gotya\\log\\err.log";

	// ������Linux
	public static String logPath = "/opt/tomcat/webapps/Gotya/log/log.txt";
	public static String logErrPath = "/opt/tomcat/webapps/Gotya/log/logErr.txt";
	public static String wikiLocalPath = "/opt/tomcat/webapps/Gotya/wiki/";
	public static String wikiInternetPath = "http://gotya.buaa3806.com/wiki/";
	
	/**
	 * Invite��ı���
	 */
	// �����ڱ���/3G,�ݲ�ȡ��
	public static String inviteCodePath = "D:\\Workspaces\\Java\\.metadata\\.me_tcat\\webapps\\Gotya\\WEB-INF\\classes\\Data\\invitation.code";
}
