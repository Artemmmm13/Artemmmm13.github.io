import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class CaesarCipher {

    private final Scanner scanner = new Scanner(System.in);

    public void printMenu(String typeOfCipher) {
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.printf("""
                [%s]
                Please enter what operation you would like to perform
                Type E for Encrypt, D for Decrypt, or X for Exit \n""", typeOfCipher);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    public String getUserInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }

    private int getShiftAmount() {
        int messageShiftAmount;
        while (true) {
            String shiftAmountStr = getUserInput("Please enter the amount of bytes you want to shift as an integer: ");
            try {
                messageShiftAmount = Integer.parseInt(shiftAmountStr);
                if (isValidShiftAmount(messageShiftAmount)) {
                    break;
                } else {
                    System.out.println("Shift amount must be between -256 and 256. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input wasn't an integer\n" +
                        "Please try again");
            }
        }
        return messageShiftAmount;
    }

    public boolean isValidMessageInput(String message) {
        return message.trim().isEmpty();
    }

    public byte[] shiftBytes(byte[] bytes, int shiftAmount) {
        byte[] shiftedBytes = new byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            shiftedBytes[i] = (byte) ((bytes[i] + shiftAmount) % 256);
        }

        return shiftedBytes;
    }

    public String encrypt() {
        String userMessage;
        int messageShiftAmount;
        do {
            userMessage = getUserInput("Please enter the message you want to encrypt");
        } while (isValidMessageInput(userMessage));

        do {
            messageShiftAmount = getShiftAmount();
        } while (!isValidShiftAmount(messageShiftAmount));

        if (messageShiftAmount < 0){
            messageShiftAmount = messageShiftAmount+256;
        }
        byte[] shiftedBytes = shiftBytes(userMessage.getBytes(StandardCharsets.UTF_8), messageShiftAmount);

        return Base64.getEncoder().encodeToString(shiftedBytes);
    }

    public String decrypt() {
        String userMessage;
        int messageShiftAmount;
        do {
            userMessage = getUserInput("Please enter the message you want to decrypt");
        } while (isValidMessageInput(userMessage));

        do {
            messageShiftAmount = getShiftAmount();
        } while (!isValidShiftAmount(messageShiftAmount));

        if (messageShiftAmount<0){
            messageShiftAmount = messageShiftAmount+256;
        }

        byte[] unShiftedBytes = deShiftBytes(userMessage, messageShiftAmount);

        return new String(unShiftedBytes, StandardCharsets.UTF_8);
    }

    public void doCipher(String typeOfCipher) {
        String userChoice;

        do {
            printMenu(typeOfCipher);
            userChoice = scanner.nextLine().trim();

            switch (userChoice.toUpperCase()) {
                case "E":
                    System.out.println(encrypt());
                    break;
                case "D":
                    try {
                        System.out.println(decrypt());
                    } catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "X":
                    System.out.println("See you later!");
                    break;
                default:
                    System.out.println("Your input: " + "(" + userChoice+ ") " +  "didn't match any of the following possible: E, D or X\n" +
                            "Please try again");
                    break;
            }
        } while (!userChoice.equalsIgnoreCase("X"));
    }

    public boolean isValidShiftAmount(int shiftAmount){
        return shiftAmount >= -256 && shiftAmount <= 256;
    }

    public byte[] deShiftBytes(String msg, int shiftAmount){
        if (!isBase64String(msg)){
            throw new IllegalArgumentException("Please enter encrypted message ( Base64 string )");
        }
        byte[] msgBytes = Base64.getDecoder().decode(msg);
        byte[] unShiftedBytes = new byte[msgBytes.length];

        for (int i = 0; i < msgBytes.length; i++) {
            unShiftedBytes[i] = (byte) ((msgBytes[i] - shiftAmount) % 256);
        }
        return unShiftedBytes;

    }

    public static boolean isBase64String(String input) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(input);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
