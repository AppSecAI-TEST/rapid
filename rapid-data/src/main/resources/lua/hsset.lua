redis.call("hset", KEYS[1], ARGV[1], ARGV[2])
local len = #(KEYS)
if (len > 1)
then
	for i = 2, len, 1
	do
		redis.call("sadd", KEYS[i], ARGV[1])
	end
end