package org.rapid.utilt.net.http;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.rapid.util.common.serializer.SerializeUtil;
import org.rapid.util.crypto.DesUtil;
import org.rapid.util.lang.DateUtils;
import org.rapid.util.net.http.HttpProxy;
import org.rapid.util.net.http.SyncHttpAdapter;
import org.rapid.util.net.http.handler.SyncStrRespHandler;

public class HttpProxyTest {

	public static void main(String[] args) throws Exception {
//		getVehicleInByRenewl();
//		addUser();
		searchCarInsuranceList();
	}
	
	public static void searchCarInsuranceList() throws Exception {
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		long timestamp = DateUtils.currentTime();
		StringBuilder builder = new StringBuilder();
		builder.append("-1").append("2000010120170609").delete(16, builder.length())
		.append(timestamp).append("CarCorder");
		String sign = DigestUtils.md5Hex(builder.toString());
		
		URIBuilder uri = new URIBuilder();
		uri.setScheme("http");
		uri.setHost("120.26.118.161");
		uri.setPort(10606);
		uri.setPath("/Service/CustomerInterface/SearchCarInsuranceList.ashx");
		uri.setParameter("Timestamp", String.valueOf(timestamp));
		uri.setParameter("Sign", sign);
		uri.setParameter("CompanyId", "-1");
		uri.setParameter("DeptId", "2");
		uri.setParameter("TimeStr", "2000010120170609");
		
		HttpGet request = new HttpGet(uri.build());
		request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		String result = proxy.syncRequest(request, SyncStrRespHandler.INSTANCE);
		System.out.println(result);
	}
	
	public static void addUser() throws Exception {
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		long timestamp = DateUtils.currentTime();
		StringBuilder builder = new StringBuilder();
		builder.append(timestamp).append("000000").append(timestamp).append("CarCorder");
		String sign = DigestUtils.md5Hex(builder.toString()).toUpperCase();
		
		JianJieUser jianJieUser = new JianJieUser();
		jianJieUser.setChineseName("张辛林");
		jianJieUser.setLoginName("张辛林");
		jianJieUser.setPhone("13105716369");
		jianJieUser.setIdentityNo("33012719870603341X");
		String json = SerializeUtil.JsonUtil.GSON.toJson(jianJieUser);
		URIBuilder uri = new URIBuilder();
		uri.setScheme("http");
		uri.setHost("120.26.118.161");
		uri.setPort(10606);
		uri.setPath("/Service/CustomerInterface/AddUser.ashx");
		uri.setParameter("Timestamp", String.valueOf(timestamp));
		uri.setParameter("Sign", sign);
		HttpPost post = new HttpPost(uri.build());
		post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
		post.setEntity(new StringEntity(json));
		String result = proxy.syncRequest(post, SyncStrRespHandler.INSTANCE);
		System.out.println(result);
	}
	
	public static void availableInsurance() throws Exception { 
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		
		AvailableInsuranceSubmit submit = new AvailableInsuranceSubmit();
		submit.setUsername("cxdlzjcx");
		submit.setPassword("111111");
		submit.setProvince("四川");
		String body = SerializeUtil.JsonUtil.GSON.toJson(submit);
		TimeUnit.SECONDS.sleep(1);
		System.out.println(body);
		body = DesUtil.EncryptDES(body, "68730531");
		System.out.println(body);
		body = new String(Base64.encodeBase64(body.getBytes()));
		System.out.println(body);
		HttpPost post = new HttpPost("http://123.56.130.87:8383/OpenAPI/CheXian.asmx/OtherISREADY");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("jo", body));
		post.setEntity(new UrlEncodedFormEntity(list, Charset.forName("UTF-8")));
		String result = proxy.syncRequest(post, SyncStrRespHandler.INSTANCE);
		System.out.println(result);
		byte[] buffer = Base64.decodeBase64("ew0KICAiU3RhdGUiOiAxLA0KICAiTWVzc2FnZSI6ICLmn6Xor6LmiJDlip8iLA0KICAiT3Blbkxpc3QiOiBbDQogICAgew0KICAgICAgIkluc0NweUlEIjogIjQ3MzMiLA0KICAgICAgIkluc0NweU5hbWUiOiAi5bmz5a6J6L2m6ZmpIiwNCiAgICAgICJQcm9kdWN0Q29kZSI6ICJQQVpZQ1giLA0KICAgICAgIklzT3BlbiI6IHRydWUNCiAgICB9DQogIF0NCn0=");
		System.out.println(new String(buffer));
	}
	
	private static void getVehicleInByRenewl() throws Exception {
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		
		OfferSubmit submit = new OfferSubmit();
		submit.setUsername("cxdlzjcx");
		submit.setPassword("111111");
		submit.setLicenseNo("沪C239Q5");
		submit.setVin("");
		submit.setSyr("冯高祥");
		submit.setInsureProvince("四川");
		submit.setCompanyID("2038");
		submit.setProductCode("PAZYCX");
		String body = SerializeUtil.XmlUtil.beanToXml(submit, "utf-8");
//		body = body.replace(" standalone=\"yes\"", "");
		System.out.println(body);
		body = DesUtil.EncryptDES(body, "68730531");
		body = new String(Base64.encodeBase64(body.getBytes()));
		System.out.println(body);
		HttpPost post = new HttpPost("http://123.56.130.87:8383/OpenAPI/CheXian.asmx/GetVehicleInByRenewl");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("xmlData", body));
		post.setEntity(new UrlEncodedFormEntity(list, Charset.forName("UTF-8")));
		String result = proxy.syncRequest(post, SyncStrRespHandler.INSTANCE);
		System.out.println(result);
		byte[] buffer = Base64.decodeBase64("PFJFVFVSTj48TUVTU0FHRT48VkFMVUU+5pyq5p+l6K+i5Yiw57ut5L+d5L+h5oGvPC9WQUxVRT48VElNRT4yMDE3LTA1LTAyIDExOjM0OjA1PC9USU1FPjwvTUVTU0FHRT48L1JFVFVSTj4=");
		System.out.println(new String(buffer));
	}
}
