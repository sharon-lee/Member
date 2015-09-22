package com.webapp.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Password {
	static Log log = LogFactory.getLog(Password.class);
	
	/*
	 * 단방향 암호화 (암호화) 			==> MD5, SHA-256 (Password)
	 * 양방향 암호화 (암호화, 복호화) 	==> AES-256 (개인정보)
	 */
	static public String encode(String password) {
		StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest message = MessageDigest.getInstance("SHA-256");//256bit
			//MessageDigest message = MessageDigest.getInstance("MD5");
			byte[] digest = message.digest(password.getBytes());
			Arrays.toString(digest);
			log.info("digest bytes = " + digest.length);//32byte
			
			for (byte b : digest) {
				//System.out.printf("%02x", digest[i]); //Test용
				buffer.append(String.format("%02X", b));
			}
			System.out.println();
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("[" + password + "] => [" + buffer.toString() + "]");
		
		return buffer.toString();
		
	}

	public static void main(String[] args) {
		System.out.println(encode("1234567890abcdefgyyy"));
		System.out.println(encode("1234567890abcdefgyy"));
		System.out.println(encode("1234567890abcdefgy"));
		
	}
}
