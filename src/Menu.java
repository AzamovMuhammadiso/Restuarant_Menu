import java.util.HashMap;
import java.util.Map;

public class Menu {
    private Map<String, MenuItem> items;

    public Menu() {
        this.items = new HashMap<>();
        initializeMenu();
    }

    private void initializeMenu() {
        items.put("Burger", new MenuItem("Burger", 8.99, "Tasty"));
        items.put("Pizza", new MenuItem("Pizza", 15.99, "Cheesy"));
        items.put("Salad", new MenuItem("Salad", 7.99, "Fresh and Healthy"));
        items.put("Pasta", new MenuItem("Pasta", 12.99, "Delicious"));
        items.put("Steak", new MenuItem("Steak", 19.99, "Juicy and Flavorful"));
        items.put("Sushi", new MenuItem("Sushi", 22.99, "Authentic Japanese Cuisine"));
        items.put("Chicken Curry", new MenuItem("Chicken Curry", 14.99, "Spicy and Aromatic"));
        items.put("Fish and Chips", new MenuItem("Fish and Chips", 11.99, "Classic British Dish"));
        items.put("Vegetarian Stir Fry", new MenuItem("Vegetarian Stir Fry", 10.99, "Vibrant and Flavorful"));
    }

    public void displayMenu() {
        System.out.println("Menu:");
        for (MenuItem item : items.values()) {
            System.out.println(item.name + ", Price: " + item.price + ", Description: " + item.description);
        }
    }

    public MenuItem getItem(String itemName) {
        return items.get(itemName);
    }

    public void addItem(String name, double price, String description) {
        if (items.containsKey(name)) {
            throw new IllegalArgumentException("Item with the same name already exists.");
        }

        if (items.size() >= 10) {
            throw new RuntimeException("Menu is full");
        }

        items.put(name, new MenuItem(name, price, description));
    }

    public void updateItem(String name, double price, String description) {
        if (!items.containsKey(name)) {
            throw new IllegalArgumentException("Item not found.");
        }

        MenuItem item = items.get(name);
        item.price = price;
        item.description = description;
    }

    public void deleteItem(String name) {
        if (!items.containsKey(name)) {
            throw new IllegalArgumentException("Item not found.");
        }

        items.remove(name);
    }
}
