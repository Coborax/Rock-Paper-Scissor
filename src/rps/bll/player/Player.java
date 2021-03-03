package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;

//Java imports
import java.util.*;

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
            return getRandomMove(new ArrayList<Move>(Arrays.asList(Move.values())));
        }

        List<Move> possibleMoves = getPossibleMovesFromResults(results, 2);

        return getOppositeMove(getRandomMove(possibleMoves));
    }

    private Move getRandomMove(List<Move> moves) {
        Random rand = new Random();
        return moves.get(rand.nextInt(moves.size()));
    }

    private Move getOppositeMove(Move move) {
        switch (move) {
            case Rock -> { return Move.Paper; }
            case Paper -> { return Move.Scissor; }
            case Scissor -> { return Move.Rock; }
        }
        throw new NullPointerException();
    }

    private List<Move> getPossibleMovesFromResults(List<Result> results, int amount) {
        ArrayList<Move> resultList = new ArrayList<>();
        HashMap<Move, Integer> playerMoveCounts = new HashMap<>();

        for (Result result : results) {
            Move playerMove;
            //Get the players last move
            if (result.getWinnerPlayer().getPlayerType() == PlayerType.Human) {
                playerMove = result.getWinnerMove();
            } else {
                playerMove = result.getLoserMove();
            }

            if (playerMoveCounts.containsKey(playerMove)) {
                playerMoveCounts.put(playerMove, playerMoveCounts.get(playerMove) + 1);
            } else {
                playerMoveCounts.put(playerMove, 0);
            }
        }

        List<Map.Entry<Move, Integer>> tempList = new ArrayList<>(playerMoveCounts.entrySet());

        Collections.sort(tempList, new Comparator<Map.Entry<Move, Integer>>() {
            @Override
            public int compare(Map.Entry<Move, Integer> o1, Map.Entry<Move, Integer> o2) {
                return (o1.getValue().compareTo(o2.getValue()));
            }
        });

        if (tempList.size() > amount) {
            for (int i = 0; i < tempList.size() - amount; i++) {
                tempList.remove(0);
            }
        }
        
        for (Map.Entry<Move, Integer> move : tempList) {
            resultList.add(move.getKey());
        }


        return resultList;
    }
}
