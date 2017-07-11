local len = #(ARGV)
if (len > 0)
then
	local hindex = 1
	local sindex = 1
	local data = {}
	local data1 = {}
	for i = 1, len, 2
	do
		data[hindex] = ARGV[i]
		data[hindex + 1] = ARGV[i + 1]
		data1[sindex] = ARGV[i]
		hindex = hindex + 2
		sindex = sindex + 1
	end
	redis.call("hmset", KEYS[1], unpack(data))
	for i = 2, #(KEYS), 1
	do
		redis.call("sadd", KEYS[i], unpack(data1))
	end
end