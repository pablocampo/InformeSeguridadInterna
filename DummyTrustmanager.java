/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package informeseguridadinterna;

 
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
 
import javax.net.ssl.X509TrustManager;
 
public class DummyTrustmanager implements X509TrustManager {
	  public void checkClientTrusted(X509Certificate[] cert, String string) throws CertificateException
	  {
	  }
	  public void checkServerTrusted(X509Certificate[] cert, String string) throws CertificateException
	  {
	  }
	  public X509Certificate[] getAcceptedIssuers()
	  {
	  return new java.security.cert.X509Certificate[0];
	  }
 
}