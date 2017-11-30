package com.happ.test.test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class Test04 {

	public static void main(String[] args) {
		doShell("pwd");
	}

	public static void doShell(String cmd) {
		try {
			Process p=Runtime.getRuntime().exec(cmd);
		   InputStream is=	p.getInputStream();
		   byte[] buffer=new byte[1024];
		   int len=0;
		   ByteArrayOutputStream bos=new ByteArrayOutputStream();
		   while((len=is.read(buffer))>0) {
			   bos.write(buffer, 0, len);
		   }
		   System.out.println(new String(bos.toByteArray(),"utf-8"));
		}catch (Exception e) {
			e.getMessage();
		}
	}
}
