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
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        int learnSize = 6;
        List<Result> recent = results.subList(Math.max(results.size() - learnSize, 0), results.size());
        int repeatLast = 0;
        int counterLast = 0;
        int surrenderLast = 0;
        for (int i = 1; i < recent.size(); i++) {
            if (recent.get(i-1).getWinnerMove() == recent.get(i).getWinnerMove())
                repeatLast++;
            else if ((recent.get(i-1).getWinnerMove() == Move.Rock && recent.get(i).getWinnerMove() == Move.Scissor) ||
                    (recent.get(i-1).getWinnerMove() == Move.Scissor && recent.get(i).getWinnerMove() == Move.Paper) ||
                    (recent.get(i-1).getWinnerMove() == Move.Paper && recent.get(i).getWinnerMove() == Move.Rock)) {
                counterLast++;
            } else {
                surrenderLast++;
            }
        }
        System.out.println(repeatLast + " " + counterLast + " " + surrenderLast);

        if (recent.size() < 1)
        {
            return Move.Scissor;
        }
        else
        {
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

            return repeatLast > counterLast ? repeatMove : counterLast > surrenderLast ? counterMove : surrenderMove;
        }
    }
}
