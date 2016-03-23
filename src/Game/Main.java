package Game;

import Board.Board;
import GUI.ScrabbleGUI;
import org.quinto.dawg.ModifiableDAWGSet;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class Main {

    private static ModifiableDAWGSet dawg = new ModifiableDAWGSet();
    static List<String> lines = new ArrayList<String>();
    private static ScrabbleGUI gui;


    public static void main(String[] args) {
        System.out.println("Reading word collection");
        storeWordCollection();
        System.out.println("Done reading word collection");
        dawg.addAll(lines);

        System.out.println("Check if dawg list contains the word hestur");

        if(dawg.contains("hestur")){
            System.out.println("It does!");
        }


//        gui = new ScrabbleGUI(new Board());
    }


    public void storeWordCollection() {
        try {
            try {
                lines = Files.readAllLines(
                        Paths.get(this.getClass().getResource("ordmyndalisti.txt").toURI()), Charset.defaultCharset());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


