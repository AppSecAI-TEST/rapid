local val = redis.call("hget", KEYS[1], ARGV[1])
if (val)
then
	return redis.call("hget", KEYS[2], val)
else
	return nil
end
