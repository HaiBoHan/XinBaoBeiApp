package com.xinbaobeijiaoyu.ceping.app;

public class Config {
	
	public static String PackageName = "com.xinbaobeijiaoyu.ceping";
	
	private static String HOST_NAME = "211.149.198.209";
//	private static String PORT = "8012";
//	private static String HOST_NAME = "192.168.2.102";
	private static String PORT = "8012";
	private static String FULL_URL = "http://" + HOST_NAME + ":" + PORT;
	
//	public static String API_LOGIN = FULL_URL + "/HttpPost.aspx?Method=GetUser";
//	
//	public static String API_AboutUs = FULL_URL + "/HttpPost.aspx?Method=GetAboutUs";
//	
//	public static String API_Ceping = "http://ceping.xinbaobeijiaoyu.com/Interface_login1.php?usertype=测试者&account=";
//
//	public static String API_Subjective = FULL_URL + "/HBHHttpPost.aspx?Method=GetSubQes_T";
	
//	public static String API_LOGIN = FULL_URL + "/HttpPost.aspx?Method=GetUser";
	public static String API_LOGIN = FULL_URL + "/HBHHttpPost.aspx?Method=GetUser";
	
	public static String API_AboutUs = FULL_URL + "/HBHHttpPost.aspx?Method=GetAboutUs";
	
	public static String API_Ceping = "http://ceping.xinbaobeijiaoyu.com/Interface_login1.php?usertype=测试者&account=";

	// GetSubQes_T
	public static String API_Subjective = FULL_URL + "/HBHHttpPost.aspx?Method=GetKeywordsQuestion";
	
	public static String API_Message = FULL_URL + "/HBHHttpPost.aspx?Method=GetMessage";
	
	public static String API_PostSubjective = FULL_URL + "/HBHHttpPost.aspx?Method=SaveSubjectivityQuestion";

	public static String API_Qinzi = FULL_URL + "/HBHhttpPost.aspx?Method=GetParentChildCurriculum";
	
	public static String URL_Qinzi = "http://www.xinbaobeijiaoyu.com/kecheng.php";

	// 关键字问题历史
	public static String API_QuestionHistory = FULL_URL + "/HBHHttpPost.aspx?Method=GetKeywordsHistory";
	
	// 修改用户
	public static String API_SaveUser = FULL_URL + "/HBHHttpPost.aspx?method=SaveUser";

	// 注册新用户	"http://10.10.10.161:8012"
	public static String API_UserRegister = FULL_URL + "/HBHHttpPost.aspx?method=UserRegister";
}
