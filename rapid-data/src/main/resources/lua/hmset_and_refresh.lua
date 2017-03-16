redis.call("hmset", KEYS[1], unpack(ARGV, 2))
redis.call("pexpire", KEYS[1], ARGV[1])
