package rest.util;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class Utils {

    public static void importCerts() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {

        InputStream certIn = Utils.class.getResourceAsStream("/ca-certificates.crt");
        final String passphrase="changeit";

        char separatorChar = File.separatorChar;
        File dir = new File(System.getProperty("java.home") + separatorChar + "lib" + separatorChar + "security");
        File file = new File(dir, "cacerts");
        InputStream localCertIn = new FileInputStream(file);

        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(localCertIn, passphrase.toCharArray());
        if (keystore.containsAlias("payfort")) {
            certIn.close();
            localCertIn.close();
            return;
        }
        localCertIn.close();

        BufferedInputStream bis = new BufferedInputStream(certIn);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        while (bis.available() > 0) {
            Certificate cert = cf.generateCertificate(bis);

            keystore.setCertificateEntry("payfort", cert);
        }

        certIn.close();

        OutputStream out = new FileOutputStream(file);
        keystore.store(out, passphrase.toCharArray());
        out.close();

    }

}