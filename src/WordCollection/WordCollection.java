package WordCollection;

import FileReader.FileReader;
import org.quinto.dawg.ModifiableDAWGSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class WordCollection {

    private FileReader fileReader;
    private HashSet<Letter> letters;

    private static ModifiableDAWGSet dawg;

    public WordCollection() {

        fileReader = new FileReader();
        dawg = new ModifiableDAWGSet();
        letters = new HashSet<Letter>();

        importWordCollection();
        initalizeLetters();

    }

    private void importWordCollection() {
        fileReader.storeWordCollection();
        dawg.addAll(fileReader.getLines());
    }

    private void initalizeLetters() {

        letters.add(new Letter('A', 1));
        letters.add(new Letter('Á', 3));
        letters.add(new Letter('B', 5));
        letters.add(new Letter('D', 5));
        letters.add(new Letter('Ð', 2));
        letters.add(new Letter('E', 3));
        letters.add(new Letter('É', 7));
        letters.add(new Letter('F', 3));
        letters.add(new Letter('G', 3));
        letters.add(new Letter('H', 4));
        letters.add(new Letter('I', 1));
        letters.add(new Letter('Í', 4));
        letters.add(new Letter('J', 6));
        letters.add(new Letter('K', 2));
        letters.add(new Letter('L', 2));
        letters.add(new Letter('M', 2));
        letters.add(new Letter('N', 1));
        letters.add(new Letter('O', 5));
        letters.add(new Letter('Ó', 3));
        letters.add(new Letter('P', 5));
        letters.add(new Letter('R', 1));
        letters.add(new Letter('S', 1));
        letters.add(new Letter('T', 2));
        letters.add(new Letter('U', 2));
        letters.add(new Letter('Ú', 4));
        letters.add(new Letter('V', 5));
        letters.add(new Letter('X', 10));
        letters.add(new Letter('Y', 6));
        letters.add(new Letter('Ý', 5));
        letters.add(new Letter('Þ', 7));
        letters.add(new Letter('Æ', 4));
        letters.add(new Letter('Ö', 6));
    }
}
