package rps.bll.player;

//Project imports
import rps.bll.game.GameManager;
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Example implementation of a player.
 *
 * @author smsj
 */
public class Player implements IPlayer {

    private String name;
    private PlayerType type;

    private Random rand = new Random();
    private int[][] chain;
    private Move latest = null;
    private int round;

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


    private void updateChain(Move prev, Move next) {
        chain[prev.ordinal()][next.ordinal()]++;
    }

    private Move nextMove(Move prev) {

        if (round == 1) {
            int len = Move.values().length;
            chain = new int[len][len];

            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len; j++) {
                    chain[i][j] = 0;
                }
            }

            return Move.values()[rand.nextInt(Move.values().length)];
        }

        int nextIndex = 0;

        for (int i = 0; i < Move.values().length; i++) {
            int prevIndex = prev.ordinal();

            if (chain[prevIndex][i] > chain[prevIndex][nextIndex]) {
                nextIndex = i;
                System.out.println(nextIndex);
            }
        }

        Move predNext = Move.values()[nextIndex];

        List<Move> loses = predNext.loses;

        return loses.get(rand.nextInt(loses.size()));
    }

    /**
     * Decides the next move for the bot...
     * @param state Contains the current game state including historic moves/results
     * @return Next move
     */
    @Override
    public Move doMove(IGameState state) {
        round = state.getRoundNumber();
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        Move move = nextMove(latest);

        if (latest != null) {
            updateChain(latest, GameManager.human_move);
        }

        latest = GameManager.human_move;

        return move;
    }
}
