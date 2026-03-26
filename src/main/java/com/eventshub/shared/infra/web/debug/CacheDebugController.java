package com.eventshub.shared.infra.web.debug;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Development-only endpoint for inspecting cache contents.
 * Should be removed or secured before deploying to production.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("v1/cache")
public class CacheDebugController {

    private final CacheManager manager;

    @GetMapping("/{name}")
    public Map<Object, Object> inspect(@PathVariable String name) {
        CaffeineCache cache = (CaffeineCache) manager.getCache(name);
        if (cache == null) return Map.of();
        return cache.getNativeCache().asMap();
    }

    @GetMapping("/{cacheName}/stats")
    public CacheStatsResponse stats(@PathVariable String cacheName) {
        CaffeineCache cache = (CaffeineCache) manager.getCache(cacheName);
        if (cache == null) return null;
        return CacheStatsResponse.from(cache.getNativeCache().stats());
    }
}
