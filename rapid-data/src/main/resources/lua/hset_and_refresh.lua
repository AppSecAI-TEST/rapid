local old = redis.call("hget", KEYS[1], ARGV[1])
redis.call("hset", KEYS[1], ARGV[3])
redis.call("pexpire", KEYS[1], ARGV[2])
return old