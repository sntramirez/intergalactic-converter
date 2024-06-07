package com.ec.intergalactic.converter.domain.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class IntergalacticConverter {
    private static final Logger log = LoggerFactory.getLogger(IntergalacticConverter.class);

    private static final Map<String, String> intergalacticToRoman = new HashMap<>();
    private static final Map<String, Double> materialValues = new HashMap<>();


    public static void main(String[] args) {
        String[] input = {
                "glob is I",
                "prok is V",
                "pish is X",
                "tegj is L",
                "glob glob Silver is 34 Credits",
                "glob prok Gold is 57800 Credits",
                "pish pish Iron is 3910 Credits",
                "how much is pish tegj glob glob ?",
                "how many Credits is glob prok Silver ?",
                "how many Credits is glob prok Gold ?",
                "how many Credits is glob prok Iron ?",
                "how much wood could a woodchuck chuck if a woodchuck could chuck wood ?"
        };

        IntergalacticConverter intergalacticConverter = new IntergalacticConverter();
        for (String line : input) {
            intergalacticConverter.processQuery(line);
        }
    }

    public  void processQuery(String line) {
        if (line.matches(".* is [IVXLCDM]")) {
            processIntergalacticToRoman(line);
        } else if (line.matches(".* is \\d+ Credits")) {
            processCredits(line);
        } else if (line.startsWith("how much is")) {
            processHowMuch(line);
        } else if (line.startsWith("how many Credits is")) {
            processHowManyCredits(line);
        } else {
            log.info("I have no idea what you are talking about");
        }
    }

    private  void processIntergalacticToRoman(String line) {
        String[] parts = line.split(" is ");
        intergalacticToRoman.put(parts[0], parts[1]);
    }

    private  void processCredits(String line) {
        Pattern pattern = Pattern.compile("(.+) is (\\d+) Credits");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            String[] words = matcher.group(1).split(" ");
            int credits = Integer.parseInt(matcher.group(2));
            String material = words[words.length - 1];

            StringBuilder roman = new StringBuilder();
            for (int i = 0; i < words.length - 1; i++) {
                roman.append(intergalacticToRoman.get(words[i]));
            }

            int value = romanToDecimal(roman.toString());
            double unitValue = (double) credits / value;
            materialValues.put(material, unitValue);
        }
    }

    private  void processHowMuch(String line) {
        String query = line.replace("how much is ", "").replace(" ?", "");
        String[] words = query.split(" ");

        StringBuilder roman = new StringBuilder();
        for (String word : words) {
            roman.append(intergalacticToRoman.get(word));
        }

        int value = romanToDecimal(roman.toString());
        log.info("{} is {}", query, value);

    }

    private  void processHowManyCredits(String line) {
        String query = line.replace("how many Credits is ", "").replace(" ?", "");
        String[] words = query.split(" ");
        String material = words[words.length - 1];

        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            roman.append(intergalacticToRoman.get(words[i]));
        }

        int value = romanToDecimal(roman.toString());
        double credits = value * materialValues.get(material);
        log.info("{} is {} Credits", query, (int) credits);

    }

    private  int romanToDecimal(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);
        romanMap.put('L', 50);
        romanMap.put('C', 100);
        romanMap.put('D', 500);
        romanMap.put('M', 1000);

        int decimal = 0;
        for (int i = 0; i < roman.length(); i++) {
            if (i > 0 && romanMap.get(roman.charAt(i)) > romanMap.get(roman.charAt(i - 1))) {
                decimal += romanMap.get(roman.charAt(i)) - 2 * romanMap.get(roman.charAt(i - 1));
            } else {
                decimal += romanMap.get(roman.charAt(i));
            }
        }
        return decimal;
    }
}
