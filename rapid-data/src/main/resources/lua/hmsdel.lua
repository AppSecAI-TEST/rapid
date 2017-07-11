for i = 2, #(KEYS), 1
do
	redis.call("srem", KEYS[i], unpack(ARGV))
end
redis.call("hdel", KEYS[1], unpack(ARGV))