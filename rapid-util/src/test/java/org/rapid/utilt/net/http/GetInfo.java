package org.rapid.utilt.net.http;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.rapid.util.net.http.HttpProxy;
import org.rapid.util.net.http.SyncHttpAdapter;
import org.rapid.util.net.http.handler.SyncStrRespHandler;

public class GetInfo {

	public static void main(String[] args) throws Exception {
		HttpProxy proxy = new HttpProxy();
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		adapter.setSoTimeout(60000);
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		URIBuilder builder = new URIBuilder("http://iu.91bihu.com/api/CarInsurance/GetFirstVehicleInfo");
		String key = "0bf1714de07";
		Map<String, String> map = new TreeMap<String, String>();
		map.put("EngineNo", "05207709");
		map.put("CarVin", "LBVFR7908BSE50921");
		map.put("MoldName", "揽胜RANGE ROVER SPORT 3.0L越野车");
		map.put("CiytCode", "9");
		map.put("Agent", "73065");
		map.put("CustKey", "10000000000");
		StringBuilder uri = new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			uri.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
			builder.addParameter(entry.getKey(), entry.getValue());
		}
		uri.deleteCharAt(uri.length() - 1);
		uri.append(key);
		System.out.println(uri.toString());
		String SecCode = DigestUtils.md5Hex(uri.toString());
		builder.addParameter("SecCode", SecCode);
		System.out.println(builder.build());
		System.out.println(SecCode);
		String result = proxy.syncRequest(new HttpGet(builder.build()), SyncStrRespHandler.INSTANCE);
		System.out.println(result);
	}
}
