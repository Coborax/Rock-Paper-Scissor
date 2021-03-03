package rps.gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 * @author smsj
 */
public class GameViewController implements Initializable {

    @FXML
    private ListView historyList;
    @FXML
    private Label playResult;

    private String playerName = "Bob";

    private GameManager gm;
    private IPlayer player;
    private IPlayer ai;
    private ObservableList<String> history = FXCollections.observableArrayList();

    public GameViewController() {
        player = new Player(playerName, PlayerType.Human);
        ai = new Player("SuperBot", PlayerType.AI);
        gm = new GameManager(player, ai);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void playPaper(ActionEvent event) {
        runRound(Move.Paper);
    }

    @FXML
    void playRock(ActionEvent event) {
        runRound(Move.Rock);
    }

    @FXML
    void playScissors(ActionEvent event) {
        runRound(Move.Scissor);
    }

    private void runRound(Move move) {
        gm.playRound(move);

        //Update UI
        ArrayList<Result> results = new ArrayList<>(gm.getGameState().getHistoricResults());

        playResult.setText(getResultAsString(results.get(results.size() - 1)));
        history.add(getResultAsString(results.get(results.size() - 1))); // Adds newest round to history list
        historyList.setItems(history);
        historyList.scrollTo(history.get(history.size()-1)); // Scrolls to newest object
    }

    /**
     * Provides a custom formatted string representation of a Result
     * (Totally not stolen or anything...)
     * @param result
     * @return
     */
    private String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round " + result.getRoundNumber() + ": " +
                result.getWinnerPlayer().getPlayerName() +
                " (" + result.getWinnerMove() + ") " +
                statusText + result.getLoserPlayer().getPlayerName() +
                " (" + result.getLoserMove() + ")!";
    }

}
