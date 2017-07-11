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
		
		URIBuilder builder = new URIBuilder("http://iu.91bihu.com/api/CarInsurance/GetPrecisePrice");
		String key = "0bf1714de07";
		Map<String, String> map = new TreeMap<String, String>();
		map.put("LicenseNo", "浙F010CA");
		map.put("TimeFormat", "1");
		map.put("ShowEmail", "1");
		map.put("ShowVehicleInfo", "1");
		map.put("ShowCarInfo", "1");
		map.put("QuoteGroup", "2");
		map.put("Agent", "73065");
		map.put("CustKey", DigestUtils.md5Hex(String.valueOf(17)));
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
