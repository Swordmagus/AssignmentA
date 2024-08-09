import java.util.ArrayList;
import java.util.List;

public abstract class CartAndWish {
    protected List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void viewProducts() {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

    public void removeProduct(Product product, boolean shouldPrint) {
        if (shouldPrint) {
            System.out.println("Removing product: " + product);
        }
        removeProduct(product); 
    }
}

class Cart extends CartAndWish {
    @Override
    public void viewProducts() {
        System.out.println("Cart:");
        super.viewProducts();
    }

    @Override
    public void removeProduct(Product product) {
        super.removeProduct(product);
    }
    @Override
    public void removeProduct(Product product, boolean shouldPrint) {
        super.removeProduct(product, shouldPrint);
    }


    public static void removeAllProductsFromCart(User currentUser) {
        List<Product> productsInCart = currentUser.getCart().getProducts();

        if (productsInCart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        for (Product product : productsInCart) {
            currentUser.getCart().removeProduct(product);
        }
        System.out.println("All products have been removed from the cart.");
    }

    public static Product findProductInCart(CartAndWish cartAndWish, String productName) {
        for (Product product : cartAndWish.getProducts()) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null; 
    }
}

class Wishlist extends CartAndWish {
    @Override
    public void viewProducts() {
        System.out.println("Wishlist:");
        super.viewProducts();
    }

    @Override
    public void removeProduct(Product product) {
        System.out.println("Removing product from wishlist: " + product);
        super.removeProduct(product);
    }


    protected static void addAllWishlistToCart(User currentUser) {
        List<Product> wishlist = currentUser.getWishlist().getProducts(); // get wishlist

        if (wishlist.isEmpty()) {
            System.out.println("Your wishlist is empty.");
            return;
        }
        List<Product> wishlistCopy = new ArrayList<>(wishlist); // copy list

        for (Product product : wishlistCopy) {
            currentUser.addToCart(product);
            currentUser.getWishlist().removeProduct(product);
        }
        System.out.println("All wishlist products have been added to the cart.\n");
    }
}
