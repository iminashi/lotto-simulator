package fi.tuni.tamk.tiko.malmbergtapio;

import java.nio.file.*;
import java.util.List;
import java.nio.charset.*;

/**
 * Immutable class for storing the settings for a lottery game.
 * 
 * Bonus numbers are not supported for simplicity.
 */
public class LottoSettings {
    /**
     * Settings for Finnish lotto (7 numbers, 1-39)
     */
    public static final LottoSettings FinnishLotto = new LottoSettings("Finnish Lotto", 39, 7);
    
    /**
     * Settings for Viking lotto (6 numbers, 1-48)
     */
    public static final LottoSettings Vikinglotto = new LottoSettings("Vikinglotto", 48, 6);

    /**
     * Settings for Eurojackpot (5 numbers, 1-50)
     */
    public static final LottoSettings Eurojackpot = new LottoSettings("Eurojackpot", 50, 5);

    private static LottoSettings[] allSettings;

    private static void loadAllSettings() {
        List<String> allLines = null;

        try {
            allLines = Files.readAllLines(Paths.get("LottoSettings.txt"), StandardCharsets.UTF_8);

            allSettings = new LottoSettings[allLines.size()];
            for (int i = 0; i < allSettings.length; i++) {
                String line = allLines.get(i);
                String[] properties = line.split(";");

                String name = properties[0];
                int maxNumber = Integer.parseInt(properties[1]);
                int numberCount = Integer.parseInt(properties[2]);

                allSettings[i] = new LottoSettings(name, maxNumber, numberCount);
            }
        }
        catch (Exception e) {
            System.out.println("ERROR: Loading the lotto settings file failed!");
            //e.printStackTrace();
        }
    }

    /**
     * Gets all the settings for the available lotto game types.
     * 
     * @return an array with all the available settings.
     */
    public static LottoSettings[] getAllSettings() {
        if(allSettings == null) {
            loadAllSettings();
        }

        return allSettings;
    }

    public final String name;
    public final int maxNumber;
    public final int numberCount;

    public LottoSettings(String name, int maxNumber, int numberCount) {
        this.name = name;
        this.maxNumber = maxNumber;
        this.numberCount = numberCount;
    }
}