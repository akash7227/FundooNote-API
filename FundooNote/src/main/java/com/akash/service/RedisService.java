package com.akash.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("redisService")
public class RedisService {
	private static Logger log = Logger.getLogger(RedisService.class);
	Map<String, String> hash = new HashMap<String, String>();

	public String redis(String Token, String id) {

		Jedis jedis = new Jedis("localhost");
		log.warn("in redis");
		hash.put("id", id);
		hash.put("Token", Token);
		jedis.hmset(id, hash);
		log.warn("check token details " + jedis.hgetAll(id));
		
		return "token store in redis";
	}
}
