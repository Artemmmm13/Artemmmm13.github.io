import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class VigenereCipher extends CaesarCipher{
    @Override
    public String encrypt(){
        String userMessage;
        String userKey;
        do {
            userMessage = getUserInput("Please enter the message you want to encrypt");
        } while (isValidMessageInput(userMessage));

        do {
            userKey = getUserInput("Please enter the encryption key");
        } while (isValidMessageInput(userKey));

        userKey = adjustLengthOfKey(userKey, userMessage);


        byte[] shiftedMessageBytes = shiftBytes(userMessage.getBytes(StandardCharsets.UTF_8),
                userKey.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(shiftedMessageBytes);

    }

    @Override
    public String decrypt(){
        String userMessage;
        String userKey;
        do {
            userMessage = getUserInput("Please enter the message you want to decrypt");
        } while (isValidMessageInput(userMessage));

        do {
            userKey = getUserInput("Please enter the encryption key");
        } while (isValidMessageInput(userKey));

        byte[] deShiftedBytes = deShiftBytes(userMessage,userKey);

        return new String(deShiftedBytes, StandardCharsets.UTF_8);
    }

    private String adjustLengthOfKey(String key, String messageToEncrypt){
        if (key.length() == messageToEncrypt.length()) return key;
        return fillCharArr(messageToEncrypt, key);
    }


        private String fillCharArr(String msg, String key){
            char[] adjustedKey = new char[msg.length()];
            int j = 0;

            for (int i = 0; i < msg.length(); i++){
                if (j == key.length()) {
                    j = 0;
                }
                adjustedKey[i] = key.charAt(j);
                j++;
            }

            return new String(adjustedKey);
        }

    private byte[] deShiftBytes(String message, String key){
        byte[] messageBytes;
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);

        try{
            messageBytes = Base64.getDecoder().decode(message);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Please enter an encrypted message( Base64 string )");
        }

        byte[] messageDeShiftedBytes = new byte[messageBytes.length];

            for (int i = 0; i < messageBytes.length; i++){
                messageDeShiftedBytes[i] = (byte) ((messageBytes[i] - keyBytes[i]+256)%256);
            }

        return messageDeShiftedBytes;
    }

    private byte[] shiftBytes(byte[] messageBytes, byte[] keyBytes){
        byte[] messageShiftedBytes = new byte[messageBytes.length];

        for (int i = 0; i < messageBytes.length; i++){
            messageShiftedBytes[i] = (byte) ((messageBytes[i] + keyBytes[i])%256);
        }
        return messageShiftedBytes;
    }


}
