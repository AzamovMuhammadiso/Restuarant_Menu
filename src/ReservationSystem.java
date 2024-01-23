import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationSystem {
    private Menu menu;
    private List<Map<String, Object>> reservations;
    private Map<Integer, Map<String, Map<String, Boolean>>> tableReservations;
    private User currentUser;

    public ReservationSystem() {
        this.menu = new Menu();
        this.reservations = new ArrayList<>();
        this.tableReservations = new HashMap<>();
    }

    public Menu getMenu() {
        return menu;
    }

    public List<Map<String, Object>> getReservations() {
        return reservations;
    }

    public Map<Integer, Map<String, Map<String, Boolean>>> getTableReservations() {
        return tableReservations;
    }

    public void makeReservation(int tableNumber, List<String> selectedMeals, User user) {
        if (selectedMeals.size() < 1 || selectedMeals.size() > 10) {
            throw new IllegalArgumentException("Invalid number of meals selected. Minimum 1, Maximum 10 allowed.");
        }

        double totalPrice = 0;
        Map<String, Double> prices = new HashMap<>();

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
        reservation.put("prices", prices);
        reservation.put("totalPrice", totalPrice);
        reservation.put("user", user);

        reservations.add(reservation);

        System.out.println("Reservation successfully made!");
        System.out.println("Details:");
        System.out.println("Table Number: " + reservation.get("tableNumber"));
        System.out.println("Selected Meals: " + formatMealsWithPrices(prices));
        System.out.println("Total Price: $" + reservation.get("totalPrice"));
        System.out.println("Reserved for User: " + user.getUsername());
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

    public void reserveTable(int tableNumber, String date, String time, User user) {
        if (tableNumber < 1 || tableNumber > 40) {
            throw new IllegalArgumentException("Invalid table number. Table number must be between 1 and 40.");
        }
        Map<String, Map<String, Boolean>> dateReservations = tableReservations.getOrDefault(tableNumber, new HashMap<>());
        Map<String, Boolean> timeReservations = dateReservations.getOrDefault(date, new HashMap<>());

        if (timeReservations.containsKey(time) && timeReservations.get(time)) {
            System.out.println("Sorry, the table is already reserved at the specified date and time.");
        } else {
            timeReservations.put(time, true);
            dateReservations.put(date, timeReservations);
            tableReservations.put(tableNumber, dateReservations);
            System.out.println("Table reservation successful for user: " + user.getUsername());
        }
    }

    private String formatMealsWithPrices(Map<String, Double> prices) {
        StringBuilder result = new StringBuilder("[");
        for (Map.Entry<String, Double> entry : prices.entrySet()) {
            result.append(entry.getKey()).append("=$").append(entry.getValue()).append(", ");
        }
        result.setLength(result.length() - 2);
        result.append("]");
        return result.toString();
    }
}
