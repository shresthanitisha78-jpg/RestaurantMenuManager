package restaurantmenumanager;

import java.util.ArrayList;

public class MenuManager {

    // ArrayList stores all MenuItem objects
    private final ArrayList<MenuItem> menuItems;

    // Constructor
    public MenuManager() {
        menuItems = new ArrayList<>();
    }

    // =====================================================
    // CREATE - Add Menu Item
    // =====================================================
    public void addItem(MenuItem item) {

        if (item == null) {
            System.out.println("Error: Menu item cannot be null.");
            return;
        }

        // Check for duplicate ID
        if (searchItem(item.getId()) != null) {
            System.out.println("Error: Menu item with ID "
                    + item.getId() + " already exists.");
            return;
        }

        menuItems.add(item);

        System.out.println("Menu item added successfully.");
    }

    // =====================================================
    // READ - View All Menu Items
    // =====================================================
    public void viewItems() {

        if (menuItems.isEmpty()) {
            System.out.println("No menu items available.");
            return;
        }

        System.out.println("\n========== RESTAURANT MENU ==========");

        for (MenuItem item : menuItems) {
            System.out.println("-------------------------------------");
            System.out.println(item.getDetails());
        }

        System.out.println("-------------------------------------");
    }

    // =====================================================
    // READ - Search Menu Item by ID
    // =====================================================
    public MenuItem searchItem(int id) {

        for (MenuItem item : menuItems) {

            if (item.getId() == id) {
                return item;
            }
        }

        return null;
    }

    // =====================================================
    // UPDATE - Update Menu Item by ID
    // =====================================================
    public boolean updateItem(
            int id,
            String name,
            double price,
            String category,
            boolean available) {

        MenuItem item = searchItem(id);

        if (item == null) {
            System.out.println("Menu item with ID "
                    + id + " not found.");
            return false;
        }

        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return false;
        }

        if (price < 0) {
            System.out.println("Error: Price cannot be negative.");
            return false;
        }

        if (category == null || category.trim().isEmpty()) {
            System.out.println("Error: Category cannot be empty.");
            return false;
        }

        item.setName(name.trim());
        item.setPrice(price);
        item.setCategory(category.trim());
        item.setAvailable(available);

        System.out.println("Menu item updated successfully.");

        return true;
    }

    // =====================================================
    // DELETE - Delete Menu Item by ID
    // =====================================================
    public boolean deleteItem(int id) {

        MenuItem item = searchItem(id);

        if (item == null) {
            System.out.println("Menu item with ID "
                    + id + " not found.");
            return false;
        }

        menuItems.remove(item);

        System.out.println("Menu item deleted successfully.");

        return true;
    }

    // =====================================================
    // GET - Return All Menu Items
    // =====================================================
    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    // =====================================================
    // GET - Number of Menu Items
    // =====================================================
    public int getItemCount() {
        return menuItems.size();
    }

    // =====================================================
    // CHECK - Check Whether Menu Is Empty
    // =====================================================
    public boolean isEmpty() {
        return menuItems.isEmpty();
    }
}
