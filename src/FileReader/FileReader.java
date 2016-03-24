package FileReader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public class FileReader {

    private List<String> lines = new ArrayList<String>();

    public FileReader() {
    }

    public void storeWordCollection() {
        try {
                lines = Files.readAllLines(Paths.get("/Users/eysteinn/Desktop/AI/finalProject/ScrabbleAI/ordmyndalisti.txt"), Charset.defaultCharset());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}
