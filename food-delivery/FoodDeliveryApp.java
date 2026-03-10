import controller.FoodDeliveryController;
import enums.FoodCategory;
import enums.OrderStatus;
import model.Cart;
import model.DeliveryAgent;
import model.MenuItem;
import model.Order;
import model.OrderItem;
import model.Restaurant;
import model.User;

import java.util.List;

public class FoodDeliveryApp {
    public static void main(String[] args) {
        FoodDeliveryController controller = new FoodDeliveryController();

        // ── Setup restaurants ──────────────────────────────────────────
        Restaurant pizzaPalace = new Restaurant("R1", "Pizza Palace", "123 MG Road", "Italian");
        controller.addRestaurant(pizzaPalace);

        MenuItem margherita = new MenuItem("M1", "Margherita Pizza", 299.0, FoodCategory.VEG);
        MenuItem carbonara  = new MenuItem("M2", "Pasta Carbonara",  249.0, FoodCategory.NON_VEG);
        MenuItem tiramisu   = new MenuItem("M3", "Tiramisu",         149.0, FoodCategory.VEG);
        controller.addMenuItem("R1", margherita);
        controller.addMenuItem("R1", carbonara);
        controller.addMenuItem("R1", tiramisu);

        // ── Register delivery agents ───────────────────────────────────
        DeliveryAgent rahul = new DeliveryAgent("DA1", "Rahul", "9876543210");
        DeliveryAgent amit  = new DeliveryAgent("DA2", "Amit",  "9876543211");
        controller.registerDeliveryAgent(rahul);
        controller.registerDeliveryAgent(amit);

        // ── Create users ───────────────────────────────────────────────
        User priya = new User("U1", "Priya", "priya@example.com", "9876543212", "456 Park Ave");
        User rohan = new User("U2", "Rohan", "rohan@example.com", "9876543213", "789 Lake View");

        // ── Order 1: Priya places an order using Cart ──────────────────
        System.out.println("═══════════════════════════════════════════");
        System.out.println(" ORDER 1: Priya's order");
        System.out.println("═══════════════════════════════════════════");

        Cart priyaCart = new Cart(priya);
        priyaCart.setRestaurant(pizzaPalace);
        priyaCart.addItem(margherita, 2);
        priyaCart.addItem(tiramisu, 1);

        Order order1 = controller.placeOrder(priya, priyaCart);
        System.out.printf("Total: ₹%.2f%n%n", order1.getTotalAmount());

        System.out.println("--- Status Updates ---");
        controller.updateOrderStatus(order1.getId(), OrderStatus.ACCEPTED);
        controller.updateOrderStatus(order1.getId(), OrderStatus.PREPARING);
        controller.updateOrderStatus(order1.getId(), OrderStatus.READY_FOR_PICKUP);
        controller.updateOrderStatus(order1.getId(), OrderStatus.PICKED_UP);
        controller.updateOrderStatus(order1.getId(), OrderStatus.DELIVERED);

        // ── Order 2: Rohan places an order ────────────────────────────
        System.out.println();
        System.out.println("═══════════════════════════════════════════");
        System.out.println(" ORDER 2: Rohan's order");
        System.out.println("═══════════════════════════════════════════");

        Order order2 = controller.placeOrder(rohan, "R1",
            List.of(new OrderItem(carbonara, 1)));
        System.out.printf("Total: ₹%.2f%n%n", order2.getTotalAmount());

        System.out.println("--- Cancel Order ---");
        controller.cancelOrder(order2.getId());

        // ── Track order ────────────────────────────────────────────────
        System.out.println();
        System.out.println("═══════════════════════════════════════════");
        System.out.println(" TRACKING");
        System.out.println("═══════════════════════════════════════════");
        Order tracked = controller.trackOrder(order1.getId());
        System.out.println(tracked);

        // ── Browse restaurants ─────────────────────────────────────────
        System.out.println();
        System.out.println("═══════════════════════════════════════════");
        System.out.println(" SEARCH: 'pizza'");
        System.out.println("═══════════════════════════════════════════");
        controller.searchRestaurants("pizza").forEach(r ->
            System.out.println("  " + r.getName() + " - " + r.getCuisine()));
    }
}
