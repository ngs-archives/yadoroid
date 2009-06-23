package net.jalan.jws.search;
import java.io.IOException;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import net.it4myself.util.RestfulClient;



public class APIRequest {
	static public final String VERSION = "V1";
	static public final String HOST = "http://jws.jalan.net/";
	static public final String HOTEL_LITE    = "APILite/HotelSearch/";
	static public final String HOTEL_ADVANCE = "APIAdvance/HotelSearch/";
	static public final String STOCK = "APIAdvance/StockSearch/";
	static public final String AREA = "APICommon/AreaSearch/";
	static public final String ONSEN = "APICommon/OnsenSearch/";
	//
	static public String userAgent = "net.jalan.jws.search.APIRequest";
	static public String apiKey = "";
	//
	public String type;
	public HashMap param;
	public APIRequest(String type,HashMap<String,String> param) {
		this.type = type;
		this.param = param;
	}
	public APIRequest(String type) {
		this.type = type;
		this.param = new HashMap();
	}
	public Document connect() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		try {
			builder = (DocumentBuilder) factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return doc;
		}
		try {
			param.put("key",apiKey);
			doc = (Document) RestfulClient.Get(HOST+type+VERSION+"/",param,builder);
		} catch(ClientProtocolException e) {
			e.printStackTrace();
			return doc;
		} catch(IOException e) {
			e.printStackTrace();
			return doc;
		} catch (SAXException e) {
			e.printStackTrace();
			return doc;
		}
		return doc;
	}
	
}
