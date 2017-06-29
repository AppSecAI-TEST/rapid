package org.rapid.utilt.net.http;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RETURN")
public class VehicleInfos {
	
	private List<VehicleCommonInfo> list;
	
    @XmlElement(name = "VehicleCommonInfo")  
	public List<VehicleCommonInfo> getList() {
		return list;
	}
	
	public void setList(List<VehicleCommonInfo> list) {
		this.list = list;
	}

	public static class VehicleCommonInfo {
		private String id;						// 唯一标识吗
		private String insVehicleId;			
		private String price;					// 新车购置价(含税)
		private String priceNoTax;				// 新车购置价(不含税)
		private int type;						// 车辆型号
		private String clazz;					// 车辆种类/车型种类
		private String engineNo;
		private int year;						// 年款
		private String name;					// 车型名称/厂牌型号/厂牌型号名称
		private String exhaust;					// 排量
		private String brandName;				// 品牌名称
		private String loadWeight;				// 核定载质量/拉货的质量
		private String kerbWeight;				// 汽车整备质量/汽车自重，单位千克
		private String classPicc;				// 车型类型，人保特有
		private int seat;
		private String transmissionName;
		private String vin;
		private String vehicleTypeCode;			// 车辆种类类型
		private String vehicleTypeDetailCode;	// 车辆种类类型详细(特种车独有)
		private int vehicleType;				// 车辆种类
		@XmlElement(name = "ID")
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		@XmlElement(name = "InsVehicleId")
		public String getInsVehicleId() {
			return insVehicleId;
		}
		public void setInsVehicleId(String insVehicleId) {
			this.insVehicleId = insVehicleId;
		}
		@XmlElement(name = "Price")
		public String getPrice() {
			return price;
		}
		public void setPrice(String price) {
			this.price = price;
		}
		@XmlElement(name = "PriceNoTax")
		public String getPriceNoTax() {
			return priceNoTax;
		}
		public void setPriceNoTax(String priceNoTax) {
			this.priceNoTax = priceNoTax;
		}
		@XmlElement(name = "Type")
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		@XmlElement(name = "Class")
		public String getClazz() {
			return clazz;
		}
		public void setClazz(String clazz) {
			this.clazz = clazz;
		}
		@XmlElement(name = "EngineNo")
		public String getEngineNo() {
			return engineNo;
		}
		public void setEngineNo(String engineNo) {
			this.engineNo = engineNo;
		}
		@XmlElement(name = "Year")
		public int getYear() {
			return year;
		}
		public void setYear(int year) {
			this.year = year;
		}
		@XmlElement(name = "Name")
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@XmlElement(name = "Exhaust")
		public String getExhaust() {
			return exhaust;
		}
		public void setExhaust(String exhaust) {
			this.exhaust = exhaust;
		}
		@XmlElement(name = "BrandName")
		public String getBrandName() {
			return brandName;
		}
		public void setBrandName(String brandName) {
			this.brandName = brandName;
		}
		@XmlElement(name = "LoadWeight")
		public String getLoadWeight() {
			return loadWeight;
		}
		public void setLoadWeight(String loadWeight) {
			this.loadWeight = loadWeight;
		}
		@XmlElement(name = "KerbWeight")
		public String getKerbWeight() {
			return kerbWeight;
		}
		public void setKerbWeight(String kerbWeight) {
			this.kerbWeight = kerbWeight;
		}
		@XmlElement(name = "ClassPicc")
		public String getClassPicc() {
			return classPicc;
		}
		public void setClassPicc(String classPicc) {
			this.classPicc = classPicc;
		}
		@XmlElement(name = "Seat")
		public int getSeat() {
			return seat;
		}
		public void setSeat(int seat) {
			this.seat = seat;
		}
		@XmlElement(name = "TransmissionName")
		public String getTransmissionName() {
			return transmissionName;
		}
		public void setTransmissionName(String transmissionName) {
			this.transmissionName = transmissionName;
		}
		@XmlElement(name = "Vin")
		public String getVin() {
			return vin;
		}
		public void setVin(String vin) {
			this.vin = vin;
		}
		@XmlElement(name = "Keyword2")
		public String getVehicleTypeCode() {
			return vehicleTypeCode;
		}
		public void setVehicleTypeCode(String vehicleTypeCode) {
			this.vehicleTypeCode = vehicleTypeCode;
		}
		@XmlElement(name = "Keyword3")
		public String getVehicleTypeDetailCode() {
			return vehicleTypeDetailCode;
		}
		public void setVehicleTypeDetailCode(String vehicleTypeDetailCode) {
			this.vehicleTypeDetailCode = vehicleTypeDetailCode;
		}
		@XmlElement(name = "Keyword6")
		public int getVehicleType() {
			return vehicleType;
		}
		public void setVehicleType(int vehicleType) {
			this.vehicleType = vehicleType;
		}
	}
}
