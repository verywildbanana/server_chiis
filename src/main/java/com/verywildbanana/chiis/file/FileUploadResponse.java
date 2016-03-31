package com.verywildbanana.chiis.file;

import java.net.URL;

import com.verywildbanana.chiis.data.ApiStatusInfo;

public class FileUploadResponse extends ApiStatusInfo{

	private URL location;
	
	public void setLocation(URL url) {
		
		this.location = url;
	}
	
	public URL getLocation() {
		return location;
	}
	
}
