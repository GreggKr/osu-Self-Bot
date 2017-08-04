package me.sirgregg.osubot.util.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

public class URLUtil {
	public static String readURL(String strUrl) {
		BufferedReader bufferedReader = null;
		try {
			URL url = new URL(strUrl);
			bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuffer stringBuffer = new StringBuffer();

			int reading;
			char[] chars = new char[1024];

			while((reading = bufferedReader.read(chars)) != -1) {
				stringBuffer.append(chars, 0, reading);
			}

			return stringBuffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String sanatize(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
