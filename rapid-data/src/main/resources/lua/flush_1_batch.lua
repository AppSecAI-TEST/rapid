local len = #(ARGV)
if (0 == len)
then
	return
end
local hindex = 1
local sindex = 1
local data = {}
local data1 = {}
for i = 1, len, 3
do
	data[hindex] = ARGV[i]
	data[hindex + 1] = ARGV[i + 1]
	data1[sindex] = ARGV[i + 2]
	data1[sindex + 1] = ARGV[i]
	hindex = hindex + 2
	sindex = sindex + 2
end
redis.call("hmset", KEYS[1], unpack(data))
redis.call("hmset", KEYS[2], unpack(data1))