package org.rapid.utilt.net.http;

import org.apache.http.client.methods.HttpGet;
import org.rapid.util.net.http.HttpProxy;
import org.rapid.util.net.http.SyncHttpAdapter;
import org.rapid.util.net.http.handler.SyncStrRespHandler;

public class HttpProxyTest {

	public static void main(String[] args) throws Exception {
		HttpProxy proxy = new HttpProxy();
		
		SyncHttpAdapter adapter = new SyncHttpAdapter();
		proxy.setSyncHttp(adapter);
		proxy.init();
		
		String result = proxy.syncRequest(new HttpGet("http://www.baidu.com"), SyncStrRespHandler.INSTANCE);
		System.out.println(result);
	}
}
