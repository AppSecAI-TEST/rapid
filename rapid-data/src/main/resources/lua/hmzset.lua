local len = ARGV[1]
local keys = {}
for i = 2, len * 2, 2
do
	redis.call("hset", KEYS[1], ARGV[i], ARGV[i + 1])
	keys[i / 2] = ARGV[i]
end
for i = 2, #(KEYS), 1
do
	local data = {}
	local index = 1
	for j = 1, len, 1
	do
		data[index] = ARGV[(i - 2) * len + j + len * 2 + 1]
		data[index + 1] = keys[j]
		index = index + 2
	end
	redis.call("zadd", KEYS[i], unpack(data))
end