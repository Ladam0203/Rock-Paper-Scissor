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
        int depth = 6;
        List<Result> recent = results.subList(Math.max(results.size() - depth, 0), results.size());

        float guess = 0;
        float repeatCenter = 1f/6f;
        float counterCenter = 3f/6f;
        float surrenderCenter = 5f/6f;

        for (int i = 1; i < recent.size(); ++i) {
            if (isRepeatOf(recent.get(i-1).getWinnerMove(), recent.get(i).getWinnerMove()))
            {
                guess = (guess+repeatCenter)/2;
            }
            if (isCounterOf(recent.get(i-1).getWinnerMove(), recent.get(i).getWinnerMove()))
            {
                guess = (guess+counterCenter)/2;
            }
            if (isSurrenderOf(recent.get(i-1).getWinnerMove(), recent.get(i).getWinnerMove()))
            {
                guess = (guess+surrenderCenter)/2;
            }
        }

        if (recent.size() >= 1)
        {
            Move lastWinnerMove = recent.get(recent.size()-1).getWinnerMove();
            if (guess <= repeatCenter)
            {
                return lastWinnerMove;
            }
            else if (guess <= counterCenter)
            {
                if (lastWinnerMove == Move.Rock)
                {
                    return Move.Paper;
                }
                if (lastWinnerMove == Move.Paper)
                {
                    return Move.Scissor;
                }
                if (lastWinnerMove == Move.Scissor)
                {
                    return Move.Rock;
                }
            }
            else
            {
                if (lastWinnerMove == Move.Rock)
                {
                    return Move.Scissor;
                }
                if (lastWinnerMove == Move.Paper)
                {
                    return Move.Rock;
                }
                if (lastWinnerMove == Move.Scissor)
                {
                    return Move.Paper;
                }
            }
        }

        return Move.Paper;
    }

    //HELPER CLASSES

    private boolean isRepeatOf(Move first, Move second)
    {
        return first == second;
    }
    
    private boolean isCounterOf(Move first, Move second)
    {
        if ((first == Move.Rock && second == Move.Paper) ||
            (first == Move.Scissor && second == Move.Rock) ||
            (first == Move.Paper && second == Move.Scissor))
        {
            return true;
        }
        return false;
    } 
    
    private boolean isSurrenderOf(Move first, Move second)
    {
        if ((first == Move.Rock && second == Move.Scissor) ||
                (first == Move.Scissor && second == Move.Paper) ||
                (first == Move.Paper && second == Move.Rock))
        {
            return true;
        }
        return false;
    }
}
