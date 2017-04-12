package org.rapid.aliyun;

import org.rapid.util.common.consts.conveter.Str2StrConstConverter;

public interface AliyunOptions {
	
	final Str2StrConstConverter STS_REGION								= new Str2StrConstConverter("aliyun.sts.region");
	final Str2StrConstConverter STS_VERSION								= new Str2StrConstConverter("aliyun.sts.version");
	final Str2StrConstConverter ACCESS_KEY_ID							= new Str2StrConstConverter("aliyun.accessKeyId");
	final Str2StrConstConverter ACCESS_KEY_SECRET						= new Str2StrConstConverter("aliyun.accessKeySecret");
}
