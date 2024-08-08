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
            break;
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
            scanner.nextLine(); // Consume the newline character left by nextInt()
    
        switch (choice) {
            case 1:
                viewProductsMenu(scanner);
                break;
            case 2:
                currentUser.viewCart();
                System.out.print("1. Checkout | 2. Remove Product | 3. Clear Cart| 4. Back\nEnter: ");
                int choice2 = scanner.nextInt();
                if (choice == 2) {
                    System.out.println("Enter the product name to remove:");
                    String productName = scanner.nextLine(); // Get the product name
                
                    // Try to remove the product directly
                    boolean removed = currentUser.getCart().removeProductByName(productName);
                
                    // Check if the product was removed
                    if (removed) {
                        System.out.println(productName + " has been removed from your cart.");
                    } else {
                        System.out.println("Unable to find the product in your cart.");
                    }
                }
                if (choice2 == 3){
                    Cart.removeAllProductsFromCart(currentUser);}
                break;
            case 3:
                System.out.println("Name: " + currentUser.getName() + " | Account Level: " + currentUser.getClass().getSimpleName());
                currentUser.viewWishlist();
                System.out.print("1. Add wishlist Product to Cart - If you want to add all wishlist products to Cart Enter 'ALL'\n2. Exit\nEnter: ");
                
                String choice1 = scanner.nextLine();
                if (choice1.equals("1")) {
                    Product.appendWishCartlist(scanner, currentUser, 4); //appendWishCartlist
                } else if (choice1.equalsIgnoreCase("ALL")) {
                    Wishlist.addAllWishlistToCart(currentUser);
                }
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
                    Product.viewCategoryMenu(scanner);
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




    private static Product findProductInCart(Cart cart, String productName) {
        for (Product product : cart.getProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) { // Assuming the Product class has a getName method
                return product;
            }
        }
        return null; // Return null if the product is not found
    }
}
