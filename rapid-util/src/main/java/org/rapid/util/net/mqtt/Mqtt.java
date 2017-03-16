package org.rapid.util.net.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Mqtt {
	
	private String clientId;
	private String userName;
	private String password;
	private String serverURI;
	private boolean cleanSession;
	private int keepAliveInterval;
	private MqttCallback callback;
	private MqttClientPersistence persistence;
	private SubscribeAction subscribeAction;
	
	private MqttClient client;
	
	public void start() throws MqttException { 
		client = new MqttClient(serverURI, clientId, persistence);
		MqttConnectOptions options = new MqttConnectOptions();
		options.setUserName(userName);
		options.setPassword(password.toCharArray());
		options.setServerURIs(new String[]{ serverURI });
		options.setCleanSession(cleanSession);
		options.setKeepAliveInterval(keepAliveInterval);
		client.setCallback(callback);
		client.connect(options);
		if (null != subscribeAction)
			subscribeAction.subscribe(client);
	}
	
	public void stop() throws MqttException { 
		client.disconnect();
		persistence.clear();
	}
	
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setServerURI(String serverURI) {
		this.serverURI = serverURI;
	}
	
	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}
	
	public void setKeepAliveInterval(int keepAliveInterval) {
		this.keepAliveInterval = keepAliveInterval;
	}
	
	public void setCallback(MqttCallback callback) {
		this.callback = callback;
	}
	
	public void setPersistence(MqttClientPersistence persistence) {
		this.persistence = persistence;
	}
	
	public void setSubscribeAction(SubscribeAction subscribeAction) {
		this.subscribeAction = subscribeAction;
	}
}
