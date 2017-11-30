package com.happ.test.test;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Test03 {
 
	

		
	
	
	public static void main(String[] args) throws IOException {
		  OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
		        .url("https://api.github.com/repos/square/okhttp/issues")
		        .header("User-Agent", "OkHttp Headers.java")
		        .addHeader("Accept", "application/json; q=0.5")
		        .addHeader("Accept", "application/vnd.github.v3+json")

		        .get()
		        .build();
	
		    Response response = client.newCall(request).execute();
		    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		 
		    System.out.println("Server: " + response.header("Server"));
		    System.out.println("Date: " + response.header("Date"));
		    System.out.println("Vary: " + response.headers("Vary"));
	}
}
