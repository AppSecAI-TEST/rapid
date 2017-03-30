local page = tonumber(ARGV[1])
local pageSize = tonumber(ARGV[2])
local total = redis.call("zcard", KEYS[1])
local totalPage = math.floor(total / pageSize)
local mod = math.mod(total, pageSize)
if (mod ~= 0)
then
	totalPage = totalPage + 1
end
if (page > totalPage)
then
	page = totalPage
end
if (page <= 0)
then
	return nil
end
local start = (page - 1) * pageSize
local stop = start + pageSize - 1
local set = redis.call(ARGV[3], KEYS[1], start, stop)
local data = redis.call("hmget", KEYS[2], unpack(set))
table.insert(data, 1, total)
return data

