local data = redis.call("smembers", KEYS[2])
if (next(data))
then
	for i = 2, #(KEYS), 1
	do
		redis.call("del", KEYS[i])
	end
	redis.call("hdel", KEYS[1], unpack(data))
end
