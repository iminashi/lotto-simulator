package fi.tuni.tamk.tiko.malmbergtapio;

import fi.tuni.tamk.tiko.malmbergtapio.util.Math;
import fi.tuni.tamk.tiko.malmbergtapio.util.Arrays;
import fi.tuni.tamk.tiko.malmbergtapio.util.Console;

/**
 * Contains the main program for the lottery simulation program.
 *
 * @author Tapio Malmberg
 */
public class LottoApp {
    private static final int WEEKS_IN_A_YEAR = 365 / 7;
    private static final int YEARS_IN_A_LIFETIME = 120;

    /**
     * Main program.
     * 
     * @param args command line arguments.
     */
    public static void main(String [] args) {
        // Get the settings for the lotto game
        LottoSettings lottoSettings = getLottoSettings();

        // Try to get the user numbers from command line arguments
        int[] userNumbers = getUserNumbersFromArgs(lottoSettings, args);

        // Prompt the user for how much info they want to see
        InfoAmount infoAmount = getInfoAmount();
        
        // Prompt the user for the lottery numbers
        // if the command line arguments were not given or were invalid
        if(userNumbers == null) {
            userNumbers = getUserNumbers(lottoSettings);
        }

        long yearsToJackpot = 0;
        boolean keepTrying = true;

        while(keepTrying) {
            yearsToJackpot = playUntilJackpot(lottoSettings, userNumbers, infoAmount);

            if(yearsToJackpot > YEARS_IN_A_LIFETIME) {
                System.out.println("It took more than a lifetime so let's try that again.");
            } else {
                System.out.println("Congratulations, you might still be alive to use your money!");
                keepTrying = false;
            }
        } 
    }

    /**
     * Prompts the user to select a lottery game type.
     * 
     * Returns the settings for Finnish Lotto
     * if loading the settings file fails.
     * 
     * @return settings for the game type the user chose, or default settings.
     */
    private static LottoSettings getLottoSettings() {
        LottoSettings lottoSettings;
        LottoSettings[] availableSettings = LottoSettings.getAllSettings();

        if(availableSettings != null) {
            System.out.println("Select lottery type: ");
            for (int i = 0; i < availableSettings.length; i++) {
                System.out.println((i+1) + ". " + availableSettings[i].name);
            }

            int choice = Console.readInt(1, availableSettings.length + 1, "Give a number.", "Range [1-" + availableSettings.length + "]");

            lottoSettings = availableSettings[choice - 1];
        } else {
            lottoSettings = LottoSettings.FinnishLotto;
        }

        return lottoSettings;
    }

    /**
     * Tries to get the lottery numbers from the command line arguments.
     * 
     * Returns null if:
     * The amount of numbers given does not match the settings
     * A number is not in the correct range
     * All of the arguments could not be converted to integers
     * 
     * @param settings the settings used for the lottery.
     * @param args the command line arguments given to the program.
     * @return the numbers parsed from the arguments or null if an error occurred.
     */
    private static int[] getUserNumbersFromArgs(LottoSettings settings, String[] args) {
        int[] userNumbers = null;

        if(args.length > 0 && args.length != settings.numberCount) {
            System.out.println("ERROR: Invalid number of arguments.");
            System.out.println("Numbers needed: " + settings.numberCount);
        } else if(args.length == settings.numberCount) {
            userNumbers = new int[settings.numberCount];
            
            try {
                for (int i = 0; i < userNumbers.length; i++) {
                    int number = Integer.parseInt(args[i]);

                    if(Arrays.contains(number, userNumbers)) {
                        System.out.println("ERROR: Duplicate number in arguments: " + number);
                        return null;
                    } else if(number < 1 || number > settings.maxNumber) {
                        System.out.println("ERROR: Invalid number in arguments: " + number);
                        return null;
                    }

                    userNumbers[i] = number;
                }
            } catch(NumberFormatException e) {
                System.out.println("ERROR: Could not convert all the arguments to integers.");
                userNumbers = null;
            }
        }

        return userNumbers;
    }

    /**
     * Prompts the user for the lottery numbers they want to play one by one.
     * 
     * @param settings the settings used for the lottery.
     * @return an array of unique numbers the user choce.
     */
    private static int[] getUserNumbers(LottoSettings settings) {
        int[] userNumbers = new int[settings.numberCount];
        int i = 0;

        while (i < userNumbers.length) {
            String prompt = "Please give a unique number between [1, " + settings.maxNumber + "]";

            System.out.println(prompt);
            int number = Console.readInt(1, settings.maxNumber, "Please give a number.", prompt);

            if(!Arrays.contains(number, userNumbers)) {
                userNumbers[i] = number;
                i++;
            } else {
                System.out.println("Not unique numbers!");
            }
        }

        return userNumbers;
    }

    /**
     * Generates random lottery numbers.
     * 
     * The amount and range of numbers generated depends on the settings.
     * 
     * @param settings the settings used for the lottery.
     * @return an array of unique random numbers.
     */
    private static int[] calculateLotto(LottoSettings settings) {
        // Generate an array of numbers to choose from
        int[] availableNumbers = new int[settings.maxNumber];
        for (int i = 0; i < availableNumbers.length; i++) {
            availableNumbers[i] = i + 1;
        }

        int[] numbers = new int[settings.numberCount];

        for (int i = 0; i < numbers.length; i++) {
            int randomIndex = Math.getRandom(i, availableNumbers.length - 1);

            numbers[i] = availableNumbers[randomIndex];

            // Swap the numbers to prevent duplicates
            if(randomIndex != i) {
                int temp = availableNumbers[i];
                availableNumbers[i] = availableNumbers[randomIndex];
                availableNumbers[randomIndex] = temp;
            }
        }

        return numbers;
    }

    /**
     * Keeps running a lottery until the player wins the jackpot (all numbers correct).
     * 
     * Returns the amount of time in years it took to win the jackpot.
     * 
     * @param settings the settings used for the lottery.
     * @param userNumbers the numbers chosen by the player.
     * @param infoAmount the amount of info the user wishes to see.
     * @return the amount of years it took to win the jackpot.
     */
    private static long playUntilJackpot(LottoSettings settings, int[] userNumbers, InfoAmount infoAmount) {
        int correct = 0;
        long weeks = 0;
        long years = 0;
        int target = 1;

        do {
            int[] lottoNumbers = calculateLotto(settings);
            
            // Sort the numbers
            lottoNumbers = Arrays.sort(lottoNumbers);
            userNumbers = Arrays.sort(userNumbers);

            weeks++;

            // Get the amount of numbers the were correct
            correct = Arrays.countSameValues(lottoNumbers, userNumbers);

            if(infoAmount == InfoAmount.EVERYWEEK) {
                printLottoNumbers(lottoNumbers, userNumbers);

                System.out.println("You got " + correct + " numbers right.");
            }

            while (correct >= target) {
                if(infoAmount == InfoAmount.RESULTS) {
                    printLottoNumbers(lottoNumbers, userNumbers);
                }

                years = weeks / WEEKS_IN_A_YEAR;

                String yearsStr = years == 1 ? "year" : "years";
                System.out.print("Got " + target + " right! Took " + years + " " + yearsStr);

                // Print the weeks if it took less than a year to win
                if(years == 0) {
                    String weeksStr = weeks == 1 ? "week" : "weeks";
                    System.out.println(" ("+ weeks + " " + weeksStr + ")");
                } else {
                    System.out.println();
                }

                target++;
            }
        } while (correct != settings.numberCount);

        System.out.println("You won!");

        return years;
    }

    /**
     * Prompts the user for how much information they want to see.
     * 
     * @return InfoAmount enum matching the user's choice.
     */
    private static InfoAmount getInfoAmount() {
        System.out.println("How much information do you want?");
        System.out.println("1. How many years it took to win.");
        System.out.println("2. Right lotto numbers after each win.");
        System.out.println("3. Lotto numbers for every week.");
        int choice = Console.readInt(1, 3, "Please give a number.", "[1-3]");

        switch (choice) {
            case 2: return InfoAmount.RESULTS;
            case 3: return InfoAmount.EVERYWEEK;
            default: return InfoAmount.NONE;
        }
    }

    /**
     * Prints the winning numbers and the user's numbers into the console.
     * 
     * @param lottoNumbers the winning lottery numbers.
     * @param userNumbers the numbers chosen by the user.
     */
    private static void printLottoNumbers(int[] lottoNumbers, int[] userNumbers) {
        String[] userLotto = Arrays.padLeft(userNumbers, '0', 2);
        String[] randomLotto = Arrays.padLeft(lottoNumbers, '0', 2);

        System.out.print("User lotto:\t");
        Console.printArray(userLotto);

        System.out.print("Random lotto:\t");
        Console.printArray(randomLotto);
    }
}