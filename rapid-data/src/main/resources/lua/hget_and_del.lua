local data = redis.call("hget", KEYS[1], ARGV[1])
redis.call("hdel", KEYS[1], ARGV[1])
redis.call("pexpire", KEYS[1], ARGV[2])
return data
