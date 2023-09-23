import java.util.Scanner;
import org.hw1.CaesarCipher;

public class Main {

    public static void main(String[] args) {
        CaesarCipher caesarCipher = new CaesarCipher();
        VigenereCipher vigenereCipher = new VigenereCipher();
        Scanner scanner = new Scanner(System.in);
        String userChoice;

        do {
            System.out.println("----------------------------------------------------------");
            System.out.println("Enter the cipher you want to choose\n" +
                    "Type C for Caesar, V for Vigenere or X if you want to exit");
            System.out.println("----------------------------------------------------------");
            userChoice = scanner.nextLine().trim();

            switch (userChoice.toUpperCase()){
                case "C":
                    caesarCipher.doCipher("Caesar Cipher");
                    break;
                case "V":
                    vigenereCipher.doCipher("Vigenere Cipher");
                    break;
                case "X":
                    System.out.println("See you later!");
                    break;
                default:
                    System.out.println("Your input: " + "(" + userChoice+ ") " +  "didn't match any of the following possible: V, C or X\n" +
                            "Please try again");
                    break;
            }
        } while (!userChoice.equalsIgnoreCase("X"));
    }
}
