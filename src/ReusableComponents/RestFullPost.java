package ReusableComponents;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.*;
import javax.xml.transform.stream.StreamSource;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.soap.MimeHeaders;
import org.apache.xmlbeans.impl.soap.SOAPBody;
import org.apache.xmlbeans.impl.soap.SOAPPart;

public class RestFullPost {
	
	//public static String authSwithMode,authKey;

	// http://localhost:8080/RESTfulExample/json/product/post
	public static String callPost(String xml, String TC_ID, String path, Logger log, String authSwithMode,
			String authKey) {

		try {

			/*
			 * ReadPropFile properties=new
			 * ReadPropFile("Environment.properties");
			 */
			ExcelUtilities.setExcelFile(path, "Environment", log);
			String URL = ExcelUtilities.getTcId(1, 1, log);
			/*
			 * authSwithMode=ExcelUtilities.getTcId(4, 2, log); System.out.println(
			 * "SSO Switch is in "+authSwithMode+" state......................"
			 * ); log.info("SSO Switch is in "+authSwithMode+
			 * " state......................");
			 * if(authSwithMode.toUpperCase().equals("ON")){ System.out.println(
			 * "Fetching Authentication key.............."); log.info(
			 * "Fetching Authentication key..............");
			 * authKey=ExcelUtilities.getTcId(4, 1, log); System.out.println(
			 * "Key Fetched......"); log.info("Key Fetched......"); }
			 */
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");			
			conn.setRequestProperty("Content-Type", "application/xml");
			if (authSwithMode.toUpperCase().equals("ON")) {
				conn.setRequestProperty("Authorization", "Bearer " + authKey);
			}
			String input = xml;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			 * throw new RuntimeException("Failed : HTTP error code : " +
			 * conn.getResponseCode()); }
			 */
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output, response;
				System.out.println("Output from Server .... \n");
				log.info("Output from Server .... \n");
				String statusErr = Integer.toString(conn.getResponseCode());
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
				while ((output = br.readLine()) != null) {
					response = output;
					return response;
				}

				conn.disconnect();
			} catch (IOException ioe) {
				System.out.println(ioe);
				log.error(ioe);

			}
			String statusErr = Integer.toString(conn.getResponseCode());
			System.out.println("Status is" + statusErr);
			log.info("Status is" + statusErr);
			ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
		} catch (Exception e) {

			System.out.println(e);
			log.error(e);

		}
		return "";

	}
	
	public static String callPostSSL(String xml, String TC_ID, String path, Logger log, String authSwithMode,
			String authKey) {

		try {

			/*
			 * ReadPropFile properties=new
			 * ReadPropFile("Environment.properties");
			 */
			ExcelUtilities.setExcelFile(path, "Environment", log);
			String URL = ExcelUtilities.getTcId(1, 1, log);
			/*
			 * authSwithMode=ExcelUtilities.getTcId(4, 2, log); System.out.println(
			 * "SSO Switch is in "+authSwithMode+" state......................"
			 * ); log.info("SSO Switch is in "+authSwithMode+
			 * " state......................");
			 * if(authSwithMode.toUpperCase().equals("ON")){ System.out.println(
			 * "Fetching Authentication key.............."); log.info(
			 * "Fetching Authentication key..............");
			 * authKey=ExcelUtilities.getTcId(4, 1, log); System.out.println(
			 * "Key Fetched......"); log.info("Key Fetched......"); }
			 */
			
			// Create a trust manager that does not validate certificate chains
	        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
	                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	                public void checkClientTrusted(X509Certificate[] certs, String authType) {
	                }
	                public void checkServerTrusted(X509Certificate[] certs, String authType) {
	                }
	            }
	        };
	 
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	 
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			URL url = new URL(URL);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			((HttpURLConnection) conn).setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			//conn.setRequestProperty("Content-Type", "application/xml");
			if (authSwithMode.toUpperCase().equals("ON")) {
				conn.setRequestProperty("Authorization", "Bearer " + authKey);
			}
			String input = xml;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			 * throw new RuntimeException("Failed : HTTP error code : " +
			 * conn.getResponseCode()); }
			 */
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));				
				String output, response;
				System.out.println("Output from Server .... \n");
				log.info("Output from Server .... \n");
				String statusErr = Integer.toString(((HttpURLConnection) conn).getResponseCode());
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
				while ((output = br.readLine()) != null) {
					response = output;
					return response;
				}

				((HttpURLConnection) conn).disconnect();
			} catch (IOException ioe) {
				System.out.println(ioe);
				log.error(ioe);

			}
			String statusErr = Integer.toString(((HttpURLConnection) conn).getResponseCode());	
			System.out.println("Status is" + statusErr);
			log.info("Status is" + statusErr);
			ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
			
		} catch (Exception e) {

			System.out.println(e);
			log.error(e);

		}
		return "";

	}
	
	
	public static String callPostSSLNew(String xml, String TC_ID, String path, Logger log, String authSwithMode,
			String authKey) {
		
		
		
		try {

			/*
			 * ReadPropFile properties=new
			 * ReadPropFile("Environment.properties");
			 * 
			 */
			
			ExcelUtilities.setExcelFile(path, "Environment", log);
			String URL = ExcelUtilities.getTcId(1, 1, log);
			URL url = new URL(URL);
			
			//KeyStore ksCACert = KeyStore.getInstance("JKS");
			String password = "soa.test";
			//File pKeyFile = new File("C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks");
			String pKeyFile="C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks";
			//InputStream keyInput = new FileInputStream(pKeyFile);
			/// SSLContext sslContext = SSLContext.getInstance("SSL");
			 SSLContext sslContext = SSLContext.getInstance("TLS");
		        char ksPassword[] = password.toCharArray();
		      
		            KeyStore ks = KeyStore.getInstance("JKS");
		            ks.load(new FileInputStream(new File(pKeyFile)), ksPassword);

		            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
		            kmf.init(ks, ksPassword);
		            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		            tmf.init(ks);

		            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
		            //SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
		            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
		        
					
					/*
					ksCACert.load(keyInput, password.toCharArray());
					KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
					TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
					
					kmf.init(ksCACert,password.toCharArray());
					tmf.init(ksCACert);
					SSLContext context = SSLContext.getInstance("SSL");
					//TrustManager[] trustManagers = tmf.getTrustManagers();
					TrustManager[] trustManagers = new TrustManager[] {new X509TrustManager() {
		                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                    return null;
		                }
		                public void checkClientTrusted(X509Certificate[] certs, String authType) {
		                }
		                public void checkServerTrusted(X509Certificate[] certs, String authType) {
		                }
		            }
		        };
					//context.init(kmf.getKeyManagers(), trustManagers, null);
					context.init(kmf.getKeyManagers(), trustManagers, null);
					SSLSocketFactory sslSocketFactory = context.getSocketFactory();
					
					*/
					//SSLServerSocketFactory sslServerSocketFactory = context.getServerSocketFactory();
					//SSLContext sc = SSLContext.getInstance("SSL");
					//sc.init(kmf.getKeyManagers(), trustManagers, null);
					//sc.init(null, trustAllCerts, new java.security.SecureRandom());
					//SSLServerSocketFactory ssf = sc.getServerSocketFactory();
					//url = URL("https://URL your are trying to access HERE")
					HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
					urlConnection.setDoInput(true);
					urlConnection.setDoOutput(true);
					urlConnection.setUseCaches(false);
					//urlConnection.setSSLSocketFactory(sslSocketFactory);
					
					//HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
					/*#THIS IS WHERE YOU ADD HEADER INFORMATION
					#may be different headers for you application*/
					//urlConnection.setRequestProperty("Authorization","Put access code here");
					urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					//urlConnection.setRequestProperty("Content-Type", "application/xml");
					urlConnection.setRequestMethod("POST");
					//System.setProperty("javax.net.ssl.trustStore",pKeyFile);
					//urlParameters = String("param1=paraminput");
					//os = urlConnection.getOutputStream()
		   /* String requestParams = "uid=adds&password=aAsS22.q&active=y&type=F";
		    HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
		    con.setRequestProperty( "Connection", "close" );
		      con.setDoInput(true);
		      con.setDoOutput(true);
		      con.setUseCaches(false);
		      //con.setConnectTimeout( 300000 );
		      //con.setReadTimeout( 30000 );
		      con.setRequestMethod( "POST" );
		      con.setRequestProperty("Content-Type", "application/xml");
		      con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded" );
		      con.setRequestProperty( "Content-Length", Integer.toString(requestParams.length()) );
		      
		      File pKeyFile = new File("C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks");
		      //String ini="soa.test";
		      String pKeyPassword ="soa.test";

		    		  
		      KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
		      //KeyStore keyStore = KeyStore.getInstance("PKCS12");
		      KeyStore keyStore = KeyStore.getInstance("JKS");
		      InputStream keyInput = new FileInputStream(pKeyFile);
		      keyStore.load(keyInput, pKeyPassword.toCharArray());
		      keyInput.close();
		      keyManagerFactory.init(keyStore, pKeyPassword.toCharArray());
		      SSLContext context = SSLContext.getInstance("TLS");
		      context.init(keyManagerFactory.getKeyManagers(), null, new SecureRandom());
		      SSLSocketFactory sockFact = context.getSocketFactory();
		      con.setSSLSocketFactory( sockFact );
		      //OutputStream outputStream = con.getOutputStream();
		       
		        
	 
	        // Install the all-trusting trust manager
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	 
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	 
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			URL url = new URL(URL);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			((HttpURLConnection) conn).setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			//conn.setRequestProperty("Content-Type", "application/xml");
			if (authSwithMode.toUpperCase().equals("ON")) {
				conn.setRequestProperty("Authorization", "Bearer " + authKey);
			}
*/			String input = xml;

			OutputStream os = urlConnection.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			
			
		        
			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			 * throw new RuntimeException("Failed : HTTP error code : " +
			 * conn.getResponseCode()); }
			 */
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader((urlConnection.getInputStream())));				
				String output, response;
				System.out.println("Output from Server .... \n");
				log.info("Output from Server .... \n");
				String statusErr = Integer.toString(((HttpURLConnection) urlConnection).getResponseCode());
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
				while ((output = br.readLine()) != null) {
					response = output;
					return response;
				}

				((HttpURLConnection) urlConnection).disconnect();
			} catch (IOException ioe) {
				System.out.println(ioe);
				log.error(ioe);

			}
			String statusErr = Integer.toString(((HttpURLConnection) urlConnection).getResponseCode());	
			System.out.println("Status is" + statusErr);
			log.info("Status is" + statusErr);
			ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
			
		} catch (Exception e) {

			System.out.println(e);
			log.error(e);

		}
		return "";

	}
	
	private static class TrustAllCertificates implements X509TrustManager {
	    public void checkClientTrusted(X509Certificate[] certs, String authType) {
	    }
	 
	    public void checkServerTrusted(X509Certificate[] certs, String authType) {
	    }
	 
	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}
	private static class TrustAllHosts implements HostnameVerifier {
	    public boolean verify(String hostname, SSLSession session) {
	        return true;
	    }
	}
	public static SOAPMessage createSOAP(String xml){
	       
	       try{
	       
	       MessageFactory msgFactory     = MessageFactory.newInstance();
	        SOAPMessage message           = msgFactory.createMessage();
	        javax.xml.soap.SOAPPart soapPart             = message.getSOAPPart();

	        // Load the SOAP text into a stream source
	        byte[] buffer                 = xml.getBytes();
	        ByteArrayInputStream stream   = new ByteArrayInputStream(buffer);
	        StreamSource source           = new StreamSource(stream);

	        // Set contents of message 
	        soapPart.setContent(source);

	        // -- DONE

	        message.writeTo(System.out);

	        
	        return message;
	        
	       }catch(Exception e){
	              System.out.println(e);
	       }
	              return null;
	    }


	
	public static SOAPMessage callSoapSSLNew(String xml, String TC_ID, String path, Logger log, String authSwithMode,
			String authKey) {

		
		
		
		try {
			SOAPMessage request = null;
			
            
			ExcelUtilities.setExcelFile(path, "Environment", log);
			String URL = ExcelUtilities.getTcId(1, 1, log);
			URL url = new URL(URL);
	        final boolean isHttps = URL.toLowerCase().startsWith("https");
	        HttpsURLConnection httpsConnection = null;
	        // Open HTTPS connection
	        if (isHttps) {
	            // Create SSL context and trust all certificates
	            SSLContext sslContext = SSLContext.getInstance("SSL");
	            TrustManager[] trustAll
	                    = new TrustManager[] {new TrustAllCertificates()};
	            
	            //-------
	            String password = "soa.test";
				//File pKeyFile = new File("C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks");
				String pKeyFile="C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks";
				//InputStream keyInput = new FileInputStream(pKeyFile);
				/// SSLContext sslContext = SSLContext.getInstance("SSL");
				 //SSLContext sslContext = SSLContext.getInstance("TLS");
			        char ksPassword[] = password.toCharArray();
			      
			            KeyStore ks = KeyStore.getInstance("JKS");
			            ks.load(new FileInputStream(new File(pKeyFile)), ksPassword);

			            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			            kmf.init(ks, ksPassword);
			            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			            tmf.init(ks);

			            //sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), new SecureRandom());
	            //-------
	            
	            
	            
	            
	            sslContext.init(kmf.getKeyManagers(), trustAll, new java.security.SecureRandom());
	            // Set trust all certificates context to HttpsURLConnection
	            HttpsURLConnection
	                    .setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	            // Open HTTPS connection
	            //URL url = new URL(endpointUrl);
	            httpsConnection = (HttpsURLConnection) url.openConnection();
	            // Trust all hosts
	            httpsConnection.setHostnameVerifier(new TrustAllHosts());
	            // Connect
	            httpsConnection.connect();
	        }
	        // Send HTTP SOAP request and get response
	        SOAPConnection soapConnection
	                = SOAPConnectionFactory.newInstance().createConnection();
	        SOAPMessage response = soapConnection.call(createSOAP(xml), URL);
	        System.out.println("Output++++++++++++++++++++++++++++++++");
	        System.out.println(response.getSOAPBody());
	        // Close connection
	        soapConnection.close();
	        // Close HTTPS connection
	        if (isHttps) {
	            httpsConnection.disconnect();
	        }
	        return response;
	    } 
	    catch (Exception e) {

			System.out.println(e);
			log.error(e);

		}
		//return "";
		return null;

	
	}
	
	
	
	
	
	
	public static String callPost(String xml, String URL, String TC_ID, String path, Logger log, String authSwithMode,
			String authKey) {

		try {

			// ReadPropFile properties=new
			// ReadPropFile("Environment.properties");
			URL url = new URL(URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			if (authSwithMode.toUpperCase().equals("ON")) {
				conn.setRequestProperty("Authorization", "Bearer " + authKey);
			}

			String input = xml;

			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();

			/*
			 * if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
			 * throw new RuntimeException("Failed : HTTP error code : " +
			 * conn.getResponseCode()); }
			 */
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output, response;
				System.out.println("Output from Server ......");
				log.info("Output from Server ......");
				String statusErr = Integer.toString(conn.getResponseCode());
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				// ExcelUtilities.setStatus(statusErr, TC_ID, statPos, "Main",
				// path, log);
				while ((output = br.readLine()) != null) {
					response = output;
					return response;
				}

				conn.disconnect();
			} catch (IOException ioe) {
				System.out.println(ioe);
				log.error(ioe);

			}
			String statusErr = Integer.toString(conn.getResponseCode());
			System.out.println("Status is" + statusErr);
			log.info("Status is" + statusErr);
			// ExcelUtilities.setStatus(statusErr, TC_ID, statPos, "Main", path,
			// log);
		} catch (Exception e) {
			System.out.println(e);
			log.error(e);
		}
		return "";

	}
	
	

}