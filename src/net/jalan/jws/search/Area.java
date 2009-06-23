package net.jalan.jws.search;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import android.util.Log;

public class Area {
	public static final String AREA = "Area";
	public static final String REGION = "Region";
	public static final String PREFECTURE = "Prefecture";
	public static final String LAREA = "LargeArea";
	public static final String SAREA = "SmallArea";
	public static final String CODE = "cd";
	public static final String NAME = "name";
	
	private static final String TAG = "MyActivity";
	
	public String code;
	public String name;
	public String type;
	private LinkedHashMap<String,Area> children;
	
	public Area(Node node) {
		type = node.getNodeName();
		if(!type.equals(AREA)) {
			NamedNodeMap atr = node.getAttributes();
			code = atr.getNamedItem(Area.CODE).getNodeValue();
			name = atr.getNamedItem(Area.NAME).getNodeValue();
		}
		children = getList(node.getChildNodes());
			
	}
	public Area(Document doc) {
		type = AREA;
		children = getList(doc.getElementsByTagName(Area.REGION));
	}
	private LinkedHashMap getList(NodeList list) {
		String childName = type.equals(Area.AREA)?
			Area.REGION:type.equals(Area.REGION)?
				Area.PREFECTURE:type.equals(Area.PREFECTURE)?
					Area.LAREA:type.equals(Area.LAREA)?
						Area.SAREA:"";
		int len = list.getLength();
		LinkedHashMap map = new LinkedHashMap<String,Area>();
		if(childName.equals("")) return map;
		for(int i=0;i<len;++i) {
			Node n = (Node) list.item(i);
			if(n.getNodeName().equals(childName)){
				Area a = new Area(n);
				map.put(a.code,a);
			}
		}
		return map;
	}
	public String[] getNames() {
		LinkedList rtn = new LinkedList();
		Iterator it = children.keySet().iterator();
		while (it.hasNext()) {
			Object o = it.next();
			String nm = item(o).name.toString();
			rtn.add(nm);
		}
		return (String[]) rtn.toArray(new String[]{});
	}
	public String[] getCodes() {
		LinkedList rtn = new LinkedList();
		Iterator it = children.keySet().iterator();
		while (it.hasNext()) {
			Object o = it.next();
			String nm = item(o).code.toString();
			rtn.add(nm);
		}
		return (String[]) rtn.toArray(new String[]{});
	}
	public String getCode(int index) {
		return getCodes()[index];
	}
	public LinkedHashMap<String,Area> item() {
		return children;
	}
	public Area item(Object key) {
		return children.get(key);
	}
}
