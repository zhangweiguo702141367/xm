package com.zh.dubbo.config;

import com.google.common.collect.Sets;
import com.zh.dubbo.utils.SerializeUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;



public class ShrioRedisCache<K, V> implements Cache<K, V> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private RedisTemplate<byte[], V> redisTemplate;
	private String prefix = "xiaomei_login:";

	public ShrioRedisCache(RedisTemplate<byte[], V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public ShrioRedisCache(RedisTemplate<byte[], V> redisTemplate, String prefix) {
		this(redisTemplate);
		this.prefix = prefix;
	}

	@Override
	public V get(K key) throws CacheException {
		if(logger.isDebugEnabled()) {
			logger.debug("Key: {}", key);
		}
		if(key == null) {
			return null;
		}
		
		byte[] bkey = getByteKey(key);
		return redisTemplate.opsForValue().get(bkey);
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if(logger.isDebugEnabled()) {
			logger.debug("Key: {}, value: {}", key, value);
		}
		
		if(key == null || value == null) {
			return null;
		}
		
		byte[] bkey = getByteKey(key);
		redisTemplate.opsForValue().set(bkey, value);
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		if(logger.isDebugEnabled()) {
			logger.debug("Key: {}", key);
		}
		
		if(key == null) {
			return null;
		}
		
		byte[] bkey = getByteKey(key);
		ValueOperations<byte[], V> vo = redisTemplate.opsForValue();
		V value = vo.get(bkey);
		redisTemplate.delete(bkey);
		return value;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.getConnectionFactory().getConnection().flushDb();
	}

	@Override
	public int size() {
		Long len = redisTemplate.getConnectionFactory().getConnection().dbSize();
		return len.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		byte[] bkey = (prefix+"*").getBytes();
		Set<byte[]> set = redisTemplate.keys(bkey);
		Set<K> result = Sets.newHashSet();
		
		if(CollectionUtils.isEmpty(set)) {
			return Collections.emptySet();
		}
		
		for(byte[] key: set) {
			result.add((K)key);
		}
		return result;
	}

	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
		List<V> values = new ArrayList<>(keys.size());
		for(K k: keys) {
			byte[] bkey = getByteKey(k);
			values.add(redisTemplate.opsForValue().get(bkey));
		}
		return values;
	}
	
	private byte[] getByteKey(K key){
		if(key instanceof String){
			String preKey = this.prefix + key;
    		return preKey.getBytes();
    	}else{
    		return SerializeUtils.serialize(key);
    	}
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}