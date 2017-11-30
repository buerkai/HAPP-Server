package com.happ.webcore.base.net;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by hyc on 17/1/4.
 */

public class HX509TrustManager implements X509TrustManager {

    private X509TrustManager mStandardTrustManager;

    public HX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
        super();
        TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        factory.init(keystore);
        TrustManager[] trustmanagers = factory.getTrustManagers();
        if (trustmanagers.length == 0) {
            throw new NoSuchAlgorithmException("no trust manager found");
        }
        this.mStandardTrustManager = (X509TrustManager) trustmanagers[0];
    }


    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
        mStandardTrustManager.checkClientTrusted(x509Certificates, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
        try {
            if ((x509Certificates != null) && (x509Certificates.length == 1)) {
                x509Certificates[0].checkValidity();
            } else {
                mStandardTrustManager.checkServerTrusted(x509Certificates, authType);
            }
        } catch (CertificateException e) {
            if (e.getCause() instanceof CertPathValidatorException || e instanceof CertificateExpiredException) {
                return;
            }
            throw e;
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
