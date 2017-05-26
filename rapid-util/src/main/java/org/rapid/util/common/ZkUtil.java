package org.rapid.util.common;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;
import org.rapid.util.common.serializer.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper 的工具类
 * 
 * @author ahab
 */
public class ZkUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ZkUtil.class);

	public static <T> T readJson(ZkClient zkClient, String path, Class<T> clazz) { 
		byte[] buffer = zkClient.readData(path, true);
		if (null == buffer) {
			logger.warn("Zookeeper {} has no data to read!", path);
			return null;
		}
		return SerializeUtil.JsonUtil.GSON.fromJson(new String(buffer, Consts.UTF_8), clazz);
	}
	
	public static void writeJson(ZkClient zkClient, Object config, String path) {
		byte[] data = SerializeUtil.JsonUtil.GSON.toJson(config).getBytes(Consts.UTF_8);
		if (!zkClient.exists(path))
			zkClient.create(path, data, CreateMode.PERSISTENT);
		else
			zkClient.writeData(path, data);
	}
}
