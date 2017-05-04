package org.rapid.utilt.net.http;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "VehicleData")
public class OfferSubmit {

	private String Username;
	private String Password;
	private String LicenseNo;
	private String Vin;
	private String Syr;
	private String InsureProvince;
	private String CompanyID;
	private String ProductCode;

	public String getUsername() {
		return Username;
	}

	@XmlElement(name = "Username")
	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	@XmlElement(name = "Password")
	public void setPassword(String password) {
		Password = password;
	}

	public String getLicenseNo() {
		return LicenseNo;
	}

	@XmlElement(name = "LicenseNo")
	public void setLicenseNo(String licenseNo) {
		LicenseNo = licenseNo;
	}

	public String getVin() {
		return Vin;
	}
	
	@XmlElement(name = "Vin")
	public void setVin(String vin) {
		Vin = vin;
	}

	public String getSyr() {
		return Syr;
	}

	@XmlElement(name = "Syr")
	public void setSyr(String syr) {
		Syr = syr;
	}

	public String getInsureProvince() {
		return InsureProvince;
	}

	@XmlElement(name = "InsureProvince")
	public void setInsureProvince(String insureProvince) {
		InsureProvince = insureProvince;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	@XmlElement(name = "CompanyID")
	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public String getProductCode() {
		return ProductCode;
	}

	@XmlElement(name = "ProductCode")
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
}
