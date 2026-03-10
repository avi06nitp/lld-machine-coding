package cache;

import model.Url;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * LRU Cache for URL mappings to avoid hitting the primary store on every lookup.
 * Evicts least recently used entries when capacity is exceeded.
 */
public class UrlCache {
    private final int capacity;
    private final Map<String, Url> cache;

    public UrlCache(int capacity) {
        this.capacity = capacity;
        // LinkedHashMap with access-order=true gives LRU semantics
        this.cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Url> eldest) {
                return size() > UrlCache.this.capacity;
            }
        };
    }

    public void put(String shortCode, Url url) {
        cache.put(shortCode, url);
    }

    public Optional<Url> get(String shortCode) {
        return Optional.ofNullable(cache.get(shortCode));
    }

    public void remove(String shortCode) {
        cache.remove(shortCode);
    }

    public int size() {
        return cache.size();
    }
}
