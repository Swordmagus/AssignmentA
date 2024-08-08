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
}

class Cart extends CartAndWish {
    @Override
    public void viewProducts() {
        System.out.println("Cart:");
        super.viewProducts();
    }
}

class Wishlist extends CartAndWish {
    @Override
    public void viewProducts() {
        System.out.println("Wishlist:");
        super.viewProducts();
    }
}
