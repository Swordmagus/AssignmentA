import java.util.Scanner;

public class OnlineShoppingSystem {
    private static User currentUser;
    public static boolean usercheck;

    public static void main(String[] args) {
        Product.initializeProducts(); // Initialize products
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        while (true) {
            System.out.print("\nLogin in to Account:\nPlease Enter 'yes' or 'no': ");
            String Discition = scanner.nextLine();
            loginMenu(Discition, scanner, name);

        }
    }

    private static void loginMenu(String Discition, Scanner scanner, String name) {
        if (Discition.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.print("\nEnter a 4-digit code to become a Customer or a 5-digit code to become a Member:");
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
        } else if (Discition.equalsIgnoreCase("no")) {
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
            System.out.print("1. View Products | 2. View Cart | 3. View User | 4. Exit\nEnter Button Number: ");
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
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Product.viewAllProducts();
                    break;
                case "2":
                    Product.searchProducts(scanner);
                    break;
                case "3":
                    Product.viewCategoryMenu(scanner);
                    break;
                case "4":
                    Product.appendWishCartlist(scanner, currentUser, choice, usercheck);
                    // Add product to cart if user is a customer or a member
                    break;
                case "5":
                    Product.appendWishCartlist(scanner, currentUser, choice, usercheck);
                    // Add product to wishlist if the user is a customer or a member
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
                "2. Add wishlist Product to Cart - If you want to add all wishlist products to Cart Enter 'ALL'\n3. Remove Wish | 4. Exit\nEnter: ");

        String choice1 = scanner.nextLine();

        switch (choice1) {
            case "1":
                if (!usercheck) {
                    loginMenu("yes", scanner, currentUser.getName());
                } else {
                    System.out.println("Log Out.");
                    usercheck = false;
                    loginMenu("no", scanner, currentUser.getName());
                }
            case "2":
                Product.appendWishCartlist(scanner, currentUser, "4", usercheck);// append selected wishlist product to
                                                                                 // cart
                break;
            case "ALL":
                if (!usercheck) {
                    Wishlist.addAllWishlistToCart(currentUser);
                } // Add all wishlist products to cart
                else {
                    System.out.println("You are not a Customer");
                }
                break;
            case "3":
                System.out.print("Enter the name of the product to remove from the wishlist: ");
                String productName = scanner.nextLine(); // Get the product name
                Product product = Cart.findProductInCart(currentUser.getWishlist(), productName); // Find the product
                // in the wishlist
                if (product != null) {
                    currentUser.getWishlist().removeProduct(product); // Remove the product from the wishlist
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
        System.out.print("1. Checkout | 2. Remove Product | 3. Clear Cart | 4. Back\nEnter: ");
        String choice2 = scanner.nextLine();

        switch (choice2) {

            case "1":
                if (currentUser.canCheckout()) {
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
                }
                System.out.print("Enter the product name: ");
                String productName = scanner.nextLine(); // Get the product name
                Product productToRemove = Cart.findProductInCart(currentUser.getCart(), productName);
                if (productToRemove != null) {
                    currentUser.getCart().removeProduct(productToRemove);
                    System.out.println("Product removed.");
                } else {
                    System.out.println("Product not found.");
                }
                break;
            case "3":
                if (usercheck) {
                    Cart.removeAllProductsFromCart(currentUser);
                    break;
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
            return;
        } else {
            System.out.println("Order placement failed. Please try again.");
        }
    }
}
