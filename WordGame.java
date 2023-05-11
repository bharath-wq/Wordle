import java.io.*;
import java.util.*;
import java.io.FileWriter;

/**
 * WordGame
 * <p>
 * provides WordGame methods and content
 *
 * @author Bharath Sadagopan
 * @version 10/31/2022
 */
public class WordGame {

    public static String welcome = "Ready to play?";
    public static String yesNo = "1.Yes\n2.No";
    public static String noPlay = "Maybe next time!";
    public static String currentRoundLabel = "Current Round: ";
    public static String enterGuess = "Please enter a guess!";
    public static String winner = "You got the answer!";
    public static String outOfGuesses = "You ran out of guesses!";
    public static String solutionLabel = "Solution: ";
    public static String incorrect = "That's not it!";
    public static String keepPlaying = "Would you like to make another guess?";
    public static String fileNameInput = "Please enter a filename";


    public static void updateGameLog(String solution, String[] guesses, boolean solved) {
        String gameLogData = "";
        int gameNumber = 1;
        int gamesCompleted = 0;
        boolean continue1 = true;
        String solvedQuestion = "";
        try {
            File f = new File("gamelog.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            BufferedReader bfr = new BufferedReader(new FileReader(f));
            String lineA = bfr.readLine();
            if (lineA == null || lineA.equalsIgnoreCase(" ")) {
                gamesCompleted = 1;
            } else {
                gamesCompleted = Integer.parseInt(lineA.substring(17));
                gamesCompleted++;
                gameNumber = gamesCompleted;
            }
            if (lineA != null) {
                lineA = bfr.readLine();
            }
            while (lineA != null) {
                gameLogData += lineA + "\n";
                lineA = bfr.readLine();
            }
            bfr.close();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            BufferedWriter bfw = new BufferedWriter(new FileWriter("gamelog.txt", false));
                    /*if (gamesCompleted != 1) {

                    }*/
            do {
                bfw.write("Games Completed: " + gamesCompleted);
                bfw.newLine();
                continue1 = false;
            } while (continue1 == true);
            if (gameNumber != 1) {
                bfw.write(gameLogData);
            }

            bfw.write("Game " + gameNumber);
            bfw.newLine();
            bfw.write("- Solution: " + solution);
            bfw.newLine();
            String allGuesses = "";
            for (int i = 0; i < guesses.length - 1; i++) {
                allGuesses += (guesses[i] + ",");
            }
            allGuesses += guesses[guesses.length - 1];
            bfw.write("- Guesses: " + allGuesses);
            bfw.newLine();
            if (solved == true) {
                solvedQuestion = "Yes";
            } else {
                solvedQuestion = "No";
            }
            bfw.write("- Solved: " + solvedQuestion);
            bfw.newLine();
            bfw.flush();
            bfw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] guesses;
        ArrayList<String> guessList = new ArrayList<>();
        boolean solved = false;
        String solution = "";
        System.out.println(fileNameInput);
        String fileName = scanner.nextLine();
        WordLibrary wordLibrary = new WordLibrary(fileName); // takes in filename as a String and then takes
        int count = 1;
        System.out.println(welcome);
        System.out.println(yesNo);
        int ready = scanner.nextInt();
        scanner.nextLine();
        if (ready == 1) {
            do {
                solution = wordLibrary.chooseWord();
                WordGuesser wordGuesser = new WordGuesser(solution);
                do {
                    wordGuesser.setRound(count);
                    System.out.println(currentRoundLabel + wordGuesser.getRound());
                    wordGuesser.printField();
                    System.out.println(enterGuess);
                    String guess = scanner.nextLine();
                    guessList.add(guess);
                    try {
                        wordGuesser.checkGuess(guess);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (guess.equalsIgnoreCase(solution)) {
                        System.out.println(winner);
                        try {
                            wordGuesser.checkGuess(guess);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        solved = true;
                        ready = 0;
                    } else {
                        if (count == 5) {
                            System.out.println(outOfGuesses);
                            System.out.printf(solutionLabel + "%s", solution);
                            System.out.println();
                            ready = 0;
                        } else {
                            System.out.println(incorrect);
                            System.out.println(keepPlaying);
                            System.out.println(yesNo);
                            ready = scanner.nextInt();
                            scanner.nextLine();
                            count++;
                        }
                    }

                } while (ready == 1);
                guesses = new String[guessList.size()];
                for (int i = 0; i < guesses.length; i++) {
                    guesses[i] = guessList.get(i);
                }
                updateGameLog(solution, guesses, solved);
                guessList.clear();
                wordGuesser.printField();
                System.out.println(welcome);
                System.out.println(yesNo);
                ready = scanner.nextInt();
                scanner.nextLine();
                count = 1;
            } while (ready == 1);
        }
        System.out.println(noPlay);


    }
}
