package restaurantmenumanager;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

// Name of the file used to save menu data
private String fileName = "menu.txt";

// -------------------------------------------------
// SAVE MENU ITEMS TO FILE
// -------------------------------------------------
public void saveMenu(ArrayList<MenuItem> menuItems) {

try {
// Open menu.txt for writing
PrintWriter writer =
new PrintWriter(new FileWriter(fileName));

// Go through every menu item
for (MenuItem item : menuItems) {

// Save FoodItem
if (item instanceof FoodItem) {

FoodItem food = (FoodItem) item;

writer.println(
"FOOD," +
food.getId() + "," +
food.getName() + "," +
food.getPrice() + "," +
food.getCategory() + "," +
food.isAvailable() + "," +
food.getDietaryType()
);

// Save DrinkItem
} else if (item instanceof DrinkItem) {

DrinkItem drink = (DrinkItem) item;

writer.println(
"DRINK," +
drink.getId() + "," +
drink.getName() + "," +
drink.getPrice() + "," +
drink.getCategory() + "," +
drink.isAvailable() + "," +
drink.getDrinkSize()
);
}
}

// Close the file
writer.close();

System.out.println("Menu data saved successfully.");

} catch (IOException e) {

// Handles file writing errors
System.out.println("Error saving menu data.");
}
}


// -------------------------------------------------
// LOAD MENU ITEMS FROM FILE
// -------------------------------------------------
public ArrayList<MenuItem> loadMenu() {

// Create empty list for loaded menu items
ArrayList<MenuItem> menuItems = new ArrayList<>();

try {

// Open menu.txt for reading
BufferedReader reader =
new BufferedReader(new FileReader(fileName));

String line;

// Read the file one line at a time
while ((line = reader.readLine()) != null) {

try {

// Separate the information using commas
String[] parts = line.split(",", 7);

// Check that the line contains enough data
if (parts.length < 7) {
System.out.println("Invalid menu data skipped.");
continue;
}

// Read common MenuItem information
String type = parts[0];
int id = Integer.parseInt(parts[1]);
String name = parts[2];
double price = Double.parseDouble(parts[3]);
String category = parts[4];
boolean available =
Boolean.parseBoolean(parts[5]);

// Create FoodItem
if (type.equalsIgnoreCase("FOOD")) {

String dietaryType = parts[6];

FoodItem food = new FoodItem(
id,
name,
price,
category,
available,
dietaryType
);

menuItems.add(food);

// Create DrinkItem
} else if (type.equalsIgnoreCase("DRINK")) {

String drinkSize = parts[6];

DrinkItem drink = new DrinkItem(
id,
name,
price,
category,
available,
drinkSize
);

menuItems.add(drink);
}

} catch (NumberFormatException e) {

// Handles invalid ID or price
System.out.println(
"Invalid number in menu.txt. Line skipped."
);
}
}

reader.close();

System.out.println("Menu data loaded successfully.");

} catch (FileNotFoundException e) {

// Happens if menu.txt does not exist yet
System.out.println(
"menu.txt not found. Starting with an empty menu."
);

} catch (IOException e) {

// Handles other file errors
System.out.println("Error reading menu.txt.");
}

return menuItems;
}
}
