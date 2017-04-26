package org.rapid.utilt.net.http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "string")
public class Res {

	private String string;
	
	public String getString() {
		return string;
	}
	
	public void setString(String string) {
		this.string = string;
	}
}
