package me.sirgregg.osubot.util.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileDownloader {
	public static String downloadFromUrl(URL url, String localFileName) throws IOException {
		InputStream is = null;
		FileOutputStream fos = null;

		String tempDir = System.getProperty("java.io.tmpdir");
		String outputPath = tempDir + "/" + localFileName;

		try {
			URLConnection connection = url.openConnection();

			is = connection.getInputStream();
			fos = new FileOutputStream(outputPath);

			byte[] buffer = new byte[4096]; // 4 kb
			int length;

			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer,0, length);
			}

			return outputPath;
		} finally {
			try {
				if (is != null) is.close();
			} finally {
				if (fos != null) fos.close();
			}
		}
	}
}
