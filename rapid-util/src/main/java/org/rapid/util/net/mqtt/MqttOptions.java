package org.rapid.util.net.mqtt;

import org.rapid.util.common.consts.conveter.Str2BoolConstConverter;
import org.rapid.util.common.consts.conveter.Str2IntConstConverter;
import org.rapid.util.common.consts.conveter.Str2StrConstConverter;
import org.rapid.util.common.uuid.AlternativeJdkIdGenerator;

public interface MqttOptions {

	/**
	 * MQTT 客户端的 clientId，如果不设置则默认为一个 uuid
	 */
	final Str2StrConstConverter CLIENT_ID							= new Str2StrConstConverter("MQTT.ClientId") {
		public String value() {
			return AlternativeJdkIdGenerator.INSTANCE.generateId().toString();
		};
	};
	
	final Str2StrConstConverter SERVER_URI							= new Str2StrConstConverter("MQTT.ServerURI");
	final Str2BoolConstConverter CLEAN_SESSION						= new Str2BoolConstConverter("MQTT.CleanSession", false);
	final Str2IntConstConverter KEEP_ALIVE_INTERVAL					= new Str2IntConstConverter("MQTT.KeepAliveInterval", 60);
	/**
	 * 如果想要开启文件系统来存储消息，则需要设置该值为 false
	 */
	final Str2BoolConstConverter CACHE_MEMORY						= new Str2BoolConstConverter("MQTT.cacheMemory", true);
	/**
	 * 当且仅当 MQTT.cacheMemory = false 时该属性才有效，表示消息存储的文件路径，默认为  user.dir 属性定义的路径
	 */
	final Str2StrConstConverter CACHE_FILE							= new Str2StrConstConverter("MQTT.CacheFile");
}
