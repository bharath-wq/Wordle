import java.io.*;
import java.util.ArrayList;
import java.util.Random;
/**
 * WordLibrary
 *
 * provides WordLibrary methods and content
 *
 * @author Bharath Sadagopan
 *
 * @version 10/31/2022
 *
 */
public class WordLibrary {

    private String[] library;
    private int seed;
    private Random random;
    private String fileName;

    public WordLibrary(String fileName) {
        this.fileName = fileName;
        ArrayList<String> fileValues = new ArrayList<>();
        try {
            File f = new File(fileName);
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            String lineX = bfr.readLine();
            this.seed = Integer.parseInt(lineX.substring(lineX.indexOf(" ") + 1));
            this.random = new Random(seed);
            while (lineX != null) {
                fileValues.add(lineX);
                lineX = bfr.readLine();
            }
            library = new String[fileValues.size() - 1];
            for (int i = 1; i < fileValues.size(); i++) {
                library[i - 1] = fileValues.get(i);
            }
            this.processLibrary();

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    public void verifyWord(String word) throws InvalidWordException {
        if (word.length() != 5) {
            throw new InvalidWordException("Invalid word!");
        }
    }

    public void processLibrary() throws InvalidWordException {
        ArrayList<String> fullLibrary = new ArrayList<String>();
        for (int i = 0; i < library.length; i++) {
            try {
                verifyWord(library[i]);
                fullLibrary.add(library[i]);
            } catch (InvalidWordException e) {
                System.out.println(e.getMessage());
            }
        }
        library = fullLibrary.toArray(new String[0]);
    }

    public String chooseWord() {
        return library[random.nextInt(library.length)];
        //return ("");
    }

    public String[] getLibrary() {
        return library;
    }

    public void setLibrary(String[] library) {
        this.library = library;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

}
