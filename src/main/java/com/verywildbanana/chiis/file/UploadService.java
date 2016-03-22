package com.verywildbanana.chiis.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import org.abstractj.kalium.encoders.Encoder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.verywildbanana.chiis.Constants;


@Service("uploadService")
public class UploadService {
	
	Logger log = Logger.getLogger(this.getClass());
	
	private SecureRandom rng = new SecureRandom();

	public FileInfo getFile(String fileId) {
		Path savePath = Paths.get(fileId);
		Path absSavePath = getAbsSavePath(savePath);
		URL downloadUrl = getDownloadPath(Paths.get(fileId));


		FileInfo result = new FileInfo();
		result.setFilesystemPath(absSavePath);
		result.setDownloadUrl(downloadUrl);

		return result;
	}


	public FileInfo saveFile(InputStream input) throws IOException {
		String newFileId = generateFileId();
		Path savePath = Paths.get(newFileId);
		Path absSavePath = getAbsSavePath(savePath);
		URL downloadUrl = getDownloadPath(Paths.get(newFileId));

		Files.copy(input, absSavePath);

		log.info("File uploaded to absSavePath  " + absSavePath);
		log.info("File uploaded to downloadUrl " + downloadUrl.toString());

		FileInfo result = new FileInfo();
		result.setFilesystemPath(absSavePath);
		result.setDownloadUrl(downloadUrl);

		return result;
	}

	private Path getAbsSavePath(Path savePath) {
		Path path = Paths.get(getUploadDirectory().toString(), savePath.toString());
		new File(path.getParent().toString()).mkdirs();

		return path;
	}

//	private Path getSavePath(String fileId) {
//		String d1 = fileId.substring(0, 2);
//		String d2 = fileId.substring(2, 4);
//		String d3 = fileId.substring(4);
//
//		return Paths.get(d1, d2, d3);
//	}

	private URL getDownloadPath(Path savePath) {
		
		URL downloadBaseUrl = null;
		
		try {
			downloadBaseUrl = new URL(Constants.downloadBaseUrl);
			return new URL(downloadBaseUrl, savePath.toString());
		} catch(MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	private String generateFileId() {
		byte[] bytes = new byte[32];
		rng.nextBytes(bytes);
		return Encoder.HEX.encode(bytes);
	}

	public Path getUploadDirectory() {
		
		File dir = new File(Constants.getFilePath());
		
		if( !dir.isDirectory()) {
			
			if(!dir.canWrite()) {
				throw new IllegalArgumentException("Upload directory is not writable");
			}
			else {
				
				dir.mkdir();	
			}
		}

		return Paths.get(Constants.getFilePath());
	}

}