local data = redis.call("hget", KEYS[1], ARGV[1])
if (data)
then
	local cdata = cjson.decode(data)
	local expire = cdata["expire"]
	if (expire >= ARGV[2])
	then
		redis.call("hdel", KEYS[1], ARGV[1])
		return nil
	else
		return data
	end
else
	return nil
end