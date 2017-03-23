redis.call("del", KEYS[1])
return redis.call("sadd", KEYS[1], unpack(ARGV))