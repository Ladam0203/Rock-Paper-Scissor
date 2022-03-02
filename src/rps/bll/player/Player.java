package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;

//Java imports
import java.util.ArrayList;
import java.util.List;

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
    private double repeatLast = 0;
    private double counterLast = 0;
    private double surrenderLast = 0;
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        int depth = 5;
        List<Result> recent = results.subList(Math.max(results.size() - depth, 0), results.size());

        if (recent.size() >= 1)
        {
            for (int i = recent.size()-1; i > 1; i--) {
                Result earlier = recent.get(recent.size()-1);
                Result latter = recent.get(recent.size()-2);
                if (latter.getLoserPlayer() == this) {
                    if (earlier.getWinnerMove() == latter.getWinnerMove() && latter.getType() != ResultType.Tie) {
                        counterLast = (counterLast+1)/2;
                        repeatLast = (counterLast+0)/2;
                        surrenderLast = (counterLast+0)/2;
                    }
                    else if (earlier.getWinnerMove() == Move.Rock && latter.getWinnerMove() == Move.Scissor ||
                            earlier.getWinnerMove() == Move.Scissor && latter.getWinnerMove() == Move.Paper ||
                            earlier.getWinnerMove() == Move.Paper && latter.getWinnerMove() == Move.Rock)
                    {
                        counterLast = (counterLast+0)/2;
                        repeatLast = (counterLast+1)/2;
                        surrenderLast = (counterLast+0)/2;
                    }
                    else {
                        counterLast = (counterLast+0)/2;
                        repeatLast = (counterLast+0)/2;
                        surrenderLast = (counterLast+1)/2;
                    }
                }
            }
        }
        else
        {
            return Move.Scissor;
        }
        System.out.println(repeatLast + " " + counterLast + " " + surrenderLast);

        Move repeatMove = recent.get(recent.size()-1).getWinnerMove();

        Move counterMove = Move.Rock;
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Rock)
        {
            counterMove = Move.Paper;
        }
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Paper)
        {
            counterMove = Move.Scissor;
        }
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Scissor)
        {
            counterMove = Move.Rock;
        }

        Move surrenderMove = Move.Rock;
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Rock)
        {
            surrenderMove = Move.Scissor;
        }
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Paper)
        {
            surrenderMove = Move.Rock;
        }
        if (recent.get(recent.size()-1).getWinnerMove() == Move.Scissor)
        {
            surrenderMove = Move.Paper;
        }

        return repeatLast > counterLast && repeatLast > surrenderLast ? repeatMove : counterLast > surrenderLast ? counterMove : surrenderMove ;
    }

    //HELPER CLASSES

    public Move getBotMoveFromResult(Result result)
    {
        if (result.getType() == ResultType.Tie)
        {
            return result.getLoserMove();
        }
        else if (result.getWinnerPlayer() == this)
        {
            return result.getWinnerMove();
        }
        else
        {
            return result.getLoserMove();
        }
    }

    public Move getHumanMoveFromResult(Result result)
    {
        if (result.getType() == ResultType.Tie)
        {
            return result.getWinnerMove();
        }
        else if (result.getWinnerPlayer() == this)
        {
            return result.getLoserMove();
        }
        else
        {
            return result.getWinnerMove();
        }
    }
}
