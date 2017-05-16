package org.rapid.utilt.net.http;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.rapid.util.net.http.HttpProxy;
import org.rapid.util.net.http.SyncHttpAdapter;
import org.rapid.util.net.http.handler.SyncStrRespHandler;

public class GetInfo {

	public static void main(String[] args) throws Exception {
		HttpProxy proxy = new HttpProxy();
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		URIBuilder builder = new URIBuilder("http://iu.91bihu.com/api/CarInsurance/getreinfo");
		String key = "0bf1714de07";
		Map<String, String> map = new TreeMap<String, String>();
		map.put("LicenseNo", "鄂A34JR9");
		map.put("CityCode", "9");
		map.put("Agent", "73065");
		map.put("Group", "1");
		map.put("CanShowNo", "1");
		map.put("CanShowExhaustScale", "1");
		map.put("ShowXiuLiChangType", "1");
		map.put("TimeFormat", "1");
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
