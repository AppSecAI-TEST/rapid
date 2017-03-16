local data = redis.call("hgetall", KEYS[1])
redis.call("pexpire", KEYS[1], ARGV[1])
return data