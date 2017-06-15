local data = redis.call("hget", KEYS[1], ARGV[1])
redis.call("hdel", KEYS[1], ARGV[1])
local len = #(KEYS)
if (len > 1)
then
	for i = 2, len, 1
	do
		redis.call("zrem", KEYS[i], ARGV[1])
	end
end
return data
