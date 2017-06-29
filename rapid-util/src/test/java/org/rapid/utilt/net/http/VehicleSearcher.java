package org.rapid.utilt.net.http;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VehicleData")
public class VehicleSearcher {

	private String Username;
	private String Password;
	private String Vin;
	private String ModelCode = "";

	@XmlElement(name = "Username")
	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	@XmlElement(name = "Password")
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@XmlElement(name = "Vin")
	public String getVin() {
		return Vin;
	}

	public void setVin(String vin) {
		Vin = vin;
	}

	@XmlElement(name = "ModelCode")
	public String getModelCode() {
		return ModelCode;
	}

	public void setModelCode(String modelCode) {
		ModelCode = modelCode;
	}
}
