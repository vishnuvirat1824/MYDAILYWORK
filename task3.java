import java.util.Scanner;
class BankAccount {
    private double balance;
    private String pin;

    // Constructor to initialize the account with an opening balance and a PIN
    public BankAccount(double initialBalance, String pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    // Method to check the account balance
    public double getBalance() {
        return balance;
    }

    // Method to deposit money into the account
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Current balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive value.");
        }
    }

    // Method to withdraw money from the account
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Current balance: $" + balance);
        } else if (amount > balance) {
            System.out.println("Insufficient funds. Your current balance is: $" + balance);
        } else {
            System.out.println("Invalid withdrawal amount. Please enter a positive value.");
        }
    }

    // Method to validate the entered PIN
    public boolean validatePin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    // Method to change the user's PIN
    public void changePin(String currentPin, String newPin) {
        if (validatePin(currentPin)) {
            this.pin = newPin;
            System.out.println("PIN changed successfully.");
        } else {
            System.out.println("Incorrect current PIN. Unable to change PIN.");
        }
    }
}
class ATM {
    private BankAccount account;
    private final int MAX_PIN_ATTEMPTS = 3;

    // Constructor to associate the ATM with a user's bank account
    public ATM(BankAccount account) {
        this.account = account;
    }

    // Method to display the ATM menu and handle user input
    public void showMenu() {
        if (!pinValidation()) {
            return; // Exit if PIN validation fails
        }

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 5) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Change PIN");
            System.out.println("5. Exit");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    depositMoney(scanner);
                    break;
                case 3:
                    withdrawMoney(scanner);
                    break;
                case 4:
                    changePin(scanner);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
        scanner.close();
    }

    // Method to validate PIN with a limited number of attempts
    private boolean pinValidation() {
        Scanner scanner = new Scanner(System.in);
        int attempts = 0;

        while (attempts < MAX_PIN_ATTEMPTS) {
            System.out.print("Enter your 4-digit ATM PIN: ");
            String enteredPin = scanner.nextLine();

            if (account.validatePin(enteredPin)) {
                System.out.println("PIN verified. Access granted.");
                return true;
            } else {
                attempts++;
                System.out.println("Incorrect PIN. Attempt " + attempts + " of " + MAX_PIN_ATTEMPTS);
            }

            if (attempts >= MAX_PIN_ATTEMPTS) {
                System.out.println("Too many incorrect attempts. Access denied.");
                return false;
            }
        }
        return false;
    }

    // Method to check the current balance
    private void checkBalance() {
        System.out.println("Your current balance is: $" + account.getBalance());
    }

    // Method to handle depositing money
    private void depositMoney(Scanner scanner) {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
    }

    // Method to handle withdrawing money
    private void withdrawMoney(Scanner scanner) {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
    }

    // Method to change the user's PIN
    private void changePin(Scanner scanner) {
        scanner.nextLine(); // Clear the buffer
        System.out.print("Enter your current PIN: ");
        String currentPin = scanner.nextLine();

        System.out.print("Enter your new 4-digit PIN: ");
        String newPin = scanner.nextLine();

        account.changePin(currentPin, newPin);
    }

    public static void main(String[] args) {
        // Create a bank account with an initial balance and a PIN
        BankAccount userAccount = new BankAccount(1000, "1234"); // Example: $1000 balance and PIN: 1234

        // Create an ATM instance linked to the user's account
        ATM atm = new ATM(userAccount);

        // Display the ATM menu
        atm.showMenu();
}
}
