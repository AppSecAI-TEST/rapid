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
		params.put("tpl_id", "1790102");
		String tpl_value = URLEncoder.encode("#code#", ENCODING) + "=" + URLEncoder.encode("5689", ENCODING);
		params.put("tpl_value", tpl_value);
		params.put("mobile", "13805730396");
		
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
