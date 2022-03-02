package rps.bll.player;

//Project imports
import rps.bll.game.IGameState;
import rps.bll.game.Move;
import rps.bll.game.Result;
import rps.bll.game.ResultType;

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

    private int[][] matrix = new int[3][3];
    private Random rnd = new Random();
    @Override
    public Move doMove(IGameState state) {
        //Historic data to analyze and decide next move...
        ArrayList<Result> results = (ArrayList<Result>) state.getHistoricResults();

        if (results.size() < 2)
        {
            return getRandomMove();
        }
        else
        {
            Move earlier = getHumanMove(results.get(results.size()-1));
            Move latter = getHumanMove(results.get(results.size()-2));

            matrix[moveToInt(earlier)][moveToInt(latter)] += 1;

            int max = 0;
            int nextIndex = 0;
            for (int i = 0; i < 3; i++) {
                if (matrix[moveToInt(latter)][i] > max)
                {
                    max = matrix[moveToInt(latter)][i];
                    nextIndex = i;
                }
            }

            return losesTo(intToMove(nextIndex));
        }
    }

    //HELPER CLASSES

    private Move losesTo(Move faces)
    {
        if (faces == Move.Rock)
        {
            return Move.Paper;
        }
        else if (faces == Move.Paper)
        {
            return Move.Scissor;
        }
        else
        {
            return Move.Rock;
        }
    }

    private Move getRandomMove() {
        return intToMove(rnd.nextInt(0,3));
    }

    private Move getHumanMove(Result result)
    {
        if (result.getWinnerPlayer() != this)
        {
            return result.getWinnerMove();
        }
        else
        {
            return result.getLoserMove();
        }
    }

    private int moveToInt(Move move)
    {
        if (move == Move.Rock) {
            return 0;
        }
        else if (move == Move.Paper) {
            return 1;
        }
        else {
            return 2;
        }
    }

    private Move intToMove(int number)
    {
        if (number == 0) {
            return Move.Rock;
        }
        else if (number == 1) {
            return Move.Paper;
        }
        else {
            return Move.Scissor;
        }
    }
}
