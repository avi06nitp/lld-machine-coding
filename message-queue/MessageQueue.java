import messagequeue.enums.ExchangeType;
import messagequeue.models.Broker;
import messagequeue.models.Consumer;
import messagequeue.models.Producer;
import messagequeue.registry.ConsumerRegistry;

public class MessageQueue {

    // ── ANSI styling (same palette as the Snake & Ladder / LRU consoles) ──
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
        banner("MESSAGE  QUEUE  TESTS");

        fanOutDeliversToAll();
        directDeliversOnlyToMatchingConsumer();
        directWithNoMatchDeliversToNone();
        topicDeliversToConsumersContainingKey();
        consumerAckRemovesHead();
        producerPublishesThroughBroker();
        brokerIsSingleton();

        summary();
    }

    // ── Scenario 1: FANOUT broadcasts to every registered consumer ──
    private static void fanOutDeliversToAll() {
        section("FANOUT delivers to all consumers");
        reset();
        Consumer a = consumer("a");
        Consumer b = consumer("b");
        Consumer c = consumer("c");

        Broker.getInstance().sendMessage("hello", ExchangeType.FANOUT, "ignored-key");

        check("a received it", a.getMessages().contains("hello"));
        check("b received it", b.getMessages().contains("hello"));
        check("c received it", c.getMessages().contains("hello"));
        checkEquals("each mailbox has exactly 1 message", a.getMessages().size(), 1);
    }

    // ── Scenario 2: DIRECT routes only to the consumer whose name == binding key ──
    private static void directDeliversOnlyToMatchingConsumer() {
        section("DIRECT delivers only to the matching consumer");
        reset();
        Consumer orders   = consumer("orders");
        Consumer payments = consumer("payments");

        // Deliberately a runtime (non-interned) string: routing must match by VALUE,
        // not by reference. This is what a real binding key (from config/network) looks like.
        String bindingKey = new String("orders");
        Broker.getInstance().sendMessage("order#1", ExchangeType.DIRECT, bindingKey);

        check("orders received it", orders.getMessages().contains("order#1"));
        checkEquals("payments got nothing", payments.getMessages().size(), 0);
    }

    // ── Scenario 3: DIRECT with an unknown key delivers to nobody ──
    private static void directWithNoMatchDeliversToNone() {
        section("DIRECT with no matching consumer delivers to none");
        reset();
        Consumer a = consumer("a");

        Broker.getInstance().sendMessage("lost", ExchangeType.DIRECT, "no-such-consumer");

        checkEquals("a got nothing", a.getMessages().size(), 0);
    }

    // ── Scenario 4: TOPIC delivers to consumers whose name contains the key ──
    private static void topicDeliversToConsumersContainingKey() {
        section("TOPIC delivers to consumers whose name contains the key");
        reset();
        Consumer ordersEu = consumer("orders.eu");
        Consumer ordersUs = consumer("orders.us");
        Consumer paymentsEu = consumer("payments.eu");

        Broker.getInstance().sendMessage("evt", ExchangeType.TOPIC, "orders");

        check("orders.eu received it", ordersEu.getMessages().contains("evt"));
        check("orders.us received it", ordersUs.getMessages().contains("evt"));
        checkEquals("payments.eu got nothing", paymentsEu.getMessages().size(), 0);
    }

    // ── Scenario 5: getMessages() reads the queue; ack() removes the head ──
    private static void consumerAckRemovesHead() {
        section("Consumer getMessages() reads, ack() removes");
        reset();
        Consumer x = consumer("x");
        x.add("m1");
        x.add("m2");

        checkEquals("queue holds both messages", x.getMessages().size(), 2);
        checkEquals("head is m1", x.getMessages().peek(), "m1");

        x.ack(); // prints "Acknowledged Message: m1" and removes the head

        checkEquals("ack() removed the head (size now 1)", x.getMessages().size(), 1);
        checkEquals("new head is m2", x.getMessages().peek(), "m2");

        x.ack(); // removes m2

        checkEquals("queue empty after acking both", x.getMessages().size(), 0);
    }

    // ── Scenario 6: Producer.publish routes through the broker ──
    private static void producerPublishesThroughBroker() {
        section("Producer.publish routes through the broker");
        reset();
        Consumer c = consumer("p");
        Producer producer = new Producer(1L, "prod-1");

        producer.publish("via-producer", ExchangeType.FANOUT, "any");

        check("consumer received producer's message", c.getMessages().contains("via-producer"));
    }

    // ── Scenario 7: Broker.getInstance() returns the same instance ──
    private static void brokerIsSingleton() {
        section("Broker is a singleton (same instance every call)");
        check("getInstance() == getInstance()", Broker.getInstance() == Broker.getInstance());
    }

    // ── test plumbing ──

    private static Consumer consumer(String name) {
        Consumer c = new Consumer(name);
        ConsumerRegistry.add(c);
        return c;
    }

    private static void reset() {
        // ConsumerRegistry is global static state — clear it so each test is isolated
        ConsumerRegistry.getConsumers().clear();
    }

    // ── pretty-printing + assertions ──

    private static void check(String label, boolean ok) {
        record(label, ok, null, null);
    }

    private static void checkEquals(String label, Object actual, Object expected) {
        boolean ok = (actual == null) ? expected == null : actual.equals(expected);
        record(label, ok, actual, expected);
    }

    private static void record(String label, boolean ok, Object actual, Object expected) {
        if (ok) passed++; else failed++;
        String mark = ok ? GREEN + "✓" + RESET : RED + "✗" + RESET;
        StringBuilder line = new StringBuilder("  " + mark + "  " + label);
        if (!ok && (actual != null || expected != null)) {
            line.append(DIM).append("  (got ").append(show(actual))
                .append(", expected ").append(show(expected)).append(")").append(RESET);
        }
        System.out.println(line);
    }

    private static String show(Object v) {
        return v == null ? "(null)" : "\"" + v + "\"";
    }

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

    private static void summary() {
        System.out.println();
        String color = failed == 0 ? GREEN : RED;
        String label = failed == 0 ? "ALL PASSED" : "SOME FAILED";
        System.out.println(color + "  " + label + " — " + passed + " passed, " + failed + " failed" + RESET);
        System.out.println();
    }

    private static String center(String s, int width) {
        if (s.length() >= width) return s;
        int left = (width - s.length()) / 2;
        int right = width - s.length() - left;
        return " ".repeat(left) + s + " ".repeat(right);
    }
}