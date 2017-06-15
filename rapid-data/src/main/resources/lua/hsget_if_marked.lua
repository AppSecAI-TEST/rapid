local flag = redis.call("hexists", KEYS[1], ARGV[1])
if (1 == flag)
then
	local set = redis.call("smembers", KEYS[2])
	if ((2 == #(KEYS)) or (not next(set)))
	then
		return set
	end
	return redis.call("hmget", KEYS[3], unpack(set))
else
	return nil
end