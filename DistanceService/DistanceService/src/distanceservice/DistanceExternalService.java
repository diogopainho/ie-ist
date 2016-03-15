package distanceservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import java.net.URL;
import java.net.URLConnection;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.jws.WebService;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLNode;

import org.xml.sax.InputSource;


@WebService(serviceName = "DistanceExternalService", portName = "DistanceExternalServiceSoap12HttpPort")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class DistanceExternalService {
    public DistanceExternalService() {
        super();
    }
    
    public String getDistance(String origin, String destination) {
        String apiID = "AIzaSyBBfBLMF5OiQzLtMZ1pudF7I7wpqj46m_U";
        String endpoint = "https://maps.googleapis.com/maps/api/directions/";
        
        StringBuilder responseString = null;
        String returnString = "";
        String xpathResult = null;
        
        String charset = "UTF-8";
        
        String url = endpoint + "xml?origin=" + origin + "&destination=" + destination + "&key=" + apiID;
        System.out.println(url);
        
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
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
 
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
          
        try {
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
                
            BufferedReader rd = new BufferedReader(new InputStreamReader(response));
            responseString = new StringBuilder();
            
            String inputLine;
            
            while ((inputLine = rd.readLine()) != null)
                responseString.append(inputLine);
            rd.close();
            
            System.out.println("Response: " + responseString);
            
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
            
            try {
                DOMParser domParser = new DOMParser();
                domParser.parse(new InputSource(new StringReader(responseString.toString())));
                XMLDocument document = domParser.getDocument();
                
                XMLNode node = (XMLNode)document.selectSingleNode("/route/step/distance/@value");
                xpathResult = node.getText();
                returnString = xpathResult;
                System.out.println(returnString);
            
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
                   
            return returnString;
            }
}
