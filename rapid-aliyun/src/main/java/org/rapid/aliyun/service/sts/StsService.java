package org.rapid.aliyun.service.sts;

import org.rapid.aliyun.AliyunConfig;
import org.rapid.aliyun.AliyunOptions;
import org.rapid.aliyun.policy.Policy;
import org.rapid.util.common.serializer.SerializeUtil;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

public class StsService {
	
	private AliyunConfig config;
	private DefaultAcsClient client;
	
	public void init() {
		IClientProfile profile = DefaultProfile.getProfile(
				config.getConfig(AliyunOptions.STS_REGION), 
				config.getConfig(AliyunOptions.STS_ACCESS_KEY_ID),  
				config.getConfig(AliyunOptions.STS_ACCESS_KEY_SECRET));
		this.client = new DefaultAcsClient(profile);
	}
	
	public AssumeRoleResponse assumeRole(String roleArn, String roleSessionName) {
		AssumeRoleRequest request = _request(roleArn, roleSessionName);
		try {
			return client.getAcsResponse(request);
		} catch (ClientException e) {
			throw new RuntimeException("Aliyun sts - AssumeRole invoke failure!", e);
		}
	}
	
	public AssumeRoleResponse assumeRole(String roleArn, String roleSessionName, int durationSeconds) {
		AssumeRoleRequest request = _request(roleArn, roleSessionName);
		request.setDurationSeconds(Long.valueOf(durationSeconds));
		try {
			return client.getAcsResponse(request);
		} catch (ClientException e) {
			throw new RuntimeException("Aliyun sts - AssumeRole invoke failure!", e);
		}
	}

	public AssumeRoleResponse assumeRole(String roleArn, String roleSessionName, Policy policy) {
		AssumeRoleRequest request = _request(roleArn, roleSessionName);
		request.setPolicy(SerializeUtil.JsonUtil.GSON.toJson(policy));
		try {
			return client.getAcsResponse(request);
		} catch (ClientException e) {
			throw new RuntimeException("Aliyun sts - AssumeRole invoke failure!", e);
		}
	}
	
	public AssumeRoleResponse assumeRole(String roleArn, String roleSessionName, Policy policy, int durationSeconds) {
		AssumeRoleRequest request = _request(roleArn, roleSessionName);
		request.setPolicy(SerializeUtil.JsonUtil.GSON.toJson(policy));
		request.setDurationSeconds(Long.valueOf(durationSeconds));
		try {
			return client.getAcsResponse(request);
		} catch (ClientException e) {
			throw new RuntimeException("Aliyun sts - AssumeRole invoke failure!", e);
		}
	}
	
	private AssumeRoleRequest _request(String roleArn, String roleSessionName) {
		AssumeRoleRequest request = new AssumeRoleRequest();
		request.setVersion(config.getConfig(AliyunOptions.STS_VERSION));
		request.setMethod(MethodType.POST);
		request.setProtocol(ProtocolType.HTTPS);
		request.setRoleArn(roleArn);
		request.setRoleSessionName(roleSessionName);
		return request;
	}
	
	public void dispose() {
		this.client = null;
	}
	
	public void setConfig(AliyunConfig config) {
		this.config = config;
	}
}
