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
    private static List<Product> products = new ArrayList<>();

    public Product(String name, double price, ProductCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
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

    @Override
    public String toString() {
        return name + " (" + category + ") - $" + price;
    }

    public static void initializeProducts() {
        // Electronics
        products.add(new Product("Laptop", 800.0, ProductCategory.ELECTRONICS));
        products.add(new Product("Smartphone", 600.0, ProductCategory.ELECTRONICS));
        products.add(new Product("Headphones", 100.0, ProductCategory.ELECTRONICS));
        products.add(new Product("Smartwatch", 200.0, ProductCategory.ELECTRONICS));
        products.add(new Product("Bluetooth Speaker", 50.0, ProductCategory.ELECTRONICS));

        // Clothing
        products.add(new Product("T-Shirt", 20.0, ProductCategory.CLOTHING));
        products.add(new Product("Jeans", 40.0, ProductCategory.CLOTHING));
        products.add(new Product("Jacket", 100.0, ProductCategory.CLOTHING));
        products.add(new Product("Sneakers", 80.0, ProductCategory.CLOTHING));
        products.add(new Product("Socks", 10.0, ProductCategory.CLOTHING));

        // Home
        products.add(new Product("Coffee Maker", 50.0, ProductCategory.HOME));
        products.add(new Product("Vacuum Cleaner", 150.0, ProductCategory.HOME));
        products.add(new Product("Microwave Oven", 120.0, ProductCategory.HOME));
        products.add(new Product("Air Purifier", 180.0, ProductCategory.HOME));
        products.add(new Product("Blender", 30.0, ProductCategory.HOME));

        // Books
        products.add(new Product("Book", 10.0, ProductCategory.BOOKS));
        products.add(new Product("Science Fiction Novel", 15.0, ProductCategory.BOOKS));
        products.add(new Product("Cooking Book", 25.0, ProductCategory.BOOKS));
        products.add(new Product("History Book", 20.0, ProductCategory.BOOKS));
        products.add(new Product("Children's Book", 8.0, ProductCategory.BOOKS));

        // Beauty
        products.add(new Product("Lipstick", 15.0, ProductCategory.BEAUTY));
        products.add(new Product("Perfume", 60.0, ProductCategory.BEAUTY));
        products.add(new Product("Face Cream", 25.0, ProductCategory.BEAUTY));
        products.add(new Product("Shampoo", 12.0, ProductCategory.BEAUTY));
        products.add(new Product("Conditioner", 12.0, ProductCategory.BEAUTY));

        // Sports
        products.add(new Product("Basketball", 25.0, ProductCategory.SPORTS));
        products.add(new Product("Tennis Racket", 100.0, ProductCategory.SPORTS));
        products.add(new Product("Football", 30.0, ProductCategory.SPORTS));
        products.add(new Product("Yoga Mat", 20.0, ProductCategory.SPORTS));
        products.add(new Product("Running Shoes", 120.0, ProductCategory.SPORTS));

        // Toys
        products.add(new Product("Action Figure", 15.0, ProductCategory.TOYS));
        products.add(new Product("Lego Set", 50.0, ProductCategory.TOYS));
        products.add(new Product("Doll", 20.0, ProductCategory.TOYS));
        products.add(new Product("Puzzle", 10.0, ProductCategory.TOYS));
        products.add(new Product("Board Game", 30.0, ProductCategory.TOYS));
    }

    public static void viewAllProducts() {
        System.out.println("\nAll Products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public static void searchProducts(Scanner scanner) {
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

    public static void sortProducts(ProductCategory selectedCategory, Scanner scanner) {
        System.out.println(
                "Sort by: \n1. Price (Low to High)\n2. Price (High to Low)\n3. Name (A-Z)\n4. Name (Z-A)\n5. Exit");
        int choice = scanner.nextInt();

        List<Product> tempProductList = new ArrayList<>(); // Temporary Product list to sort
        for (Product product : products) {
            if (product.getCategory() == selectedCategory) {
                tempProductList.add(product);
            }
        }
        switch (choice) {
            case 1:
                tempProductList.sort(Comparator.comparingDouble(Product::getPrice));
                break;
            case 2:
                tempProductList.sort(Comparator.comparingDouble(Product::getPrice).reversed());
                break;
            case 3:
                tempProductList.sort(Comparator.comparing(Product::getName));
                break;
            case 4:
                tempProductList.sort(Comparator.comparing(Product::getName).reversed());
                break;
            case 5:
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

    public static Product findProductByName(String productName) {
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

    public static void appendWishCartlist(Scanner scanner, User currentUser, int choice) {
        if (currentUser instanceof Customer || currentUser instanceof Member) {
            System.out.print("Enter the product name to add to Cart: ");
            String productName = scanner.nextLine();
            Product productToAdd = Product.findProductByName(productName);
            if (productToAdd != null) {
                if (choice == 4) {
                    currentUser.addToCart(productToAdd);
                    System.out.println(productToAdd.getName() + " added to Wishlist.");
                    return;
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
}
