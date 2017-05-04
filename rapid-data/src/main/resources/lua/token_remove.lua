local uid = redis.call("hget", KEYS[2], ARGV[1])
if (uid) 
then
	local len = #(ARGV)
	if (len > 1)
	then
		local lock = string.gsub(ARGV[2], "{0}", uid)
		local isLock = redis.call("set", lock, ARGV[3], "NX", "PX", ARGV[4])
		if ((not isLock) or (isLock["ok"] ~= "OK"))
		then
			return -1
		end
	end
	redis.call("hdel", KEYS[1], uid)
	redis.call("hdel", KEYS[2], ARGV[1])
	return tonumber(uid)
else
	return -2
end