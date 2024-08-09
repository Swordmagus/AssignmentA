import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public interface Payment {
    boolean checkout(User currentUser);

}

class CashOnDelivery implements Payment {
    @Override
    public boolean checkout(User currentUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your delivery address: ");
        String address = scanner.nextLine();

        double totalPrice = calculateTotalPrice(currentUser);
        System.out.println("Total Price: $" + totalPrice);
        System.out.println("Cash on delivery order placed successfully to: " + address);

        reduceInventory(currentUser);
        return true;
    }

    private double calculateTotalPrice(User currentUser) {
        double totalPrice = 0.0;
        if (currentUser instanceof Member) {
            double discount = ((Member) currentUser).getDiscount();
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice() * (1 - discount / 100);
            }
            System.out.println("Discount " + discount + "%");

        } else {
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice();
            }
        }
        return totalPrice;
    }


    private void reduceInventory(User currentUser) {
        List<Product> productsToRemove = new ArrayList<>();

        for (Product product : currentUser.getCart().getProducts()) {
            product.setInventory(product.getInventory() - 1);
            productsToRemove.add(product);
        }

        for (Product product : productsToRemove) {
            currentUser.getCart().removeProduct(product);
        }
    }
}

class OnlinePayment implements Payment {
    @Override
    public boolean checkout(User currentUser) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter your delivery address: ");
        String address = scanner.nextLine();

        System.out.print("\nEnter Card Type (Credit/Debit): ");
        String cardType = scanner.nextLine().toLowerCase();
        while (true) {
            if (cardType.equals("credit") || cardType.equals("debit")) {
                break;
            } else {
                System.out.print("\nInvalid card type. Please enter 'Credit' or 'Debit': ");
                cardType = scanner.nextLine().toLowerCase(); // Update the cardType here
            }
        }

        String cardNumberStr = "";
        while (true) {
            System.out.print("Enter your card number (10 Digits): ");
            String input = scanner.nextLine();
            try {
                int cardNumber = Integer.parseInt(input);
                cardNumberStr = Integer.toString(cardNumber);
                if (cardNumberStr.length() == 10) {
                    break;
                } else {
                    System.out.println("Card number must be 10 digits long.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid card number.");
            }
        }

        System.out.print("Enter Expiration Date (MM/YY): ");
        String expirationDate = scanner.nextLine();

        String ccv = "";
        while (true) {
            System.out.print("Enter CCV (3 Digits): ");
            String input = scanner.nextLine();
            try {
                int ccvNumber = Integer.parseInt(input);
                ccv = Integer.toString(ccvNumber);
                if (ccv.length() == 3) {
                    break;
                } else {
                    System.out.println("CCV must be 3 digits long.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid CCV.");
            }
        }

        if (validateCardDetails(cardNumberStr, expirationDate, ccv)) {
            double totalPrice = calculateTotalPrice(currentUser);
            System.out.println("Total Price: $" + totalPrice);
            System.out.println(cardType + " card payment successful.");
            System.out.println("Order placed successfully to: " + address);

            reduceInventory(currentUser);
            return true;
        } else {
            System.out.println("Invalid card details. Please try again.");
            return checkout(currentUser); // retry payment
        }
    }

    private boolean validateCardDetails(String cardNumberStr, String expirationDate, String ccv) {
        return cardNumberStr.length() == 10 && expirationDate.matches("\\d{2}/\\d{2}") && ccv.length() == 3;
    }

    private double calculateTotalPrice(User currentUser) {
        double totalPrice = 0.0;
        if (currentUser instanceof Member) {
            double discount = ((Member) currentUser).getDiscount();
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice() * (1 - discount / 100);
            }
            System.out.println(" Discount " + discount + "%");

        } else {
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice();
            }
        }
        return totalPrice;
    }

    private void reduceInventory(User currentUser) {
        List<Product> productsToRemove = new ArrayList<>();

        for (Product product : currentUser.getCart().getProducts()) {
            product.setInventory(product.getInventory() - 1);
            productsToRemove.add(product);
        }

        for (Product product : productsToRemove) {
            currentUser.getCart().removeProduct(product, false);
        }
    }
}
