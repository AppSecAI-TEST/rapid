package org.rapid.util.net.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * 订阅操作：有些 topic 在订阅之前要先 unsubscribe 比如 qos 设置为 1 或者 2，但是又不想获取到离线消息，这时要先
 * 取消订阅，然后再订阅。但是有些消息我们全部都要收到，比如充值消息，必须要设置 qos = 2，而且客户端离线的消息在客户端上线
 * 之后也必须要收到。
 * 
 * @author ahab
 */
public interface SubscribeAction {

	void subscribe(MqttClient client);
}
