import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationSystem {
    private Menu menu;
    private List<Map<String, Object>> reservations;

    public ReservationSystem() {
        this.menu = new Menu();
        this.reservations = new ArrayList<>();
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Map<String, Object>> getReservations() {
        return reservations;
    }

    public void makeReservation(int tableNumber, List<String> selectedMeals) {
        if (selectedMeals.size() < 1 || selectedMeals.size() > 10) {
            throw new IllegalArgumentException("Invalid number of meals selected. Minimum 1, Maximum 10 allowed.");
        }

        double totalPrice = 0;
        Map<String, Double> prices = new HashMap<>(); // Map to store prices of selected meals

        for (String meal : selectedMeals) {
            MenuItem menuItem = menu.getItem(meal);
            if (menuItem != null) {
                double mealPrice = menuItem.getPrice();
                prices.put(meal, mealPrice);
                totalPrice += mealPrice;
            } else {
                throw new IllegalArgumentException("Menu item not found: " + meal);
            }
        }

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("tableNumber", tableNumber);
        reservation.put("selectedMeals", selectedMeals);
        reservation.put("prices", prices);  // Add prices to the reservation details
        reservation.put("totalPrice", totalPrice);

        reservations.add(reservation);

        System.out.println("Reservation successfully made!");
        System.out.println("Details:");
        System.out.println("Table Number: " + reservation.get("tableNumber"));
        System.out.println("Selected Meals: " + formatMealsWithPrices(prices)); // Display meals with prices
        System.out.println("Total Price: $" + reservation.get("totalPrice"));
    }

    // Helper method to format meals with prices
    private String formatMealsWithPrices(Map<String, Double> prices) {
        StringBuilder result = new StringBuilder("[");
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            result.append(entry.getKey()).append("=$").append(entry.getValue()).append(", ");
        }
        result.setLength(result.length() - 2); // Remove the trailing comma and space
        result.append("]");
        return result.toString();
    }


    public void checkout(int tableNumber) {
        Map<String, Object> reservationToRemove = null;
        for (Map<String, Object> reservation : reservations) {
            if ((int) reservation.get("tableNumber") == tableNumber) {
                System.out.println("\nCheckout:");
                System.out.println("Table Number: " + reservation.get("tableNumber"));
                System.out.println("Selected Meals: " + reservation.get("selectedMeals"));
                System.out.println("Total Price: $" + reservation.get("totalPrice"));
                System.out.println("Thank you for dining at Delicious Bites!");
                reservationToRemove = reservation;
                break;
            }
        }

        if (reservationToRemove != null) {
            reservations.remove(reservationToRemove);
        } else {
            System.out.println("Reservation for Table Number " + tableNumber + " not found.");
        }
    }
}
