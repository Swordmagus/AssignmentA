import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

enum ProductCategory {
    ELECTRONICS, CLOTHING, BOOKS, HOME, BEAUTY, TOYS, SPORTS;
}

public class Product {
    private String name;
    private double price;
    private ProductCategory category;
    private int inventory;
    private static List<Product> products = new ArrayList<>();

    public Product(String name, double price, ProductCategory category, int inventory) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public int getinventory() {
        return inventory;
    }

    public void setInventory(int newInventory) {
        this.inventory = newInventory;
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price;
    }

    protected static void initializeProducts() {
        // Electronics
        products.add(new Product("Laptop", 800.0, ProductCategory.ELECTRONICS, 10));
        products.add(new Product("Smartphone", 600.0, ProductCategory.ELECTRONICS, 15));
        products.add(new Product("Headphones", 100.0, ProductCategory.ELECTRONICS, 25));
        products.add(new Product("Smartwatch", 200.0, ProductCategory.ELECTRONICS, 20));
        products.add(new Product("Bluetooth Speaker", 50.0, ProductCategory.ELECTRONICS, 30));

        // Clothing
        products.add(new Product("T-Shirt", 20.0, ProductCategory.CLOTHING, 50));
        products.add(new Product("Jeans", 40.0, ProductCategory.CLOTHING, 40));
        products.add(new Product("Jacket", 100.0, ProductCategory.CLOTHING, 25));
        products.add(new Product("Sneakers", 80.0, ProductCategory.CLOTHING, 10));
        products.add(new Product("Socks", 10.0, ProductCategory.CLOTHING, 100));

        // Home
        products.add(new Product("Coffee Maker", 50.0, ProductCategory.HOME, 12));
        products.add(new Product("Vacuum Cleaner", 150.0, ProductCategory.HOME, 8));
        products.add(new Product("Microwave Oven", 120.0, ProductCategory.HOME, 15));
        products.add(new Product("Air Purifier", 180.0, ProductCategory.HOME, 7));
        products.add(new Product("Blender", 30.0, ProductCategory.HOME, 20));

        // Books
        products.add(new Product("Book", 10.0, ProductCategory.BOOKS, 50));
        products.add(new Product("Science Fiction Novel", 15.0, ProductCategory.BOOKS, 30));
        products.add(new Product("Cooking Book", 25.0, ProductCategory.BOOKS, 20));
        products.add(new Product("History Book", 20.0, ProductCategory.BOOKS, 15));
        products.add(new Product("Children's Book", 8.0, ProductCategory.BOOKS, 40));

        // Beauty
        products.add(new Product("Lipstick", 15.0, ProductCategory.BEAUTY, 50));
        products.add(new Product("Perfume", 60.0, ProductCategory.BEAUTY, 20));
        products.add(new Product("Face Cream", 25.0, ProductCategory.BEAUTY, 30));
        products.add(new Product("Shampoo", 12.0, ProductCategory.BEAUTY, 45));
        products.add(new Product("Conditioner", 12.0, ProductCategory.BEAUTY, 40));

        // Sports
        products.add(new Product("Basketball", 25.0, ProductCategory.SPORTS, 15));
        products.add(new Product("Tennis Racket", 100.0, ProductCategory.SPORTS, 10));
        products.add(new Product("Football", 30.0, ProductCategory.SPORTS, 20));
        products.add(new Product("Yoga Mat", 20.0, ProductCategory.SPORTS, 25));
        products.add(new Product("Running Shoes", 120.0, ProductCategory.SPORTS, 8));

        // Toys
        products.add(new Product("Action Figure", 15.0, ProductCategory.TOYS, 30));
        products.add(new Product("Lego Set", 50.0, ProductCategory.TOYS, 20));
        products.add(new Product("Doll", 20.0, ProductCategory.TOYS, 25));
        products.add(new Product("Puzzle", 10.0, ProductCategory.TOYS, 40));
        products.add(new Product("Board Game", 30.0, ProductCategory.TOYS, 15));
    }

    protected static void viewAllProducts() {
        System.out.println("\nAll Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    protected static void searchProducts(Scanner scanner) {
        System.out.print("Enter product name to search: ");
        String searchName = scanner.nextLine().toUpperCase();

        System.out.println("\nSearch Results:");
        for (Product product : products) {
            if (product.getName().toUpperCase().contains(searchName)) {
                System.out.println(product);
            }
        }
    }

    public static void viewProductsByCategory(ProductCategory selectedCategory) {
        for (Product product : products) {
            if (product.getCategory() == selectedCategory) {
                System.out.println(product);
            }
        }
    }

    protected static void sortProducts(ProductCategory selectedCategory, Scanner scanner) {
        System.out.println(
                "Sort by: \n1. Price (Low to High)\n2. Price (High to Low)\n3. Name (A-Z)\n4. Name (Z-A)\n5. Exit");
        String choice = scanner.nextLine();

        List<Product> tempProductList = new ArrayList<>(); // Temporary Product list to sort
        for (Product product : products) {
            if (product.getCategory() == selectedCategory) {
                tempProductList.add(product);
            }
        }
        switch (choice) {
            case "1":
                tempProductList.sort(Comparator.comparingDouble(Product::getPrice).thenComparing(Product::getName));
                break;
            case "2":
                tempProductList.sort(
                        (Comparator.comparingDouble(Product::getPrice).thenComparing(Product::getName)).reversed());
                break;
            case "3":
                tempProductList.sort(Comparator.comparing(Product::getName).thenComparingDouble(Product::getPrice));
                break;
            case "4":
                tempProductList.sort(
                        (Comparator.comparing(Product::getName).thenComparingDouble(Product::getPrice)).reversed());
                break;
            case "5":
                return;
            default:
                System.out.println("Invalid choice.");
                break;
        }

        // Display the sorted products
        System.out.println("Sorted Products:");
        for (Product product : tempProductList) {
            System.out.println(product);
        }
    }

    protected static Product findProductByName(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

    public static List<Product> getProducts() {
        return products;
    }

    // Adds Items to either the wishlist or the cart depending on "choice"
    protected static void appendWishCartlist(Scanner scanner, User currentUser, String choice, boolean usercheck) {
        if (usercheck) {
            System.out.print("Enter the product name to add: ");
            String productName = scanner.nextLine();
            Product productToAdd = findProductByName(productName);
            if (productToAdd != null) {
                if (choice.equals("4") && productToAdd.getinventory() > 0) {
                    currentUser.addToCart(productToAdd);
                    System.out.println(productToAdd.getName() + " added to Cart.");

                    if (currentUser.getWishlist().getProducts().contains(productToAdd)) {
                        // Attempt to remove the product from the wishlist
                        currentUser.getWishlist().removeProduct(productToAdd);
                    }
                    return;
                } else if (choice.equals("4") && productToAdd.getinventory() == 0) {
                    System.out.println("Product is out of stock.");
                } else {
                    currentUser.addToWishlist(productToAdd);
                    System.out.println(productToAdd.getName() + " added to Wishlist.");
                    return;
                }
            } else {
                System.out.println("Product not found.");
            }
        } else {
            System.out.println("Only customers and members can add products.");
        }
    }

    protected static void viewCategoryMenu(Scanner scanner) {
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

        viewProductsByCategory(selectedCategory);

        // Sorting products in the selected category
        Product.sortProducts(selectedCategory, scanner);
    }
}
