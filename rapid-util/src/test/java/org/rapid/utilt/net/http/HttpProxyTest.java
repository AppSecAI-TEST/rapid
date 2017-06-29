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
//		searchCarInsuranceList();
		carInfo();
	}
	
	public static void carInfo() throws Exception {
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		VehicleSearcher submit = new VehicleSearcher();
		submit.setUsername("cxdlzjcx");
		submit.setPassword("111111");
		submit.setVin("WAU8FD8T5FA028514");
		String body = SerializeUtil.XmlUtil.beanToXml(submit, "utf-8");
		System.out.println(body);
		body = DesUtil.EncryptDES(body, "68730531");
		body = new String(Base64.encodeBase64(body.getBytes()));
		System.out.println(body);
		HttpPost post = new HttpPost("http://123.56.130.87:8383/OpenAPI/CheXian.asmx/GetVehicleInfoByVinOrModelCode");
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("xmlData", body));
		post.setEntity(new UrlEncodedFormEntity(list, Charset.forName("UTF-8")));
		String result = proxy.syncRequest(post, SyncStrRespHandler.INSTANCE);
		System.out.println(result);
		Result r = SerializeUtil.XmlUtil.xmlToBean(result, Result.class);
		System.out.println(r.getContent());
		byte[] buffer = Base64.decodeBase64("PFJFVFVSTj48VmVoaWNsZUNvbW1vbkluZm8+PElEPjU4ODg5NTI8L0lEPjxJbnNWZWhpY2xlSWQ+TEREMTAxNUJKWDwvSW5zVmVoaWNsZUlkPjxQcmljZT4xMDk5MjI8L1ByaWNlPjxQcmljZU5vVGF4PjEwMzMwMDwvUHJpY2VOb1RheD48QW5hbG9neVByaWNlPjA8L0FuYWxvZ3lQcmljZT48QW5hbG9neVByaWNlTm9UYXg+MDwvQW5hbG9neVByaWNlTm9UYXg+PENvdW50cnlOYXR1cmUgLz48VHlwZSAvPjxDbGFzcz5LMzM8L0NsYXNzPjxCcmFuZE5hbWVOZXcgLz48QnJhbmRJZE5ldz5BMDEyPC9CcmFuZElkTmV3PjxFbmdpbmVQb3dlciAvPjxFbmdpbmVUeXBlIC8+PEVuZ2luZU5vIC8+PFllYXI+MjAxMjwvWWVhcj48TmFtZT7ljJfkuqznjrDku6NCSDcxNjFIQVnovb/ovaY8L05hbWU+PENvZGUgLz48RW5lcmd5VHlwZSAvPjxFeGhhdXN0PjEuNTkxPC9FeGhhdXN0PjxCcmFuZElkPlhEQTwvQnJhbmRJZD48QnJhbmROYW1lPuWMl+S6rOeOsOS7ozwvQnJhbmROYW1lPjxMb2FkV2VpZ2h0PjA8L0xvYWRXZWlnaHQ+PEtlcmJXZWlnaHQgLz48Q2xhc3NQaWNjIC8+PFNlYXQ+NTwvU2VhdD48R3JvdXBOYW1lIC8+PEZhbWlseUlkTmV3IC8+PEZhbWlseU5hbWVOZXcgLz48QWxpYXMgLz48TWFrZXJJZCAvPjxNYWtlck5hbWUgLz48VHJhbnNtaXNzaW9uVHlwZSAvPjxUcmFuc21pc3Npb25OYW1lPuaJi+iHquS4gOS9kyBHU+aXtuWwmuWeiyDlm73ihaMgKOiKguiDveihpei0tCk8L1RyYW5zbWlzc2lvbk5hbWU+PFZJTj5MQkVNREFGQzlEWjE2MDY1MDwvVklOPjxWSU5TaW1wbGUgLz48Q29tbWVudCAvPjxJbnNDcHlTaW1wbGVFbk5hbWU+UEFaWUNYPC9JbnNDcHlTaW1wbGVFbk5hbWU+PEluc0NweUlkPjE8L0luc0NweUlkPjxDcmVhdGVEYXRlPjIwMTctMDYtMjdUMTM6NDU6NDE8L0NyZWF0ZURhdGU+PEtleXdvcmQxPmh0dHA6Ly93d3cuZXBpY2MuY29tLmNuL2VjYXIvY2FyL2Nhck1vZGVsL3Nob3dQaWM/cGF0aD0vaG9tZS9lY2FyL2p5L3BpY3MvZ3JvdXAvWERBMUFEMDEvc21hbGwvc21hbGw8L0tleXdvcmQxPjxLZXl3b3JkMj5BMDEyPC9LZXl3b3JkMj48S2V5d29yZDMgLz48S2V5d29yZDQ+MDI8L0tleXdvcmQ0PjxLZXl3b3JkNT4xPC9LZXl3b3JkNT48S2V5d29yZDY+MTwvS2V5d29yZDY+PEtleXdvcmQ3IC8+PEtleXdvcmQ4IC8+PEtleXdvcmQ5IC8+PC9WZWhpY2xlQ29tbW9uSW5mbz48VmVoaWNsZUNvbW1vbkluZm8+PElEPjU4ODg5NTA8L0lEPjxJbnNWZWhpY2xlSWQ+TEREMTAwNEJKWDwvSW5zVmVoaWNsZUlkPjxQcmljZT4xMzAxNDA8L1ByaWNlPjxQcmljZU5vVGF4PjEyMjMwMDwvUHJpY2VOb1RheD48QW5hbG9neVByaWNlPjA8L0FuYWxvZ3lQcmljZT48QW5hbG9neVByaWNlTm9UYXg+MDwvQW5hbG9neVByaWNlTm9UYXg+PENvdW50cnlOYXR1cmUgLz48VHlwZSAvPjxDbGFzcz5LMzM8L0NsYXNzPjxCcmFuZE5hbWVOZXcgLz48QnJhbmRJZE5ldz5BMDEyPC9CcmFuZElkTmV3PjxFbmdpbmVQb3dlciAvPjxFbmdpbmVUeXBlIC8+PEVuZ2luZU5vIC8+PFllYXI+MjAxMjwvWWVhcj48TmFtZT7ljJfkuqznjrDku6NCSDcxNjFIQVnovb/ovaY8L05hbWU+PENvZGUgLz48RW5lcmd5VHlwZSAvPjxFeGhhdXN0PjEuNTkxPC9FeGhhdXN0PjxCcmFuZElkPlhEQTwvQnJhbmRJZD48QnJhbmROYW1lPuWMl+S6rOeOsOS7ozwvQnJhbmROYW1lPjxMb2FkV2VpZ2h0PjA8L0xvYWRXZWlnaHQ+PEtlcmJXZWlnaHQgLz48Q2xhc3NQaWNjIC8+PFNlYXQ+NTwvU2VhdD48R3JvdXBOYW1lIC8+PEZhbWlseUlkTmV3IC8+PEZhbWlseU5hbWVOZXcgLz48QWxpYXMgLz48TWFrZXJJZCAvPjxNYWtlck5hbWUgLz48VHJhbnNtaXNzaW9uVHlwZSAvPjxUcmFuc21pc3Npb25OYW1lPuaJi+iHquS4gOS9kyBHTFjpooblhYjlnosg5Zu94oWjICjoioLog73ooaXotLQpPC9UcmFuc21pc3Npb25OYW1lPjxWSU4+TEJFTURBRkM5RFoxNjA2NTA8L1ZJTj48VklOU2ltcGxlIC8+PENvbW1lbnQgLz48SW5zQ3B5U2ltcGxlRW5OYW1lPlBBWllDWDwvSW5zQ3B5U2ltcGxlRW5OYW1lPjxJbnNDcHlJZD4xPC9JbnNDcHlJZD48Q3JlYXRlRGF0ZT4yMDE3LTA2LTI3VDEzOjQ1OjQxPC9DcmVhdGVEYXRlPjxLZXl3b3JkMT5odHRwOi8vd3d3LmVwaWNjLmNvbS5jbi9lY2FyL2Nhci9jYXJNb2RlbC9zaG93UGljP3BhdGg9L2hvbWUvZWNhci9qeS9waWNzL2dyb3VwL1hEQTFBRDAxL3NtYWxsL3NtYWxsPC9LZXl3b3JkMT48S2V5d29yZDI+QTAxMjwvS2V5d29yZDI+PEtleXdvcmQzIC8+PEtleXdvcmQ0PjAyPC9LZXl3b3JkND48S2V5d29yZDU+MTwvS2V5d29yZDU+PEtleXdvcmQ2PjE8L0tleXdvcmQ2PjxLZXl3b3JkNyAvPjxLZXl3b3JkOCAvPjxLZXl3b3JkOSAvPjwvVmVoaWNsZUNvbW1vbkluZm8+PFZlaGljbGVDb21tb25JbmZvPjxJRD41ODg4OTUxPC9JRD48SW5zVmVoaWNsZUlkPkxERDEwMDZCSlg8L0luc1ZlaGljbGVJZD48UHJpY2U+MTM3NTg4PC9QcmljZT48UHJpY2VOb1RheD4xMjkzMDA8L1ByaWNlTm9UYXg+PEFuYWxvZ3lQcmljZT4wPC9BbmFsb2d5UHJpY2U+PEFuYWxvZ3lQcmljZU5vVGF4PjA8L0FuYWxvZ3lQcmljZU5vVGF4PjxDb3VudHJ5TmF0dXJlIC8+PFR5cGUgLz48Q2xhc3M+SzMzPC9DbGFzcz48QnJhbmROYW1lTmV3IC8+PEJyYW5kSWROZXc+QTAxMjwvQnJhbmRJZE5ldz48RW5naW5lUG93ZXIgLz48RW5naW5lVHlwZSAvPjxFbmdpbmVObyAvPjxZZWFyPjIwMTI8L1llYXI+PE5hbWU+5YyX5Lqs546w5LujQkg3MTYxSEFZ6L2/6L2mPC9OYW1lPjxDb2RlIC8+PEVuZXJneVR5cGUgLz48RXhoYXVzdD4xLjU5MTwvRXhoYXVzdD48QnJhbmRJZD5YREE8L0JyYW5kSWQ+PEJyYW5kTmFtZT7ljJfkuqznjrDku6M8L0JyYW5kTmFtZT48TG9hZFdlaWdodD4wPC9Mb2FkV2VpZ2h0PjxLZXJiV2VpZ2h0IC8+PENsYXNzUGljYyAvPjxTZWF0PjU8L1NlYXQ+PEdyb3VwTmFtZSAvPjxGYW1pbHlJZE5ldyAvPjxGYW1pbHlOYW1lTmV3IC8+PEFsaWFzIC8+PE1ha2VySWQgLz48TWFrZXJOYW1lIC8+PFRyYW5zbWlzc2lvblR5cGUgLz48VHJhbnNtaXNzaW9uTmFtZT7miYvoh6rkuIDkvZMgRExY5bCK6LS15Z6LIOWbveKFoyAo6IqC6IO96KGl6LS0KTwvVHJhbnNtaXNzaW9uTmFtZT48VklOPkxCRU1EQUZDOURaMTYwNjUwPC9WSU4+PFZJTlNpbXBsZSAvPjxDb21tZW50IC8+PEluc0NweVNpbXBsZUVuTmFtZT5QQVpZQ1g8L0luc0NweVNpbXBsZUVuTmFtZT48SW5zQ3B5SWQ+MTwvSW5zQ3B5SWQ+PENyZWF0ZURhdGU+MjAxNy0wNi0yN1QxMzo0NTo0MTwvQ3JlYXRlRGF0ZT48S2V5d29yZDE+aHR0cDovL3d3dy5lcGljYy5jb20uY24vZWNhci9jYXIvY2FyTW9kZWwvc2hvd1BpYz9wYXRoPS9ob21lL2VjYXIvankvcGljcy9ncm91cC9YREExQUQwMS9zbWFsbC9zbWFsbDwvS2V5d29yZDE+PEtleXdvcmQyPkEwMTI8L0tleXdvcmQyPjxLZXl3b3JkMyAvPjxLZXl3b3JkND4wMjwvS2V5d29yZDQ+PEtleXdvcmQ1PjE8L0tleXdvcmQ1PjxLZXl3b3JkNj4xPC9LZXl3b3JkNj48S2V5d29yZDcgLz48S2V5d29yZDggLz48S2V5d29yZDkgLz48L1ZlaGljbGVDb21tb25JbmZvPjxNRVNTQUdFPjxWQUxVRT5PSzwvVkFMVUU+PFRJTUU+MjAxNy0wNi0yNyAxMzo1Mzo1OTwvVElNRT48L01FU1NBR0U+PC9SRVRVUk4+");
		result = new String(buffer);
		System.out.println(result);
		VehicleInfos infos = SerializeUtil.XmlUtil.xmlToBean(result, VehicleInfos.class);
		System.out.println(SerializeUtil.JsonUtil.GSON.toJson(infos));
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
		adapter.setSoTimeout(30000);
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
		byte[] buffer = Base64.decodeBase64("PFJFVFVSTj48TUVTU0FHRT48VkFMVUU+5pyq5p+l6K+i5Yiw57ut5L+d5L+h5oGvPC9WQUxVRT48VElNRT4yMDE3LTA2LTIxIDExOjQ4OjEyPC9USU1FPjwvTUVTU0FHRT48L1JFVFVSTj4=");
		System.out.println(new String(buffer));
	}
}
