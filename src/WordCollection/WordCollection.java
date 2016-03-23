package WordCollection;

import FileReader.FileReader;
import org.quinto.dawg.ModifiableDAWGSet;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class WordCollection {

    private FileReader fileReader;

    private static ModifiableDAWGSet dawg;

    public WordCollection() {

        fileReader = new FileReader();

        dawg = new ModifiableDAWGSet();

        System.out.println("Reading word collection");
        fileReader.storeWordCollection();
        System.out.println("Done reading word collection");
        dawg.addAll(fileReader.getLines());

        System.out.println("Check if dawg list contains the word hestur");

        if(dawg.contains("hestur")){
            System.out.println("It does!");
        }
    }
}
