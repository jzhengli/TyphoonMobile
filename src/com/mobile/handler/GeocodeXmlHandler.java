package com.mobile.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GeocodeXmlHandler extends DefaultHandler {
	int i=0;
	private String tagName;
	private String province,city,address;
	private Boolean isFirstAdress=false;

	public String getprovince() {
		return this.province;
	}
	public String getcity() {
		return this.city;
	}
	public String getaddress() {
		return this.address;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (isFirstAdress) {
			if (tagName.equals("address")) {
				if (i < 1) {
					address = new String(ch, start, length);
					i++;
					System.out.println("address:" + address);
				}
			} else if (tagName.equals("AdministrativeAreaName")) {
				province = new String(ch, start, length);
			} else if (tagName.equals("LocalityName")) {
				city = new String(ch, start, length);
			}
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("开始解析--------");
	}
	
	@Override
	public void endDocument() throws SAXException {
		System.out.println("结束解析--------");
		super.endDocument();
	}
	
	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if(localName.equals("Placemark")){
			tagName="";
			String id=attributes.getValue("id");
			if(id.equals("p1")){
				isFirstAdress=true;
			}else{
				isFirstAdress=false;
			}
		}else if(localName.equals("address")){
			tagName=localName;
		}else if(localName.equals("AdministrativeAreaName")){
			tagName=localName;
		}else if(localName.equals("LocalityName")){
			tagName=localName;
		}else{
			tagName="";
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		super.endElement(uri, localName, name);
	}

}
