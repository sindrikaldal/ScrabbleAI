package Game;

import WordCollection.Letter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sindrikaldal on 24/03/16.
 */
public class Bag {

    private List<Letter> bag;

    public Bag(ArrayList<Letter> letters) {
        bag = new ArrayList<Letter>();
        for(Letter l : letters) {
            for(int i = 0; i < l.getInstances(); i++) {
                bag.add(l);
            }
        }
    }

    public List<Letter> getBag() {
        return bag;
    }

    public void setBag(List<Letter> bag) {
        this.bag = bag;
    }
}
