import java.util.List;
import java.util.Scanner;
import java.util.Map;  // Import Map class

public class Main {
    private static User loggedInUser;
    private static ReservationSystem reservationSystem = new ReservationSystem();

    public static void main(String[] args) {
        UserManagement userManagement = new UserManagement();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (loggedInUser == null) {
                // Display login/registration options
                displayLoginOptions(scanner, userManagement);
            } else {
                // Display main menu based on user type
                if (loggedInUser.getUserType() == UserType.STAFF) {
                    displayStaffMenu(scanner);
                } else {
                    displayUserMenu(scanner);
                }
            }
        }
    }

    private static void displayLoginOptions(Scanner scanner, UserManagement userManagement) {
        System.out.println("\nLogin / Registration Options:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice (1-3): ");

        String loginChoice = scanner.nextLine();

        switch (loginChoice) {
            case "1":
                // Register User
                registerUser(scanner, userManagement);
                break;
            case "2":
                // Login
                login(scanner, userManagement);
                break;
            case "3":
                // Exit
                System.out.println("Exiting the reservation system. Goodbye!");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    private static void registerUser(Scanner scanner, UserManagement userManagement) {
        System.out.print("Enter username for registration: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for registration: ");
        String password = scanner.nextLine();

        UserType userType = UserType.USER; // By default, registered users are not staff

        if (username.equals("staff") && password.equals("staff123")) {
            userType = UserType.STAFF;
        }

        userManagement.registerUser(username, password, userType);

        System.out.println("Registration successful. Please log in.");
    }

    private static void login(Scanner scanner, UserManagement userManagement) {
        System.out.print("Enter username for login: ");
        String username = scanner.nextLine();
        System.out.print("Enter password for login: ");
        String password = scanner.nextLine();

        loggedInUser = userManagement.loginUser(username, password);

        if (loggedInUser != null) {
            System.out.println("Logged in as: " + loggedInUser.getUsername());
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void displayUserMenu(Scanner scanner) {
        System.out.println("\nOptions:");
        System.out.println("1. Display Menu");
        System.out.println("2. Make Reservation");
        System.out.println("3. Checkout");
        System.out.println("4. Exit");
        System.out.print("Enter your choice (1-4): ");

        String choice = scanner.nextLine();

        handleUserChoice(choice, scanner);
    }

    private static void displayStaffMenu(Scanner scanner) {
        System.out.println("Staff Menu:");
        System.out.println("1. View Reservations");
        System.out.println("2. Manage Menu");
        System.out.println("3. Exit");
        System.out.print("Enter your choice (1-3): ");

        String staffChoice = scanner.nextLine();

        switch (staffChoice) {
            case "1":
                // View Reservations
                viewReservations();
                break;
            case "2":
                // Manage Menu
                manageMenu(scanner);
                break;
            case "3":
                exit();
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 3.");
        }
    }

    private static void exit() {
        System.out.println("Exiting staff menu. Returning to login screen.");
        loggedInUser = null;  // Reset logged-in user
    }


    private static void handleUserChoice(String choice, Scanner scanner) {
        switch (choice) {
            case "1":
                reservationSystem.getMenu().displayMenu();
                break;
            case "2":
                // Make Reservation
                if (loggedInUser != null) {
                    try {
                        System.out.print("Enter table number: ");
                        int tableNumber = Integer.parseInt(scanner.nextLine());
                        System.out.print("Enter selected meals (comma-separated): ");
                        String[] selectedMeals = scanner.nextLine().split(",");
                        List<String> selectedMealsList = List.of(selectedMeals);
                        reservationSystem.makeReservation(tableNumber, selectedMealsList);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Please log in or register to make a reservation.");
                }
                break;
            case "3":
                // Checkout
                if (loggedInUser != null) {
                    try {
                        System.out.print("Enter table number for checkout: ");
                        int tableNumber = Integer.parseInt(scanner.nextLine());
                        reservationSystem.checkout(tableNumber);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Please log in to perform checkout.");
                }
                break;
            case "4":
                // Exit
                System.out.println("Exiting the reservation system. Goodbye!");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        }
    }

    private static void manageMenu(Scanner scanner) {
        System.out.println("\nMenu Management:");
        System.out.println("1. Display Menu");
        System.out.println("2. Add Item to Menu");
        System.out.println("3. Update Item in Menu");
        System.out.println("4. Delete Item from Menu");
        System.out.print("Enter your choice (1-4): ");

        String menuChoice = scanner.nextLine();

        switch (menuChoice) {
            case "1":
                // Display Menu
                reservationSystem.getMenu().displayMenu();
                break;
            case "2":
                // Add Item to Menu
                addItemToMenu(scanner);
                break;
            case "3":
                // Update Item in Menu
                updateItemInMenu(scanner);
                break;
            case "4":
                // Delete Item from Menu
                deleteItemFromMenu(scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        }
    }

    private static void addItemToMenu(Scanner scanner) {
        System.out.print("Enter name of the new item: ");
        String name = scanner.nextLine();
        System.out.print("Enter price of the new item: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter description of the new item: ");
        String description = scanner.nextLine();

        try {
            reservationSystem.getMenu().addItem(name, price, description);
            System.out.println("Item added to the menu successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateItemInMenu(Scanner scanner) {
        reservationSystem.getMenu().displayMenu();
        System.out.print("Enter name of the item to update: ");
        String nameToUpdate = scanner.nextLine();

        System.out.print("Enter new price for the item: ");
        double newPrice = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter new description for the item: ");
        String newDescription = scanner.nextLine();

        try {
            reservationSystem.getMenu().updateItem(nameToUpdate, newPrice, newDescription);
            System.out.println("Item updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void deleteItemFromMenu(Scanner scanner) {
        reservationSystem.getMenu().displayMenu();
        System.out.print("Enter name of the item to delete: ");
        String nameToDelete = scanner.nextLine();

        try {
            reservationSystem.getMenu().deleteItem(nameToDelete);
            System.out.println("Item deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewReservations() {
        if (loggedInUser != null && loggedInUser.getUserType() == UserType.STAFF) {
            System.out.println("\nReservations:");
            if (reservationSystem.getReservations().isEmpty()) {
                System.out.println("No reservations available.");
            } else {
                for (Map<String, Object> reservation : reservationSystem.getReservations()) {
                    System.out.println("\nTable Number: " + reservation.get("tableNumber"));
                    System.out.println("Selected Meals: " + reservation.get("selectedMeals"));
                    System.out.println("Total Price: $" + reservation.get("totalPrice"));
                }
            }
        } else {
            System.out.println("You do not have permission to view reservations.");
        }
    }

}
