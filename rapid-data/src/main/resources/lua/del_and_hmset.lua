redis.call("del", KEYS[1])
return redis.call("hmset", KEYS[1], unpack(ARGV))