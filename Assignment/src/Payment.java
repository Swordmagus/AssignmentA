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
        scanner.close();
        return true;
    }

    private double calculateTotalPrice(User currentUser) {
        double totalPrice = 0.0;
        for (Product product : currentUser.getCart().getProducts()) {
            totalPrice += product.getPrice();
        }
        return totalPrice;
    }

    private void reduceInventory(User currentUser) {
        for (Product product : currentUser.getCart().getProducts()) {

            product.setInventory(product.getinventory() - 1);
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
        String cardType = scanner.nextLine();
        while (true) {
            if (cardType.equalsIgnoreCase("credit") || cardType.equalsIgnoreCase("debit")) {
                break;
            } else {
                System.out.print("\nInvalid card type. Please enter 'Credit' or 'Debit'.");
                checkout(currentUser);
            }
        }
        
        int cardNumber = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("\nEnter Card Number: ");
            if (scanner.hasNextInt()) {
                cardNumber = scanner.nextInt();
                validInput = true;
            } else {
                System.out.print("\nInvalid input. Please enter a valid 16-digit card number.: ");
            }
        }

        System.out.print("Enter Expiration Date (MM/YY): ");
        String expirationDate = scanner.nextLine();

        System.out.print("Enter CCV: ");
        String ccv = scanner.nextLine();

        scanner.close();

        if (validateCardDetails(cardNumber, expirationDate, ccv)) {
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

    private boolean validateCardDetails(int cardNumber, String expirationDate, String ccv) {
        String cardNumberStr = String.valueOf(cardNumber);
        return cardNumberStr.length() == 16 && expirationDate.matches("\\d{2}/\\d{2}") && ccv.length() == 3;
    }

    private double calculateTotalPrice(User currentUser) {
        double totalPrice = 0.0;
        if (currentUser instanceof Member) {
            double discount = ((Member) currentUser).getDiscount();
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice() * (1 - discount / 100);
            }
        } else {
            for (Product product : currentUser.getCart().getProducts()) {
                totalPrice += product.getPrice();
            }
        }
        return totalPrice;
    }

    private void reduceInventory(User currentUser) {
        for (Product product : currentUser.getCart().getProducts()) {

            product.setInventory(product.getinventory() - 1);
            currentUser.getCart().removeProduct(product);
        }
    }
}
