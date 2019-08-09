package ReusableComponents;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.xml.soap.*;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

public class SOAPClientRequest {
    
	
	public static ReadPropFile properties = new ReadPropFile("Environment.properties");
	public static File directory = new File(properties.getPropertyValue("directory"));
	public static String CertificatePath = directory.getAbsolutePath()
			+ properties.getPropertyValue("Certificate");
	public static String password = properties.getPropertyValue("CertificatePassword");
	public static String str,errorMessage,ErrorMessage;
    public static String getHostNameFromUrl (String urlString){
     
         String host = urlString.substring(8,urlString.indexOf("/", 8));
    return host;
 }
    
    public static String createSOAPRequest (String soapMessage, String TC_ID, String path, Logger log)
    
			    {

    	ExcelUtilities.setExcelFile(path, "Environment", log);
			           String URL;
					try {
						URL = ExcelUtilities.getTcId(1, 1, log);
					
			
			String lvSoapMessage = soapMessage;
			String responseString = "";
			//Create connection
			URL URLForSOAP = new URL(URL);
			
			KeyStore ksCACert = KeyStore.getInstance("JKS");
			//String password = "soa.test";
			File pKeyFile = new File(CertificatePath);
			//File pKeyFile = new File("C:/QuickWin_Rating/certificate doc rating/sys.ts-soa-testing.td.com.jks");
			InputStream keyInput = new FileInputStream(pKeyFile);
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
			
			URLConnection URLConnectionForSOAP = URLForSOAP.openConnection();
			 HttpsURLConnection Connection =  (HttpsURLConnection) URLConnectionForSOAP;
			 //Adjust connection
			 Connection.setDoOutput(true);
			 Connection.setDoInput(true);
			 Connection.setRequestMethod("POST");
			 //Use the method to get the host name from the URL string and set
			 //the request property for the connection.
			 Connection.setRequestProperty("Host", getHostNameFromUrl(URL));
			 Connection.setRequestProperty("Content-Type","application/soap+xml; charset=utf-8");
			 Connection.setSSLSocketFactory(sslSocketFactory);
			 //Send the request
			 OutputStreamWriter soapRequestWriter = new OutputStreamWriter(Connection.getOutputStream());
			 soapRequestWriter.write(lvSoapMessage);
			 //System.out.println(lvSoapMessage);
			 soapRequestWriter.flush();
			 //Read the reply
			 try{
			 BufferedReader soapRequestReader =
			         new BufferedReader
			                 (new InputStreamReader
			                         (Connection.getInputStream()));
			 String line;
			 while ((line = soapRequestReader.readLine()) != null) {
			     responseString = responseString.concat(line);
			     
			     }
			 
			 //Clean up
			 soapRequestWriter.close();
			 soapRequestReader.close();
			 Connection.disconnect();
			 String statusErr = Integer.toString(((HttpURLConnection) Connection).getResponseCode());
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
				return responseString;
			/*String statusMessage = ((HttpURLConnection) Connection).getResponseMessage();
				System.out.println("Status is: " + statusMessage);
				log.info("Status is: " + statusMessage);
				ExcelUtilities.setStatus(statusMessage, TC_ID, 4, "Main", path, log);*/
			 }
			 catch (IOException ioe) {
					System.out.println(ioe);
					log.error(ioe);

				}
			 String statusErr = Integer.toString(((HttpURLConnection) Connection).getResponseCode());
			 
			 //String str = null ;
		        StringBuffer buf = new StringBuffer(); 
			 InputStream statusErrMessage = ((HttpURLConnection) Connection).getErrorStream();
			 BufferedReader br1=new BufferedReader(new InputStreamReader(statusErrMessage));
			 if (statusErrMessage != null) {                            
	                while ((str = br1.readLine()) != null) {    
	                    buf.append(str );
	                    //System.out.println(str);
	                    errorMessage=str;
	                }  
	                String array1[]= errorMessage.split("<message>");
	                for (String temp: array1){
	     		          //System.out.println(temp);
	     		          if(temp.contains("</message>"))
	     		          {
	     		        	 //System.out.println(temp);
	     		        	 String array2[]=temp.split("</message>");
	     		        			 for(String temp1 : array2)
	     		        			 {
	     		        				ErrorMessage = temp1;
	     		        				System.out.println(temp1);
	     		        				log.info("Status is: " + ErrorMessage);
	     		       				ExcelUtilities.setStatus(ErrorMessage, TC_ID, 4, "Main", path, log);
	     		        				break;
	     		        			 }
	     		        			
	     		          }
	     		       }
	            }
			 
   			 
			
				System.out.println("Status is: " + statusErr);
				log.info("Status is: " + statusErr);
				ExcelUtilities.setStatus(statusErr, TC_ID, 3, "Main", path, log);
			 
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "";
}
 
 public static SOAPMessage createSOAP(String xml){
    
    try{
    
    MessageFactory msgFactory     = MessageFactory.newInstance();
     SOAPMessage message           = msgFactory.createMessage();
     SOAPPart soapPart             = message.getSOAPPart();

     // Load the SOAP text into a stream source
     byte[] buffer                 = xml.getBytes();
     ByteArrayInputStream stream   = new ByteArrayInputStream(buffer);
     StreamSource source           = new StreamSource(stream);

     // Set contents of message 
     soapPart.setContent(source);

     // -- DONE

     //message.writeTo(System.out);

     
     return message;
     
    }catch(Exception e){
           System.out.println(e);
    }
           return null;
 }

}
