local keys = redis.call("zrange", KEYS[1], ARGV[1], ARGV[2])
if (next(keys))
then
	return redis.call("hmget", KEYS[2], unpack(keys))
else
	return nil
end