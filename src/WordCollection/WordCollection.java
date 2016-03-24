package WordCollection;

import FileReader.FileReader;
import org.quinto.dawg.ModifiableDAWGSet;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("Building DAWG from string list");
        dawg.addAll(fileReader.getLines());
        System.out.println("Done building DAWG");
        System.out.println("DAWG size " + dawg.size());
        System.out.println("DAWG node count " + dawg.getNodeCount());

        Iterable<String> testSet = dawg.getStringsWithSubstring("hot"); //{"str1"}

        for(String s: testSet){
            System.out.println(s);
        }
    }
}
