package rps.gui.controller;

// Java imports
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import rps.bll.game.GameManager;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;
import rps.bll.player.IPlayer;
import rps.bll.player.Player;
import rps.bll.player.PlayerType;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 *
 * @author smsj
 */
public class GameViewController {
    @FXML
    private Label lblPlayerNames;
    @FXML
    private TextArea txaHistoricResults;
    @FXML
    private Button btnRock, btnPaper, btnScissor, btnExit;

    private String playerName;
    private IPlayer human;
    private IPlayer bot;
    private GameManager ge;

    /**
     * Initializes the controller class.
     */

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        initGame();
    }

    public void initGame()
    {
        human = new Player(playerName, PlayerType.Human);
        bot = new Player(getRandomBotName(), PlayerType.AI);

        lblPlayerNames.setText(playerName + " vs." + bot.getPlayerName());

        ge = new GameManager(human, bot);
    }

    public void handleRock()
    {
        ge.playRound(Move.Rock);
        displayCallback();
    }

    public void handlePaper()
    {
        ge.playRound(Move.Paper);
        displayCallback();
    }

    public void handleScissor()
    {
        ge.playRound(Move.Scissor);
        displayCallback();
    }

    public void handleExit()
    {
        if (ge.getGameState().getHistoricResults().size() > 0)
            txaHistoricResults.setText("Game stats: yeah... in the future...");
        btnRock.setDisable(true);
        btnPaper.setDisable(true);
        btnScissor.setDisable(true);
        btnExit.setDisable(true);
    }

    public void displayCallback()
    {
        txaHistoricResults.setText(ge.getGameState().getHistoricResults()
                .stream()
                .map(result -> getResultAsString(result) + "\n")
                .collect(Collectors.joining()));

        System.out.println(ge.getGameState().getHistoricResults()
                .stream()
                .map(result -> getResultAsString(result) + "\n")
                .collect(Collectors.joining()));
        System.out.println(getGameStatString());
    }

    private String getRandomBotName() {
        String[] botNames = new String[] {
                "R2D2",
                "Mr. Data",
                "3PO",
                "Bender",
                "Marvin the Paranoid Android",
                "Bishop",
                "Robot B-9",
                "HAL"
        };
        int randomNumber = new Random().nextInt(botNames.length - 1);
        return botNames[randomNumber];
    }

    public String getResultAsString(Result result) {
        String statusText = result.getType() == ResultType.Win ? "wins over " : "ties ";

        return "Round #" + result.getRoundNumber() + ":" +
                result.getWinnerPlayer().getPlayerName() +
                " (" + result.getWinnerMove() + ") " +
                statusText + result.getLoserPlayer().getPlayerName() +
                " (" + result.getLoserMove() + ")!";
    }

    public String getGameStatString()
    {
        long ties = ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Tie).count();
        long botWins = ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win && result.getWinnerPlayer() == bot).count();
        long humanWins = ge.getGameState().getHistoricResults().stream().filter(result -> result.getType() == ResultType.Win && result.getWinnerPlayer() == human).count();

        return humanWins+"/"+ties+"/"+botWins;
    }
}
