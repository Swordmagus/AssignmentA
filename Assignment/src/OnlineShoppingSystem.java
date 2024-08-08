import java.util.Scanner;

public class OnlineShoppingSystem {

    private static User currentUser;
    public static void main(String[] args) {
        Product.initializeProducts(); // Initialize products
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        while (true) {
            System.out.print("\nLogin in to Account:\nPlease Enter 'yes' or 'no': ");
            String Customer = scanner.nextLine();
            loginMenu(Customer, scanner, name);
            mainMenu(scanner);
            return;
        }
    }

    private static void loginMenu(String Customer, Scanner scanner, String name) {
        if (Customer.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.print("\nEnter a 4-digit code to become a Customer or a 5-digit code to become a Member:");
                String code = scanner.nextLine();

                if (code.matches("\\d{4}")) {
                    currentUser = new Customer(name);
                    System.out.println("You are now a Customer.");
                    break;
                } else if (code.matches("\\d{5}")) {
                    currentUser = new Member(name);
                    System.out.println("You are now a Member.");
                    break;
                } else {
                    System.out.println("Invalid code. Please enter a 4-digit or 5-digit code.");
                }
                break;
            }
        } else if (Customer.equalsIgnoreCase("no")) {
            currentUser = new Guest(name);
            System.out.println("\nWelcome to the Online Shopping System!");
        } else {
            System.out.println("\nInvalid input. Please enter 'yes' or 'no'.");
        }
    }

    private static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.print("1. View Products | 2. View Cart | 3. View User | 4. Exit\nEnter Button Number: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewProductsMenu(scanner);
                    break;
                case 2:
                    currentUser.viewCart();
                    break;
                case 3:
                    System.out.println("Name: " + currentUser.getName() + " | Account LVL: "
                            + currentUser.getClass().getSimpleName());
                    currentUser.viewWishlist();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewProductsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nProducts Menu: ");
            System.out.print(
                    "1. View All Products | 2. Search Products | 3. View Category | 4. Add product to Cart | 5. Add product to Wishlist | 6. Back to Main Menu\nEnter Button Number: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    Product.viewAllProducts();
                    break;
                case 2:
                    Product.searchProducts(scanner);
                    break;
                case 3:
                    viewCategoryMenu(scanner);
                    break;
                case 4:
                    Product.appendWishCartlist(scanner, currentUser, choice);
                    // Add product to cart if user is a customer or a member
                    break;
                case 5:
                    Product.appendWishCartlist(scanner, currentUser, choice);
                    // Add product to wishlist if the user is a customer or a member
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewCategoryMenu(Scanner scanner) {
        System.out.println("\nCategories:");
        int i = 0;
        for (ProductCategory category : ProductCategory.values()) {
            i += 1;
            System.out.println(i + ". " + category);
        }
        System.out.print("Enter category Name: ");
        String categoryInput = scanner.nextLine().toUpperCase();

        ProductCategory selectedCategory;
        try {
            selectedCategory = ProductCategory.valueOf(categoryInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid category.");
            return;
        }

        Product.viewProductsByCategory(selectedCategory);

        // Sorting products in the selected category
        Product.sortProducts(selectedCategory, scanner);
    }
}
