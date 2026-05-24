import keyvaluestore.models.LRUCache;

import java.util.Objects;

public class RedisKeyValueStore {

    // ── ANSI styling (same palette as the Snake & Ladder console) ──
    private static final String RESET   = "\033[0m";
    private static final String CYAN    = "\033[1;36m";
    private static final String YELLOW  = "\033[1;33m";
    private static final String GREEN   = "\033[1;32m";
    private static final String RED     = "\033[1;31m";
    private static final String MAGENTA = "\033[1;35m";
    private static final String DIM     = "\033[2m";

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        banner("REDIS  LRU  CACHE");

        // ── Scenario 1: basic put / get ──
        section("Scenario 1: Basic put / get (capacity = 2)");
        LRUCache c1 = new LRUCache(2);
        put(c1, "a", "apple");
        put(c1, "b", "banana");
        expectGet(c1, "a", "apple");
        expectGet(c1, "b", "banana");

        // ── Scenario 2: LRU eviction, with get() refreshing recency ──
        section("Scenario 2: LRU eviction + recency (capacity = 2)");
        LRUCache c2 = new LRUCache(2);
        put(c2, "a", "apple");
        put(c2, "b", "banana");
        note("touch 'a' so 'b' becomes least-recently-used");
        expectGet(c2, "a", "apple");      // promotes 'a'
        put(c2, "c", "cherry");           // evicts 'b'
        expectGet(c2, "a", "apple");      // survivor
        expectGet(c2, "b", null);         // evicted
        expectGet(c2, "c", "cherry");     // newest

        // ── Scenario 3: missing keys return null ──
        section("Scenario 3: Missing keys return null (capacity = 2)");
        LRUCache c3 = new LRUCache(2);
        put(c3, "x", "100");
        expectGet(c3, "y", null);
        expectGet(c3, "x", "100");
        expectGet(c3, "z", null);

        // ── Scenario 4: capacity = 1, every put evicts the previous ──
        section("Scenario 4: Capacity = 1 (each put evicts the previous)");
        LRUCache c4 = new LRUCache(1);
        put(c4, "a", "apple");
        put(c4, "b", "banana");           // evicts 'a'
        expectGet(c4, "a", null);
        expectGet(c4, "b", "banana");
        put(c4, "c", "cherry");           // evicts 'b'
        expectGet(c4, "b", null);
        expectGet(c4, "c", "cherry");

        // ── Scenario 5: updating an existing key (correct LRU semantics) ──
        section("Scenario 5: Update an existing key (capacity = 3)");
        LRUCache c5 = new LRUCache(3);
        put(c5, "k1", "v1");
        put(c5, "k2", "v2");
        put(c5, "k3", "v3");
        note("re-put 'k2' with a new value — should update in place, evict nothing");
        put(c5, "k2", "v2-new");
        expectGet(c5, "k1", "v1");        // must still be present
        expectGet(c5, "k2", "v2-new");    // value updated
        expectGet(c5, "k3", "v3");        // must still be present

        summary();
    }

    // ── pretty-printing helpers ──

    private static void banner(String title) {
        int width = 46;
        System.out.println();
        System.out.println(CYAN + "  ╔" + "═".repeat(width) + "╗");
        System.out.println("  ║" + center(title, width) + "║");
        System.out.println("  ╚" + "═".repeat(width) + "╝" + RESET);
    }

    private static void section(String title) {
        System.out.println();
        System.out.println(MAGENTA + "──[ " + title + " ]──" + RESET);
    }

    private static void note(String msg) {
        System.out.println(DIM + "  · " + msg + RESET);
    }

    private static void put(LRUCache cache, String key, String value) {
        cache.put(key, value);
        System.out.println(YELLOW + "  + PUT  " + RESET + pad(key) + DIM + "= " + RESET + value);
    }

    private static void expectGet(LRUCache cache, String key, String expected) {
        String actual = cache.get(key);
        boolean ok = Objects.equals(actual, expected);
        if (ok) passed++; else failed++;
        String mark = ok ? GREEN + "✓" + RESET : RED + "✗" + RESET;
        System.out.println(CYAN + "  ► GET  " + RESET + pad(key) + "-> " + show(actual)
                + "   " + mark + DIM + "  (expected " + show(expected) + ")" + RESET);
    }

    private static void summary() {
        System.out.println();
        String color = failed == 0 ? GREEN : RED;
        String label = failed == 0 ? "ALL PASSED" : "SOME FAILED";
        System.out.println(color + "  " + label + " — " + passed + " passed, " + failed + " failed" + RESET);
        System.out.println();
    }

    private static String pad(String s) {
        return String.format("%-6s", s);
    }

    private static String show(String v) {
        return v == null ? "(null)" : "\"" + v + "\"";
    }

    private static String center(String s, int width) {
        if (s.length() >= width) return s;
        int left = (width - s.length()) / 2;
        int right = width - s.length() - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }
}