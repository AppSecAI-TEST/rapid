package org.rapid.utilt.net.http;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.rapid.util.common.Consts;
import org.rapid.util.net.http.AsyncHttpAdapter;
import org.rapid.util.net.http.HttpProxy;
import org.rapid.util.net.http.handler.AsyncRespHandler;

public class YunPianTest {
	
	private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";
	private static String ENCODING = "UTF-8";

	public static void main(String[] args) throws Exception {
		HttpProxy proxy = new HttpProxy();
		AsyncHttpAdapter adapter = new AsyncHttpAdapter();
		adapter.setSslEnabled(true);
		proxy.setAsyncHttp(adapter);
		proxy.init();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("apikey", "8288deb5acd16da054cc67d3df93d565");
		params.put("tpl_id", "1855094");
		String tpl_value = URLEncoder.encode("#license#",Consts.UTF_8.name()) + "=" + URLEncoder.encode("浙H0155R", Consts.UTF_8.name()) 
		+ "&" + URLEncoder.encode("#insurer#", Consts.UTF_8.name()) + "=" + URLEncoder.encode("人保车险", Consts.UTF_8.name())
		+ "&" + URLEncoder.encode("#insurance#", Consts.UTF_8.name()) + "=" + URLEncoder.encode("交强险950元，车船税360元，商业险6488.93元(商业险包含:车损12.8万、三者50万、司机1万、乘客1万、玻璃[国产]、不计免赔) ", Consts.UTF_8.name())
		+ "&" + URLEncoder.encode("#price#", Consts.UTF_8.name()) + "=" + URLEncoder.encode("7798.93", Consts.UTF_8.name())
		+ "&" + URLEncoder.encode("#name#", Consts.UTF_8.name()) + "=" + URLEncoder.encode("张辛林", Consts.UTF_8.name())
		+ "&" + URLEncoder.encode("#mobile#", Consts.UTF_8.name()) + "=" + URLEncoder.encode("13105716369", Consts.UTF_8.name());
		params.put("tpl_value", tpl_value);
		params.put("mobile", "13105716369");
		
		HttpPost post = new HttpPost(URI_TPL_SEND_SMS);
		if (params != null) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> param : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
				paramList.add(pair);
			}
			post.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
		}
		proxy.asyncRequest(post, new AsyncRespHandler() {
			@Override
			public void failed(Exception ex) {
				ex.printStackTrace();
			}
			
			@Override
			public void completed(HttpResponse response) {
				StatusLine statusLine = response.getStatusLine();
				HttpEntity entity = response.getEntity();
				try {
					if (statusLine.getStatusCode() >= 300) 
						throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase() + " --- " + EntityUtils.toString(entity));
					if (null == entity)
						throw new ClientProtocolException("Response contains no content");
					
					String result = EntityUtils.toString(entity);
					System.out.println(result);
					EntityUtils.consume(entity);
				} catch (ParseException | IOException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void cancelled() {
				System.out.println("cancelled");
			}
		});
	}
}
