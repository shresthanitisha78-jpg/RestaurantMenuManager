package restaurantmenumanager;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final MenuManager menuManager =
            new MenuManager();

    private static final EvaluationManager evaluationManager =
            new EvaluationManager();

    private static final FileManager fileManager =
            new FileManager();

    public static void main(String[] args) {

        // Load existing data when application starts
        loadData();

        boolean running = true;

        while (running) {

            displayMainMenu();

            int choice = readInt(
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1:
                        addMenuItem();
                        break;

                    case 2:
                        menuManager.viewItems();
                        break;

                    case 3:
                        searchMenuItem();
                        break;

                    case 4:
                        updateMenuItem();
                        break;

                    case 5:
                        deleteMenuItem();
                        break;

                    case 6:
                        evaluateMenuItem();
                        break;

                    case 7:
                        showFeedback();
                        break;

                    case 8:
                        showRewardPenalty();
                        break;

                    case 9:
                        saveData();
                        break;

                    case 10:
                        saveData();
                        running = false;
                        System.out.println(
                                "Thank you for using Restaurant Menu Manager."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid menu choice. "
                                        + "Please choose 1-10."
                        );
                }

            } catch (Exception e) {

                System.out.println(
                        "An unexpected error occurred: "
                                + e.getMessage()
                );
            }
        }

        scanner.close();
    }

    // =====================================================
    // MAIN MENU
    // =====================================================

    private static void displayMainMenu() {

        System.out.println();
        System.out.println(
                "===== RESTAURANT MENU MANAGER ====="
        );
        System.out.println("1. Add Menu Item");
        System.out.println("2. View Menu");
        System.out.println("3. Search Menu Item");
        System.out.println("4. Update Menu Item");
        System.out.println("5. Delete Menu Item");
        System.out.println("6. Evaluate Menu Item");
        System.out.println("7. Feedback");
        System.out.println("8. Reward/Penalty");
        System.out.println("9. Save Data");
        System.out.println("10. Exit");
        System.out.println(
                "===================================="
        );
    }

    // =====================================================
    // ADD MENU ITEM
    // =====================================================

    private static void addMenuItem() {

        System.out.println(
                "\n========== ADD MENU ITEM =========="
        );

        int type;

        while (true) {

            System.out.println("1. Food");
            System.out.println("2. Drink");

            type = readInt("Choose item type: ");

            if (type == 1 || type == 2) {
                break;
            }

            System.out.println(
                    "Invalid item type. Choose 1 or 2."
            );
        }

        int id = readPositiveInt(
                "Enter ID: "
        );

        if (menuManager.searchItem(id) != null) {

            System.out.println(
                    "Error: ID already exists."
            );

            return;
        }

        String name = readNonEmptyString(
                "Enter name: "
        );

        double price = readNonNegativeDouble(
                "Enter price: "
        );

        String category = readNonEmptyString(
                "Enter category: "
        );

        boolean available =
                readBoolean(
                        "Is the item available? (yes/no): "
                );

        if (type == 1) {

            String dietaryType =
                    readNonEmptyString(
                            "Enter dietary type: "
                    );

            FoodItem food = new FoodItem(
                    id,
                    name,
                    price,
                    category,
                    available,
                    dietaryType
            );

            menuManager.addItem(food);

        } else {

            String drinkSize =
                    readNonEmptyString(
                            "Enter drink size: "
                    );

            DrinkItem drink = new DrinkItem(
                    id,
                    name,
                    price,
                    category,
                    available,
                    drinkSize
            );

            menuManager.addItem(drink);
        }
    }

    // =====================================================
    // SEARCH
    // =====================================================

    private static void searchMenuItem() {

        System.out.println(
                "\n========== SEARCH MENU ITEM =========="
        );

        int id = readPositiveInt(
                "Enter menu item ID: "
        );

        MenuItem item =
                menuManager.searchItem(id);

        if (item == null) {

            System.out.println(
                    "Menu item not found."
            );

        } else {

            System.out.println(
                    "Item found:"
            );

            System.out.println(
                    item.getDetails()
            );
        }
    }

    // =====================================================
    // UPDATE
    // =====================================================

    private static void updateMenuItem() {

        System.out.println(
                "\n========== UPDATE MENU ITEM =========="
        );

        int id = readPositiveInt(
                "Enter menu item ID: "
        );

        MenuItem item =
                menuManager.searchItem(id);

        if (item == null) {

            System.out.println(
                    "Menu item not found."
            );

            return;
        }

        System.out.println(
                "Current item:"
        );

        System.out.println(
                item.getDetails()
        );

        String name =
                readNonEmptyString(
                        "Enter new name: "
                );

        double price =
                readNonNegativeDouble(
                        "Enter new price: "
                );

        String category =
                readNonEmptyString(
                        "Enter new category: "
                );

        boolean available =
                readBoolean(
                        "Is the item available? (yes/no): "
                );

        menuManager.updateItem(
                id,
                name,
                price,
                category,
                available
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    private static void deleteMenuItem() {

        System.out.println(
                "\n========== DELETE MENU ITEM =========="
        );

        int id =
                readPositiveInt(
                        "Enter menu item ID: "
                );

        MenuItem item =
                menuManager.searchItem(id);

        if (item == null) {

            System.out.println(
                    "Menu item not found."
            );

            return;
        }

        System.out.println(
                "Deleting: "
                        + item.getName()
        );

        boolean confirmed =
                readBoolean(
                        "Are you sure? (yes/no): "
                );

        if (confirmed) {

            menuManager.deleteItem(id);

        } else {

            System.out.println(
                    "Delete cancelled."
            );
        }
    }

    // =====================================================
    // EVALUATE MENU ITEM
    // =====================================================

    private static void evaluateMenuItem() {

        System.out.println(
                "\n========== EVALUATE MENU ITEM =========="
        );

        int id =
                readPositiveInt(
                        "Enter menu item ID: "
                );

        MenuItem item =
                menuManager.searchItem(id);

        if (item == null) {

            System.out.println(
                    "Menu item not found."
            );

            return;
        }

        System.out.println(
                "Evaluating: "
                        + item.getName()
        );

        int rating;

        while (true) {

            rating =
                    readInt(
                            "Enter rating (1-5): "
                    );

            if (rating >= 1 && rating <= 5) {
                break;
            }

            System.out.println(
                    "Invalid rating. "
                            + "Rating must be between 1 and 5."
            );
        }

        String period =
                readNonEmptyString(
                        "Enter evaluation period "
                                + "(e.g. Week 1): "
                );

        evaluationManager.addEvaluation(
                item,
                rating,
                period
        );
    }

    // =====================================================
    // FEEDBACK
    // =====================================================

    private static void showFeedback() {

        System.out.println(
                "\n========== FEEDBACK =========="
        );

        int id =
                readPositiveInt(
                        "Enter menu item ID: "
                );

        evaluationManager.showFeedback(id);
    }

    // =====================================================
    // REWARD / PENALTY
    // =====================================================

    private static void showRewardPenalty() {

        System.out.println(
                "\n========== REWARD / PENALTY =========="
        );

        int id =
                readPositiveInt(
                        "Enter menu item ID: "
                );

        evaluationManager.showRewardPenalty(id);

        // Also show full performance status
        evaluationManager.checkPerformance(id);
    }

    // =====================================================
    // SAVE DATA
    // =====================================================

    private static void saveData() {

        fileManager.saveMenu(
                menuManager.getMenuItems()
        );

        fileManager.saveEvaluations(
                evaluationManager.getEvaluations()
        );
    }

    // =====================================================
    // LOAD DATA
    // =====================================================

    private static void loadData() {

        ArrayList<MenuItem> savedItems =
                fileManager.loadMenu();

        for (MenuItem item : savedItems) {
            menuManager.addItem(item);
        }

        fileManager.loadEvaluations(
                evaluationManager,
                menuManager
        );
    }

    // =====================================================
    // INPUT - INTEGER
    // =====================================================

    private static int readInt(String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                return Integer.parseInt(input);

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. "
                                + "Please enter a whole number."
                );
            }
        }
    }

    // =====================================================
    // INPUT - POSITIVE INTEGER
    // =====================================================

    private static int readPositiveInt(
            String message) {

        while (true) {

            int value =
                    readInt(message);

            if (value > 0) {
                return value;
            }

            System.out.println(
                    "Value must be greater than zero."
            );
        }
    }

    // =====================================================
    // INPUT - DOUBLE
    // =====================================================

    private static double readNonNegativeDouble(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                double value =
                        Double.parseDouble(input);

                if (value >= 0) {
                    return value;
                }

                System.out.println(
                        "Price cannot be negative."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid price. "
                                + "Please enter a valid number."
                );
            }
        }
    }

    // =====================================================
    // INPUT - NON-EMPTY STRING
    // =====================================================

    private static String readNonEmptyString(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty."
            );
        }
    }

    // =====================================================
    // INPUT - BOOLEAN
    // =====================================================

    private static boolean readBoolean(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine()
                            .trim()
                            .toLowerCase();

            if (input.equals("yes")
                    || input.equals("y")) {

                return true;
            }

            if (input.equals("no")
                    || input.equals("n")) {

                return false;
            }

            System.out.println(
                    "Invalid input. "
                            + "Please enter yes or no."
            );
        }
    }
}
