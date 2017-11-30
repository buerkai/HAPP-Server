package com.happ.webcore.base.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import java.net.Socket;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.happ.webcore.base.exception.HException;

/**
 * Created by hyc on 17/1/4.
 */

public class HSSLSocketFactory extends SSLSocketFactory {

	private final static String CERTIFACATE_ALGORITHM = "PKCS12";
	private final static String CERTIFICATE_FORMAT = "X509";
	private final static String TLS_PROTOCAL_NAME = "TLS";

	private SSLContext mSslcontext;

	public HSSLSocketFactory(SSLContext sslContext) {
		this.mSslcontext = sslContext;
	}

	public HSSLSocketFactory() {
		try {
			SSLContext sslContext = SSLContext.getInstance(TLS_PROTOCAL_NAME);
			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] x509Certificates, String s)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(X509Certificate[] x509Certificates, String s)
						throws CertificateException {

				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			mSslcontext = sslContext;
		} catch (Exception e) {
			throw new HException(-2, e.getMessage());
		}
	}

	public HSSLSocketFactory(String p12path, String passwd) throws HException {
		InputStream certificateFileStream = null;
		try {
			certificateFileStream = CERTIFICATE_FORMAT.getClass().getResourceAsStream(p12path);
			if (null == certificateFileStream) {
			}
			KeyStore keyStore = KeyStore.getInstance(CERTIFACATE_ALGORITHM);
			keyStore.load(certificateFileStream, passwd != null ? passwd.toCharArray() : null);
			//
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(CERTIFICATE_FORMAT);
			keyManagerFactory.init(keyStore, passwd != null ? passwd.toCharArray() : null);
			SSLContext sslContext = SSLContext.getInstance(TLS_PROTOCAL_NAME);
			sslContext.init(keyManagerFactory.getKeyManagers(), new TrustManager[] { new HX509TrustManager(keyStore) },
					new SecureRandom());
			mSslcontext = sslContext;
		} catch (Exception e) {
			throw new HException(-2, e.getMessage());
		} finally {
			if (certificateFileStream != null) {
				try {
					certificateFileStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public Socket createSocket(String s, int i) throws IOException {
		return mSslcontext.getSocketFactory().createSocket(s, i);
	}

	@Override
	public Socket createSocket(String s, int i, InetAddress inetAddress, int i1) throws IOException {
		return mSslcontext.getSocketFactory().createSocket(s, i, inetAddress, i1);
	}

	@Override
	public Socket createSocket(InetAddress inetAddress, int i) throws IOException {
		return mSslcontext.getSocketFactory().createSocket(inetAddress, i);
	}

	@Override
	public Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress1, int i1) throws IOException {
		return mSslcontext.getSocketFactory().createSocket(inetAddress, i, inetAddress1, i1);
	}

	@Override
	public String[] getDefaultCipherSuites() {
		return mSslcontext.getSocketFactory().getDefaultCipherSuites();
	}

	@Override
	public String[] getSupportedCipherSuites() {
		return mSslcontext.getSocketFactory().getSupportedCipherSuites();
	}

	@Override
	public Socket createSocket(Socket socket, String s, int i, boolean b) throws IOException {
		return mSslcontext.getSocketFactory().createSocket(socket, s, i, b);
	}

	
	/***
	 * https单向认证
	 * 
	 * @return
	 */
	public static boolean initHttps() {
		try {
			HttpsURLConnection.setDefaultSSLSocketFactory(new HSSLSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/***
	 * https双向认证
	 * 
	 * @param context
	 * @param p12RawId
	 * @param passwd
	 * @return
	 */
	public static boolean initHttps(String p12path, String passwd) {
		try {
			HttpsURLConnection.setDefaultSSLSocketFactory(new HSSLSocketFactory(p12path, passwd));
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String s, SSLSession sslSession) {
					return true;
				}
			});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
