package com.lotto.web.service.caffeine;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CaffeineService {
    private final CacheManager cacheManager;

    private final static String CACHE_NAME = "AUTH_CACHE";

    public <K, V> CaffeineCache createCache() {
        return (CaffeineCache) cacheManager.getCache(CACHE_NAME);
    }

    public <K, V> V get(K key) {
        return (V) createCache().get(key).get();
    }

    public <K, V> void put(K key, V value) {
        createCache().put(key, value);
    }

    public <K> void remove(K key) {
        createCache().evict(key);
    }
}
