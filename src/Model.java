import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Model implements Serializable {

    private Stone[][] stones;
    private String nextPlayer = "black";
    private String winner;
    private boolean isDraw  = false;
    private boolean running = false;
    private int UNIT_NUMBER = RenjuGame.DEFAULT_UNIT_NUMBER;
    LinkedList<Coordinate> steps;

    public Model() {
        initialize();
    }

    public void initialize() {
        stones = new Stone[UNIT_NUMBER][UNIT_NUMBER];
        steps = new LinkedList<>();
        for (int i = 0; i < UNIT_NUMBER; i++) {
            for (int j = 0; j < UNIT_NUMBER; j++) {
                stones[i][j] = new Stone();
            }
        }
        setNextPlayer("black");
    }

    public Stone[][] getStones() {
        return stones;
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public boolean isRunning() {
        return running;
    }

    public List<Coordinate> getSteps() {
        return steps;
    }

    public int getUNIT_NUMBER() {
        return UNIT_NUMBER;
    }

    public void setStones(Stone[][] stones) {
        this.stones = stones;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setDraw(boolean isDraw) {
        this.isDraw = isDraw;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setSteps(LinkedList<Coordinate> steps) {
        this.steps = steps;
    }

    public void setUNIT_NUMBER(int UNIT_NUMBER) {
        this.UNIT_NUMBER = UNIT_NUMBER;
    }
}