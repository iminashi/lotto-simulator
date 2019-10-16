package fi.tuni.tamk.tiko.malmbergtapio;

/**
 * Defines how much information is printed to the console.
 */
public enum InfoAmount {
    /**
     * The lottery numbers are not printed.
     */
    NONE,

    /**
     * The winning numbers are printed after each win.
     */
    RESULTS,
    
    /**
     * The winning numbers are printed for every week.
     */
    EVERYWEEK
}