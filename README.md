# lotto-simulator
A simple console program written in Java that simulates playing the lottery. Made for a programming course in the Tampere University of Applied Sciences.

# What it does
The program keeps generating a group of random numbers until they all match the numbers the user chose.
The program calculates how many years it took for the player to win the jackpot and keeps running until the player wins in the lifetime of a human (<=120 years),  which is very unlikely to happen.

![Screenshot of the program running](https://i.imgur.com/gGxG1jD.png)

For simplicity, bonus numbers are not used.

# Compiling and running the program

- javac .\fi\tuni\tamk\tiko\malmbergtapio\util\\*.java
- javac .\fi\tuni\tamk\tiko\malmbergtapio\\*.java
- java fi/tuni/tamk/tiko/malmbergtapio/LottoApp

# Command line parameters

The lottery numbers can be given as command line parameters:

``java fi/tuni/tamk/tiko/malmbergtapio/LottoApp 2 4 6 8 10 12 14``

# Different types of lotteries

The program reads the types of lotteries available from the file LottoSettings.txt.

The file format is:

``lottery name;largest number;how many numbers are drawn``

# TODO

- Command line parameter for selecting the lottery type