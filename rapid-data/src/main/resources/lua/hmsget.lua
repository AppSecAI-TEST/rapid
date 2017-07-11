local set = redis.call("smembers", KEYS[2])
if (next(set))
then
	return redis.call("hmget", KEYS[1], unpack(set))
end
return nil