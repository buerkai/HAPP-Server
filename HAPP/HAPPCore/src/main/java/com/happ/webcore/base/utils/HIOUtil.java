package com.happ.webcore.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import com.happ.webcore.base.conf.HAPPConstance;

public class HIOUtil {

	public static void close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static byte[] readByteFromInputStream(InputStream input) {
		try {
			int len = 0;
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = input.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static long readFromInputStream(InputStream input, String charset, StringBuffer outBuffer) {
		try {
			int len = 0;
			long count = 0;
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = input.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
				count += len;
			}
			outBuffer.append(new String(bos.toByteArray(), charset));
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public static String readFromInputStream(InputStream input) {
		return readFromInputStream(input, HAPPConstance.UTF8);
	}

	public static String readFromInputStream(InputStream input, String charset) {
		try {
			int len = 0;
			byte[] buffer = new byte[4096];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while ((len = input.read(buffer)) > 0) {
				bos.write(buffer, 0, len);
			}
			return new String(bos.toByteArray(), charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
