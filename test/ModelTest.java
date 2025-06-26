import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ModelTest {
    private Model model;

    @Before
    public void setup() {
        model = new Model();
    }

    @Test
    public void stepsTest() {
        model.getSteps().add( new Coordinate(0,0));
        model.getSteps().add( new Coordinate(1,1));

        Coordinate first = new Coordinate(0,0);
        Coordinate second = new Coordinate(1,1);

        assertTrue( first.equals(model.getSteps().get(0)) );
        assertTrue( second.equals(model.getSteps().get(1)) );
    }

    @Test
    public void initialize() {
        assertTrue( model.getNextPlayer().equals("black") );
        assertTrue( model.getStones()[3][4].getColor().equals("blank") );
    }

    @Test
    public void winner() {
        assertTrue(model.getWinner() == null);
    }

}
