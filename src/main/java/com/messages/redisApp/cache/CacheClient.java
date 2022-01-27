package com.messages.redisApp.cache;

public interface CacheClient<T> {
	T save(String key, T data);
	T recover(String key, Class<T> classValue);

}
