redis.call("del", KEYS[1])
local len = #ARGV
local total = 0
while (total < len)
do
	local t = {};
	local limit = math.min(len - total, 1000)
	for i = 1, limit, 1
	do
		t[i] = ARGV[total + i]
	end 
	redis.call("hmset", KEYS[1], unpack(t))
	total = total + limit
end

