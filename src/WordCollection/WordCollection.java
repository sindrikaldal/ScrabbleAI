package WordCollection;

import FileReader.FileReader;
import org.quinto.dawg.ModifiableDAWGSet;

import java.util.ArrayList;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class WordCollection {

    private FileReader fileReader;
    private ArrayList<Letter> letters;

    private static ModifiableDAWGSet dawg;

    public WordCollection() {

        fileReader = new FileReader();
        dawg = new ModifiableDAWGSet();
        letters = new ArrayList<Letter>();

        importWordCollection();
        initalizeLetters();

    }

    private void importWordCollection() {
        fileReader.storeWordCollection();
        dawg.addAll(fileReader.getLines());
        dawg.addAll(fileReader.getLines());
    }

    private void initalizeLetters() {

        letters.add(new Letter('A', 1, 11));
        letters.add(new Letter('Á', 3, 2));
        letters.add(new Letter('B', 5, 1));
        letters.add(new Letter('D', 5, 1));
        letters.add(new Letter('Ð', 2, 4));
        letters.add(new Letter('E', 3, 3));
        letters.add(new Letter('É', 7, 1));
        letters.add(new Letter('F', 3, 3));
        letters.add(new Letter('G', 3, 3));
        letters.add(new Letter('H', 4, 1));
        letters.add(new Letter('I', 1, 7));
        letters.add(new Letter('Í', 4, 1));
        letters.add(new Letter('J', 6, 1));
        letters.add(new Letter('K', 2, 4));
        letters.add(new Letter('L', 2, 5));
        letters.add(new Letter('M', 2, 3));
        letters.add(new Letter('N', 1, 7));
        letters.add(new Letter('O', 5, 1));
        letters.add(new Letter('Ó', 3, 2));
        letters.add(new Letter('P', 5, 1));
        letters.add(new Letter('R', 1, 8));
        letters.add(new Letter('S', 1, 7));
        letters.add(new Letter('T', 2, 6));
        letters.add(new Letter('U', 2, 6));
        letters.add(new Letter('Ú', 4, 1));
        letters.add(new Letter('V', 5, 1));
        letters.add(new Letter('X', 10, 1));
        letters.add(new Letter('Y', 6, 1));
        letters.add(new Letter('Ý', 5, 1));
        letters.add(new Letter('Þ', 7, 1));
        letters.add(new Letter('Æ', 4, 2));
        letters.add(new Letter('Ö', 6, 1));
    }

    public ArrayList<Letter> getLetters() {
        return letters;
    }
}
