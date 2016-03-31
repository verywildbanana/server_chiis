package com.verywildbanana.chiis;

public class Constants {

	
	public static final boolean RELEASE_BUILD = false;
	
	public final static String API_SUCCESS_CODE = "200.0000";
	public final static String API_ERROR_CODE_REQ_UPDTE_APP = "400.1111"; // 강제 업데이트 
	
	public final static String API_ERROR_CODE_TOTAL_1 = "401.0000"; // try catch error
	public final static String API_ERROR_CODE_DENTAL_1 = "401.0001"; // 이미 존재하는 아이디
	public final static String API_ERROR_CODE_DENTAL_2 = "404.0001"; // 치과 데이터 없음 
	public final static String API_ERROR_CODE_DENTAL_1_TXT = "이미 존재하는 아이디 입니다.";
	public final static String API_ERROR_CODE_DENTAL_2_TXT = "등록된 치과 정보 없습니다.";
	 
	
	public final static String required_version  = "1.0.0";
	public final static String update_url  = "https://play.google.com/store/apps/details?id=";
	
	
	public static String getFilePath() {
		
		if(RELEASE_BUILD) {
		
			return "//usr//local//server//tomcat//webapps//banana//img//";
			
		}
		else {
			
			return "//Users//HDlee//Downloads//java_server//img//";
			
		}
		
	}
	
	public static final String downloadBaseUrl = "http://221.143.21.149:8080/banana/img/";
	
}
