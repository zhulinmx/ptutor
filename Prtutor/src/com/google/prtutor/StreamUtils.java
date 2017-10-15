package com.google.prtutor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	public static String readStream(InputStream is) {
		try {
			byte[] bytes = readInputStream(is);
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static byte[] readInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		return baos.toByteArray();
	}
}
