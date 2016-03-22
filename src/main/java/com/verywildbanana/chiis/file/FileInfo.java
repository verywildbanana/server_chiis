package com.verywildbanana.chiis.file;

import java.net.URL;
import java.nio.file.Path;

public class FileInfo {

private URL downloadUrl;
	
	private Path filesystemPath;
	
	public URL getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(URL downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	
	public Path getFilesystemPath() {
		return filesystemPath;
	}

	public void setFilesystemPath(Path filesystemPath) {
		this.filesystemPath = filesystemPath;
	}
}
