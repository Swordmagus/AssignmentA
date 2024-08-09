import java.util.Scanner;

public class OnlineShoppingSystem {
    private static User currentUser;
    public static boolean usercheck;

    public static void main(String[] args) {
        Product.initializeProducts(); // Initialize products
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            while (true) {
                System.out.print("\nLogin to Account:\nPlease Enter 'yes' or 'no': ");
                String decision = scanner.nextLine();
                loginMenu(decision, scanner, name);

            }
        } finally {
            scanner.close(); // Ensure the Scanner is closed to prevent resource leaks
        }
    }

    private static void loginMenu(String decision, Scanner scanner, String name) {
        if (decision.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.print("\nEnter a 4-digit code to become a Customer or a 5-digit code to become a Member: ");
                String code = scanner.nextLine();

                if (code.matches("\\d{4}")) {
                    currentUser = new Customer(name);
                    System.out.println("You are now a Customer.");
                    usercheck = true;
                    break;
                } else if (code.matches("\\d{5}")) {
                    currentUser = new Member(name);
                    System.out.println("You are now a Member.");
                    usercheck = true;
                    break;
                } else {
                    System.out.println("Invalid code. Please enter a 4-digit or 5-digit code.");
                }
            }
        } else if (decision.equalsIgnoreCase("no")) {
            currentUser = new Guest(name);
            usercheck = false;
            System.out.println("\nWelcome to the Online Shopping System!");
        } else {
            System.out.println("\nInvalid input. Please enter 'yes' or 'no'.");
            return;
        }
        mainMenu(scanner);
    }

    private static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.print("1. View Products | 2. View Cart | 3. View User | 4. Exit Website\nEnter Button Number: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewProductsMenu(scanner);
                    break;
                case "2":
                    ViewCart(scanner);
                    break;
                case "3":
                    viewUser(scanner);
                    break;
                case "4":
                    System.out.println("Exiting...");
                    System.exit(0);
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
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Product.viewAllProducts(scanner);
                    break;
                case "2":
                    Product.searchProducts(scanner);
                    break;
                case "3":
                    Product.viewCategoryMenu(scanner);
                    break;
                case "4":
                    Product.appendWishCartList(scanner, currentUser, choice, usercheck);
                    break;
                case "5":
                    Product.appendWishCartList(scanner, currentUser, choice, usercheck);
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void viewUser(Scanner scanner) {
        System.out.println("Name: " + currentUser.getName() + " | Account Level: "
                + currentUser.getClass().getSimpleName());
        currentUser.viewWishlist();
        if (!usercheck) {
            System.out.println("1. Log in");
        } else {
            System.out.println("1. Log Out");
        }
        System.out.print(
                "2. Add wishlist Product to Cart | If you want to add all wishlist products to Cart Enter 'ALL'\n3. Remove Wish | 4. Exit\nEnter: ");

        String choice1 = scanner.nextLine().toLowerCase();

        switch (choice1) {
            case "1":
                if (!usercheck) {
                    loginMenu("yes", scanner, currentUser.getName());
                } else {
                    System.out.println("Log Out.");
                    usercheck = false;
                    loginMenu("no", scanner, currentUser.getName());
                }
                break; // Added missing break
            case "2":
                Product.addProductWishToCart(scanner, currentUser, usercheck);
                break;
            case "all":
                if (usercheck) {
                    Wishlist.addAllWishlistToCart(currentUser);
                } else {
                    System.out.println("You are not a Customer");
                }
                break;
            case "3":
                System.out.print("Enter the name of the product to remove from the wishlist: ");
                String productName = scanner.nextLine();
                Product product = Cart.findProductInCart(currentUser.getWishlist(), productName);
                if (product != null) {
                    currentUser.getWishlist().removeProduct(product);
                    System.out.println("Product removed from wishlist.");
                } else {
                    System.out.println("Product not found in the wishlist.");
                }
                break;
            case "4":
                return; // Exit to the main menu
            default:
                System.out.println("Invalid choice.");
                break;
        }
    }

    private static void ViewCart(Scanner scanner) {
        currentUser.viewCart();
        System.out.print("1. Checkout | 2. Remove Product | 3. Clear Cart | 4. Back\nEnter Button No: ");
        String choice2 = scanner.nextLine();

        switch (choice2) {
            case "1":
                if (currentUser.canCheckout()) {
                    if (currentUser.getCart().getProducts().isEmpty()) {
                        System.out.println("No products in cart to checkout.");
                        break;
                    }
                    System.out.println("Proceeding to checkout...");
                    checkoutMenu(currentUser, scanner);
                } else {
                    System.out.println("Guests cannot checkout.");
                }
                break;
            case "2":
                if (!usercheck) {
                    System.out.println("You are not a Customer");
                    break;
                } else {
                    System.out.print("Enter the product name: ");
                    String productName = scanner.nextLine();
                    Product productToRemove = Cart.findProductInCart(currentUser.getCart(), productName);
                    if (productToRemove != null && currentUser.getCart().getProducts().contains(productToRemove)) {
                        currentUser.getCart().removeProduct(productToRemove);
                        System.out.println("Product removed.");
                    } else {
                        System.out.println("Product not found.");
                    }
                }
                break;
            case "3":
                if (usercheck) {
                    Cart.removeAllProductsFromCart(currentUser);
                } else {
                    System.out.println("Guests cannot clear cart.");
                }
                break;
            case "4":
                return; // Go back to the main menu
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void checkoutMenu(User user, Scanner scanner) {
        System.out.println("Select Payment Method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Online Payment");

        System.out.print("Enter your choice: ");
        String paymentOption = scanner.nextLine();

        Payment payment;

        switch (paymentOption) {
            case "1":
                payment = new CashOnDelivery();
                break;
            case "2":
                payment = new OnlinePayment();
                break;
            default:
                System.out.println("Invalid payment method. Please try again.");
                return;
        }

        boolean success = payment.checkout(user);
        if (success) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Order placement failed. Please try again.");
        }
    }
}
