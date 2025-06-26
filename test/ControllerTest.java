import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControllerTest {

    private Model model;
    private View view;
    private Controller controller;

    @Before
    public void setup() {
        model = new Model();
        view = new View( model );
        controller = new Controller(model, view);
    }

    @Test
    public void startGame() {
        assertFalse(controller.isRunning());
        controller.startGame();
        assertTrue(controller.isRunning());
    }

    @Test
    public void checkHorizontalWin() {

        for (int i = 0; i < 5; i++) {
            model.getStones()[0][i].setColor("white");
        }

        boolean result = controller.checkIfCanContinue();
        assertFalse(result);
    }

    @Test
    public void checkVerticalWin() {

        for (int i = 0; i < 5; i++) {
            model.getStones()[i][0].setColor("black");
        }

        boolean result = controller.checkIfCanContinue();
        assertFalse(result);
    }

    @Test
    public void checkDiagonalWin() {

        for (int i = 0; i < 5; i++) {
            model.getStones()[i][i].setColor("white");
        }

        boolean result = controller.checkIfCanContinue();
        assertFalse(result);
    }

    @Test
    public void isRunning() {
        boolean result = controller.isRunning();
        assertFalse(result);
    }

    @Test
    public void stepBack() {

        model.getStones()[2][2].setColor("black");
        model.getSteps().add( new Coordinate(2,2) );
        controller.stepBack();

        assertEquals("blank", model.getStones()[2][2].getColor());
    }

    @Test
    public void blackCanPlaceHere() {

        model.getStones()[2][2].setColor("black");
        model.getStones()[2][3].setColor("black");
        model.getStones()[3][4].setColor("black");
        model.getStones()[4][4].setColor("black");

        boolean placevalue = controller.blackCanPlaceHere(2,4);
        assertFalse( placevalue );
    }
}