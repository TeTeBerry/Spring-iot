package com.iot.smart.water.meter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

/**
 * MD5、SHA、SHA1、SHA-256、SHA-512加密
 */
public class HashUtil {

	private static final int DEFAULT_BUFF_LENGTH = 1024 * 16;

	private static final String[] hexDigits = {
			"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String hex(byte[] bytes) {
		StringBuilder builder = new StringBuilder();
		for (byte aByte : bytes) {
			builder.append(hex(aByte));
		}
		return builder.toString();
	}

	private static String hex(byte b) {
		return hexDigits[(b >> 4 & 0xF)] + hexDigits[(b & 0xF)];
	}

	public static class Calc {

		final String code;

		Calc(String code) {
			this.code = code;
		}

		
		public String get(byte[] data) {
			try {
				return hex(MessageDigest.getInstance(code).digest(data));
			} catch (Throwable e) {
				return null;
			}
		}

		public String get(String data) {
			return get(data.getBytes());
		}

		public String get(File data) {
			FileInputStream input = null;
			try {
				input = new FileInputStream(data);
				return get(input);
			} catch (Throwable e) {
				return null;
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (Throwable e) {
				}
			}
		}

		public String get(File data, long offset, long length) {
			RandomAccessFile raf = null;
			try {
				raf = new RandomAccessFile(data, "r");
				raf.seek(offset);
				MessageDigest md = MessageDigest.getInstance(code);
				byte[] buffer = new byte[DEFAULT_BUFF_LENGTH];
				int numRead;
				long totalRead = 0L;
				while ((numRead = raf.read(buffer)) != -1) {
					totalRead += numRead;
					if (totalRead <= length) { // normal
						md.update(buffer, 0, numRead);
					} else {
						md.update(buffer, 0, numRead - (int)(totalRead - length));
						break;
					}
				}
				return hex(md.digest());
			} catch (Throwable e) {
				return null;
			} finally {
				try {
					if (raf != null) {
						raf.close();
					}
				} catch (Throwable e) {
				}
			}
		}

		public String get(InputStream input) {
			try {
				MessageDigest md = MessageDigest.getInstance(code);
				byte[] buffer = new byte[DEFAULT_BUFF_LENGTH];
				int numRead;
				while ((numRead = input.read(buffer)) != -1) {
					md.update(buffer, 0, numRead);
				}
				return hex(md.digest());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public static final Calc MD5 = new Calc("MD5");
	public static final Calc SHA = new Calc("SHA");
	public static final Calc SHA1 = new Calc("SHA1");
	public static final Calc SHA256 = new Calc("SHA-256");
	public static final Calc SHA512 = new Calc("SHA-512");
}