package restaurantmenumanager;

public class MenuManagerTest {

    public static void main(String[] args) {

        // Create MenuManager
        MenuManager manager = new MenuManager();

        // ==========================================
        // 1. CREATE - Add Food Item
        // ==========================================

        FoodItem food = new FoodItem(
                1,
                "Chicken Burger",
                12.50,
                "Main",
                true,
                "Non-Vegetarian"
        );

        manager.addItem(food);

        // ==========================================
        // 2. CREATE - Add Drink Item
        // ==========================================

        DrinkItem drink = new DrinkItem(
                2,
                "Coke",
                4.00,
                "Beverage",
                true,
                "Large"
        );

        manager.addItem(drink);

        // ==========================================
        // 3. READ - View All Items
        // ==========================================

        System.out.println("\n--- VIEW ALL ITEMS ---");
        manager.viewItems();

        // ==========================================
        // 4. SEARCH - Search by ID
        // ==========================================

        System.out.println("\n--- SEARCH ITEM ---");

        MenuItem foundItem = manager.searchItem(1);

        if (foundItem != null) {
            System.out.println("Item found:");
            System.out.println(foundItem);
        } else {
            System.out.println("Item not found.");
        }

        // ==========================================
        // 5. UPDATE - Update Food Item
        // ==========================================

        System.out.println("\n--- UPDATE ITEM ---");

        boolean updated = manager.updateItem(
                1,
                "Chicken Cheese Burger",
                14.50,
                "Main Course",
                true
        );

        if (updated) {
            System.out.println("Update successful.");
        }

        // View after update
        manager.viewItems();

        // ==========================================
        // 6. DELETE - Delete Drink Item
        // ==========================================

        System.out.println("\n--- DELETE ITEM ---");

        boolean deleted = manager.deleteItem(2);

        if (deleted) {
            System.out.println("Delete successful.");
        }

        // ==========================================
        // 7. VIEW AGAIN
        // ==========================================

        System.out.println("\n--- MENU AFTER DELETE ---");

        manager.viewItems();

        // ==========================================
        // 8. DISPLAY ITEM COUNT
        // ==========================================

        System.out.println("\nTotal menu items: "
                + manager.getItemCount());

        // ==========================================
        // TEST COMPLETE
        // ==========================================

        System.out.println("\nMenuManager testing completed.");
    }
}
