package restaurantmenumanager;

public class DrinkItem extends MenuItem {

    // Drink-specific attribute
    private String drinkSize;

    // Constructor
    public DrinkItem(int id, String name, double price,
                     String category, boolean available,
                     String drinkSize) {

        super(id, name, price, category, available);

        this.drinkSize = drinkSize;
    }

    // Get drink size
    public String getDrinkSize() {
        return drinkSize;
    }

    // Set drink size
    public void setDrinkSize(String drinkSize) {
        this.drinkSize = drinkSize;
    }

    // Display DrinkItem information
    @Override
    public String toString() {
        return super.toString() +
                ", Type: Drink" +
                ", Drink Size: " + drinkSize;
    }
}
