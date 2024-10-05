import java.util.Random;
import java.util.Scanner;

class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean playAgain = true;
        int roundsWon = 0;
        int totalAttempts = 0;

        System.out.println("Welcome to the Number Guessing Game!");

        while (playAgain) {
            int attempts = 0;
            int maxAttempts = 7;
            int randomNumber = random.nextInt(100) + 1; // Random number between 1 and 100
            boolean hasWon = false;

            System.out.println("\nI have selected a random number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess it!");

            while (attempts < maxAttempts) {
                System.out.print("Attempt " + (attempts + 1) + ": Enter your guess: ");
                int guess = scanner.nextInt();
                attempts++;
                totalAttempts++;

                if (guess < randomNumber) {
                    System.out.println("Too low!");
                } else if (guess > randomNumber) {
                    System.out.println("Too high!");
                } else {
                    System.out.println("Congratulations! You've guessed the correct number in " + attempts + " attempts.");
                    roundsWon++;
                    hasWon = true;
                    break;
                }
            }

            if (!hasWon) {
                System.out.println("Sorry, you've used all your attempts. The correct number was " + randomNumber + ".");
            }

            System.out.print("Do you want to play again? (yes/no): ");
            String playAgainInput = scanner.next().toLowerCase();
            if (!playAgainInput.equals("yes")) {
                playAgain = false;
            }
        }

        System.out.println("\nGame Over! You won " + roundsWon + " rounds.");
        System.out.println("Total attempts: " + totalAttempts);
        
        scanner.close();
    }
}
