import java.util.Random;

public abstract class User {
    protected String name;
    protected CartAndWish wishlist;
    protected CartAndWish cart;

    public User(String name) {
        this.name = name;
        this.wishlist = new Wishlist(); // Initialize Wishlist for each user
        this.cart = new Cart(); // Initialize Cart for each user
    }

    public String getName() {
        return name;
    }

    public void addToWishlist(Product product) {
        wishlist.addProduct(product);
    }

    public void addToCart(Product product) {
        cart.addProduct(product);
    }

    public void viewWishlist() {
        wishlist.viewProducts();
    }

    public void viewCart() {
        cart.viewProducts();
    }

    public CartAndWish getWishlist() {
        return wishlist;
    }

    public CartAndWish getCart() {
        return cart;
    }

    public abstract boolean canCheckout();


}

class Guest extends User {
    public Guest(String name) {
        super(name);
    }

    @Override
    public boolean canCheckout() {
        return false;
    }
}

class Customer extends User {
    public Customer(String name) {
        super(name);
    }

    @Override
    public boolean canCheckout() {
        return true;
    }
}

class Member extends Customer {
    private double discount;

    public Member(String name) {
        super(name);
        this.discount = 5 + new Random().nextInt(6); // discount between 5 and 10 percent 
    }

    public double getDiscount() {
        return discount;
    }

    @Override
    public void viewCart() {
        System.out.println("Cart (with " + discount + "% discount):");
        for (Product product : cart.getProducts()) {
            System.out.println(product);
        }
    }
}
