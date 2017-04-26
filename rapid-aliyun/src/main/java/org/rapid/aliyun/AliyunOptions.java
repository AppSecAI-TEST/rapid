package org.rapid.aliyun;

import org.rapid.util.common.consts.conveter.Str2LongConstConverter;
import org.rapid.util.common.consts.conveter.Str2StrConstConverter;

public interface AliyunOptions {
	
	final Str2StrConstConverter  STS_REGION								= new Str2StrConstConverter("aliyun.sts.region");
	final Str2StrConstConverter  STS_VERSION							= new Str2StrConstConverter("aliyun.sts.edition");
	final Str2StrConstConverter  STS_ACCESS_KEY_ID						= new Str2StrConstConverter("aliyun.sts.access.key.id");
	final Str2StrConstConverter  STS_ACCESS_KEY_SECRET					= new Str2StrConstConverter("aliyun.sts.access.key.secret");
	final Str2StrConstConverter  STS_ROLE_ARN							= new Str2StrConstConverter("aliyun.sts.role.arn");
	final Str2LongConstConverter STS_TOKEN_EXPIRATION					= new Str2LongConstConverter("aliyun.sts.token.expiration");
	final Str2StrConstConverter  OSS_BUCKET								= new Str2StrConstConverter("aliyun.oss.bucket");
	final Str2StrConstConverter  OSS_ENDPOINT							= new Str2StrConstConverter("aliyun.oss.endpoint");
	final Str2StrConstConverter  ACCESS_KEY_ID							= new Str2StrConstConverter("aliyun.access.key.id");
	final Str2StrConstConverter  ACCESS_KEY_SECRET						= new Str2StrConstConverter("aliyun.access.key.secret");
}
