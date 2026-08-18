package restaurantmenumanager;

public class DrinkItem extends MenuItem {

    // Drink-specific attribute
    private String drinkSize;

    // Constructor - calls the parent (MenuItem) constructor using super()
    public DrinkItem(int id, String name, double price, String category, boolean available, String drinkSize) {
        super(id, name, price, category, available);
        this.drinkSize = drinkSize;
    }

    // Getter and Setter for drinkSize
    public String getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(String drinkSize) {
        this.drinkSize = drinkSize;
    }

    // Implementation of the abstract method from MenuItem (Polymorphism)
    @Override
    public String getDetails() {
        return "Drink Item: " + getName() + " | Category: " + getCategory() +
               " | Price: $" + getPrice() + " | Size: " + drinkSize +
               " | Available: " + isAvailable();
    }
}