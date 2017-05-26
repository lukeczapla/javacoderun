package frisch.java.util;

/**
 * Created by luke on 6/16/16.
 */
public class InputProcessor {


    protected static String randomWord() {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int wordlength = (int)(8.0*Math.random())+3;
        String result = "";
        for (int i = 0; i < wordlength; i++) {
            result += alphabet.charAt((int)(alphabet.length()*Math.random()));
        }
        return result;
    }


    protected static String randomWords() {

        String result = randomWord();
        int extras = (int)(5*Math.random())+1;
        for (int i = 0; i < extras; i++) {
            result += " " + randomWord();
        }

        return result;

    }


    public static String processInput(String input) {
        // %word% : single random "word"
        // %words% : 2-6 words
        // %int% : signed integer
        // %uint% : non-negative (unsigned) integer
        // %money% : a dollar amount like 15.99

        if (input == null) return "";

        while (input.indexOf("%word%") != -1) {
            input = input.substring(0, input.indexOf("%word%")) +
                    randomWord() +
                    input.substring(input.indexOf("%word%") + "%word%".length(), input.length());
        }

        while (input.indexOf("%words%") != -1) {
            input = input.substring(0, input.indexOf("%words%")) +
                    randomWords() +
                    input.substring(input.indexOf("%words%") + "%words%".length(), input.length());

        }

        while (input.indexOf("%int%") != -1) {
            input = input.substring(0, input.indexOf("%int%")) +
                    ((int)(1000*Math.random()) - 500) +
                    input.substring(input.indexOf("%int%") + "%int%".length(), input.length());
        }

        while (input.indexOf("%uint%") != -1) {
            input = input.substring(0, input.indexOf("%uint%")) +
                    ((int)(1000*Math.random())) +
                    input.substring(input.indexOf("%uint%") + "%uint%".length(), input.length());
        }

        while (input.indexOf("%money%") != -1) {
            input = input.substring(0, input.indexOf("%money%")) +
                    String.format("%5.2f", (double)(100*Math.random())) +
                    input.substring(input.indexOf("%money%") + "%money%".length(), input.length());
        }

        while (input.indexOf("%float%") != -1) {
            input = input.substring(0, input.indexOf("%float%")) +
                    (double)(1000.0*Math.random()-500.0) +
                    input.substring(input.indexOf("%float%") + "%float%".length(), input.length());
        }

        while (input.indexOf("%change%") != -1) {
            double price = (100.0*Math.random());
            double paid = price + 10.00*Math.random();
            input = input.substring(0, input.indexOf("%change%")) +
                    (price + "\n" + paid) +
                    input.substring(input.indexOf("%change%") + "%change%".length(), input.length());
        }

        return input;

    }
}
