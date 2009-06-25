package net.jalan.jws.search.hotel;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
public class CreditCard {
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String AMEX = "AMEX";
	private static final String DC = "DC";
	private static final String DINNERS = "DINERS";
	private static final String ETC = "ETC";
	private static final String JCB = "JCB";
	private static final String MASTER = "MASTER";
	private static final String MILLION = "MILLION";
	private static final String NICOS = "NICOS";
	private static final String SAISON = "SAISON";
	private static final String UC = "UC";
	private NamedNodeMap atr;
	public CreditCard(Node xml) {
		atr = xml.getAttributes();
	}
	public Boolean isAvailable(String name) {
		Node n = atr.getNamedItem(name);
		return n!=null?n.getNodeValue()==TRUE:false;
	}
}

