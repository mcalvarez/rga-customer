package org.rga.customer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LoginUtils {
	public final static String decodeBase64(String text) {
		return new String(Base64.getDecoder().decode(text.getBytes()));
	}
	
	public final static String encodeBase64(String text) {
		return Base64.getEncoder().encodeToString(text.getBytes());
	}
	
	public final static String encodeSha256(String text) {
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			final byte[] digested = digest.digest(text.getBytes());
			
			return hexToString(digested);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final static String hexToString(byte[] hex) {
		final StringBuilder sb = new StringBuilder();
		
		for (byte b : hex) {
			sb.append(String.format("%02x", b & 0xff));
		}
		
		return sb.toString();
	}
}
