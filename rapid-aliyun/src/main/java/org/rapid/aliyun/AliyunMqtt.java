package org.rapid.aliyun;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;
import org.rapid.util.net.mqtt.Mqtt;
import org.rapid.util.net.mqtt.MqttOptions;

public class AliyunMqtt extends Mqtt {
	
	private static final String CLIENT_SEPERATE			= "@@@";

	public AliyunMqtt(AliyunConfig config) {
		String clientId = config.getOptionalConfig(MqttOptions.CLIENT_ID);
		setClientId(clientId);
		setUserName(config.getConfig(AliyunOptions.ACCESS_KEY_ID));
		String accessKeySecret = config.getConfig(AliyunOptions.ACCESS_KEY_SECRET);
		byte[] buffer = HmacUtils.hmacSha1(accessKeySecret, clientId.split(CLIENT_SEPERATE)[0]);
		setPassword(Base64.encodeBase64String(buffer));
		setServerURI(config.getConfig(MqttOptions.SERVER_URI));
		setCleanSession(config.getOptionalConfig(MqttOptions.CLEAN_SESSION));
		setKeepAliveInterval(config.getOptionalConfig(MqttOptions.KEEP_ALIVE_INTERVAL));
		if (config.getOptionalConfig(MqttOptions.CACHE_MEMORY))
			setPersistence(new MemoryPersistence());
		else {
			String directory = config.getOptionalConfig(MqttOptions.CACHE_FILE);
			setPersistence(null == directory ? new MqttDefaultFilePersistence() : new MqttDefaultFilePersistence(directory));
		}
	}
}
