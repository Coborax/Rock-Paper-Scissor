package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.Random;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    /**
     * @param name
     */
    public Player(String name, PlayerType type) {
        this.name = name;
        this.type = type;
    }


    @Override
    public String getPlayerName() {
        return name;
    }


    @Override
    public PlayerType getPlayerType() {
        return type;
    }


    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        //Get a random move if there are no results yet
        if (results.isEmpty()) {
            return getRandomMove(Move.values());
        }

        Result lastResult = results.get(results.size() - 1);
        Move playerLastMove;

        //Get the players last move
        if (lastResult.getWinnerPlayer().getPlayerType() == PlayerType.Human) {
            playerLastMove = lastResult.getWinnerMove();
        } else {
            playerLastMove = lastResult.getLoserMove();
        }

        // Decide the new move, based on the players last move. (Will pick a random of the two)
        // +=============+=================================+=================================+
        // | Player Move | Player possible move (Bot Move) | Player possible move (Bot Move) |
        // +=============+=================================+=================================+
        // | Rock        | Scissor (Rock)                  | Paper (Scissor)                 |
        // +-------------+---------------------------------+---------------------------------+
        // | Paper       | Rock (Paper)                    | Scissor (Rock)                  |
        // +-------------+---------------------------------+---------------------------------+
        // | Scissor     | Paper (Scissor)                 | Rock (Paper)                    |
        // +-------------+---------------------------------+---------------------------------+
        Move move = playerLastMove;
        switch (playerLastMove) {
            case Rock -> move = getRandomMove(Move.Rock, Move.Scissor);
            case Paper -> move = getRandomMove(Move.Paper, Move.Rock);
            case Scissor -> move = getRandomMove(Move.Scissor, Move.Paper);
        }

        return move;
    }

    private Move getRandomMove(Move... moves) {
        Random rand = new Random();
        return moves[rand.nextInt(moves.length)];
    }
}
