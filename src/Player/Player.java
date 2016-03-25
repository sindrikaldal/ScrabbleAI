package Player;

import Game.Bag;
import Move.Move;
import WordCollection.Letter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sindrikaldal on 23/03/16.
 */
public abstract interface Player {

    public Move makeMove ();
    public void fillRack(Bag bag);
    public List<Letter> getRack();
}
