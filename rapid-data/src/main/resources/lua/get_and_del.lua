local data = redis.call("get", KEYS[1])
redis.call("del", KEYS[1])
return data