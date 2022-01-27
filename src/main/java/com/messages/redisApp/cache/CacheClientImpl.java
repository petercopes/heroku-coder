package com.messages.redisApp.cache;

import java.time.Duration;

import javax.annotation.PostConstruct;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.messages.redisApp.Utils.Constants;
import com.messages.redisApp.config.ApplicationProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CacheClientImpl<T> implements CacheClient<T> {

	 private final RedisTemplate<String, T> redisTemplate;
	 private final ApplicationProperties properties;
	 private HashOperations<String, String, String> hashOperations;
	 private final ObjectMapper mapper;
	
	@PostConstruct
	 void setHashOperations() {
        hashOperations = this.redisTemplate.opsForHash();
        this.redisTemplate.expire(Constants.NAME_MAP_MESSAGE, Duration.ofMillis(properties.getTimeOfLife()));
    }

	@Override
	public T save(String key, T data) {
		try {
            hashOperations.put(Constants.NAME_MAP_MESSAGE, key, serializeItem(data));
            return data;
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        }
        return data;
	}

    @Override
    public T recover(String key, Class<T> classValue) {
        try {
            var item = hashOperations.get(Constants.NAME_MAP_MESSAGE, key);
            if (item == null) return null;
            return deSerializeItem(item, classValue);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to Message", e);
        }
        return null;
    }

    private String serializeItem(T item) throws JsonProcessingException {
        var serializedItem = mapper.writeValueAsString(item);
        log.info("Mensaje en formato String: {}", serializedItem);
        return serializedItem;
    }

    private T deSerializeItem(String jsonInput, Class<T> classValue) throws JsonProcessingException {
        return mapper.readValue(jsonInput, classValue);
    }
}
