package restaurantmenumanager;

public class FoodItem extends MenuItem {

    // Food-specific attribute
    private String dietaryType;

    // Constructor - calls the parent (MenuItem) constructor using super()
    public FoodItem(int id, String name, double price, String category, boolean available, String dietaryType) {
        super(id, name, price, category, available);
        this.dietaryType = dietaryType;
    }

    // Getter and Setter for dietaryType
    public String getDietaryType() {
        return dietaryType;
    }

    public void setDietaryType(String dietaryType) {
        this.dietaryType = dietaryType;
    }

    // Implementation of the abstract method from MenuItem (Polymorphism)
    @Override
    public String getDetails() {
        return "Food Item: " + getName() + " | Category: " + getCategory() +
               " | Price: $" + getPrice() + " | Dietary Type: " + dietaryType +
               " | Available: " + isAvailable();
    }
}