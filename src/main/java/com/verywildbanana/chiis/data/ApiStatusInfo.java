package com.verywildbanana.chiis.data;

import net.sf.json.JSONObject;

public class ApiStatusInfo {

	private String code;
	private String message;
	
	UpdateInfo upateaInfo = new UpdateInfo();

	public ApiStatusInfo(String c, String m) {

		this.code = c;
		this.message = m; 
	}

}
