import java.lang.invoke.WrongMethodTypeException;
/**
 * WordGuesser
 *
 * provides WordGuesser methods and content
 *
 * @author Bharath Sadagopan
 *
 * @version 10/31/2022
 *
 */
public class WordGuesser {
    private String[][] playingField;
    private int round;
    private String solution;

    public WordGuesser(String solution) {
        this.solution = solution;
        round = 1;
        this.playingField = new String[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 4) {
                    this.playingField[i][j] = (" ");
                } else {
                    this.playingField[i][j] = (" |");
                }
            }
        }
    }
    public String [][] getPlayingField() {
        return this.playingField;
    }
    public int getRound() {
        return round;
    }
    public String getSolution() {
        return solution;
    }
    public void setPlayingField(String[][] playingField) {
        this.playingField = playingField;
    }
    public void setRound(int round) {
        this.round = round;
    }
    public void  setSolution(String solution) {
        this.solution = solution;
    }
    int row = 0;
    public boolean checkGuess(String guess) throws InvalidGuessException {
        if (guess.equalsIgnoreCase(solution)) {
            for (int i = 0; i < guess.length() - 1; i++) {
                this.playingField[row][i] = ("'" + guess.charAt(i) + "' | ");
            }
            this.playingField[row][4] = ("'" + guess.charAt(4) + "'");
            return true;
        } else {
            if (guess.length() != 5) {
                throw new InvalidGuessException("Invalid Guess!");
            } else {
                for (int j = 0; j < guess.length() - 1; j++) {
                    if (guess.charAt(j) == solution.charAt(j)) {
                        this.playingField[row][j] = ("'" + guess.charAt(j) + "' | ");
                    } else if (solution.indexOf(guess.charAt(j)) != -1) {
                        this.playingField[row][j] = ("*" + guess.charAt(j) + "* | ");
                    } else {
                        this.playingField[row][j] = ("{" + guess.charAt(j) + "} | ");
                    }
                }
                if (guess.charAt(4) == solution.charAt(4)) {
                    this.playingField[row][4] = ("'" + guess.charAt(4) + "'");
                } else if (solution.indexOf(guess.charAt(4)) != -1) {
                    this.playingField[row][4] = ("*" + guess.charAt(4) + "*");
                } else {
                    this.playingField[row][4] = ("{" + guess.charAt(4) + "}");
                }
            }
            row++;
        }
        return false;
    }
    public void printField() {
        for (int i = 0; i < this.playingField.length; i++) {
            for (int j = 0; j < this.playingField.length; j++) {
                System.out.print(this.playingField[i][j]);
            }
            System.out.println();
        }
    }
}
