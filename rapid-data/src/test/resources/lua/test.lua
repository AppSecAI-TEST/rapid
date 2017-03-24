local len = #(ARGV)
local index = 1
local data = {}
local data1 = {}
for i=1, len, 3
do
	data[index] = ARGV[i]
	data[index + 1] = ARGV[i + 2]
	data1[index] = ARGV[i + 1]
	data1[index + 1] = ARGV[i]
	index = index + 2 
end
redis.call("hmset", KEYS[1], unpack(data))
redis.call("hmset", KEYS[2], unpack(data1))
return index