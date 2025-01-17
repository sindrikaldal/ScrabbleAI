package FileReader;

import java.io.IOException;
import java.net.URI;
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

    private List<String> lines;

    public FileReader() {
        lines = new ArrayList<String>();
    }

    public void storeWordCollection() {
        String fileName = "ordmyndalisti.txt";
        URI uri = null;
        try {
            uri = this.getClass().getResource(fileName).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
                lines = Files.readAllLines(Paths.get(uri), Charset.forName("UTF-8"));

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
