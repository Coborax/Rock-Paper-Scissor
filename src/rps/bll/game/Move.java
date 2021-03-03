package rps.bll.game;

import java.util.Collections;
import java.util.List;

/**
 * The various move options in the game
 *
 * @author smsj
 */
public enum Move {
    Rock,
    Paper,
    Scissor;

    public List<Move> loses;

    public boolean loses(Move other) {
        return loses.contains(other);
    }

    static {
        Rock.loses = Collections.singletonList(Paper);
        Paper.loses = Collections.singletonList(Scissor);
        Scissor.loses = Collections.singletonList(Rock);
    }
}



