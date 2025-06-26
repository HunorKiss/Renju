import java.awt.event.*;
import javax.swing.*;


public class Controller extends JPanel implements MouseListener {

    private Model model;
    private View view;

    Controller(Model model, View view) {

        this.model = model;
        this.view = view;

        view.addMouseListener(this);
    
    }

    public void startGame(){
        model.setRunning(true);
        view.repaint();
    }

    public boolean checkIfCanContinue(){

        // végigmegyünk az első 11 oszlop elemein, és megnézzük hogy van-e vízszintesen 5 azonos színű kő
        for(int i = 0; i < model.getUNIT_NUMBER(); i++){
            for(int j = 0; j < (model.getUNIT_NUMBER()-4); j++){

                int sumofthesamecolor = 0;
                for(int counter = 0; counter < 5; counter++){
                    if( model.getStones()[i][j+counter].getColor().equals("white") )
                        sumofthesamecolor++;
                    else if( model.getStones()[i][j+counter].getColor().equals("black") )
                        sumofthesamecolor--;
                }
                if( sumofthesamecolor == -5 || sumofthesamecolor == 5 ) 
                    return false;

            }
        }

        // végigmegyünk az első 11 soron és megnézzük hogy függőlegesen van-e 5 azonos színű kő
        for(int i = 0; i < (model.getUNIT_NUMBER()-4); i++){
            for(int j = 0; j < model.getUNIT_NUMBER(); j++){

                int sumofthesamecolor = 0;
                for(int counter = 0; counter < 5; counter++){
                    if( model.getStones()[i+counter][j].getColor().equals("white") )
                        sumofthesamecolor++;
                    else if( model.getStones()[i+counter][j].getColor().equals("black") )
                        sumofthesamecolor--;
                }
                if( sumofthesamecolor == -5 || sumofthesamecolor == 5 ) 
                    return false;

            }
        }

        // ellenőrizzük hogy bal-fent jobb-lent irányban van-e átlósan 5 azonos színű kő
        for(int i = 0; i < (model.getUNIT_NUMBER()-4); i++){
            for(int j = 0; j < (model.getUNIT_NUMBER()-4); j++){

                int sumofthesamecolor = 0;
                for(int counter = 0; counter < 5; counter++){
                    if( model.getStones()[i+counter][j+counter].getColor().equals("white") )
                        sumofthesamecolor++;
                    else if( model.getStones()[i+counter][j+counter].getColor().equals("black") )
                        sumofthesamecolor--;
                }
                if( sumofthesamecolor == -5 || sumofthesamecolor == 5 ) 
                    return false;

            }
        }

        // ellenőrizzük hogy jobb-fent bal-lent irányban van-e átlósan 5 azonos színű kő
        for(int i = 0; i < (model.getUNIT_NUMBER()-4); i++){
            for(int j = 4; j < model.getUNIT_NUMBER(); j++){

                int sumofthesamecolor = 0;
                for(int counter = 0; counter < 5; counter++){
                    if( model.getStones()[i+counter][j-counter].getColor().equals("white") )
                        sumofthesamecolor++;
                    else if( model.getStones()[i+counter][j-counter].getColor().equals("black") )
                        sumofthesamecolor--;
                }
                if( sumofthesamecolor == -5 || sumofthesamecolor == 5 ) 
                    return false;

            }
        }

        int blanknum = 0;
        for(int i = 0; i < model.getUNIT_NUMBER(); i++){
            for(int j = 0; j < model.getUNIT_NUMBER(); j++){

                if( model.getStones()[i][j].getColor().equals("blank") )
                    blanknum++;

            }
        }
        if( blanknum == 0 ){
            model.setDraw(true);
            return false;
        }

        return true;

    }

    public boolean isRunning(){
        return model.isRunning();
    }

    public void stepBack(){

        if ( !model.getSteps().isEmpty() ){
            Coordinate step = model.getSteps().remove( model.getSteps().size() - 1 );
            model.getStones()[step.getY()][step.getX()].setColor("blank");
            if( model.getNextPlayer().equals("white"))
                model.setNextPlayer("black");
            else model.setNextPlayer("white");
        }
        view.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if ( model.isRunning() ) {

            int x = e.getX();
            int y = e.getY();

            // A legközelebbi hely kiszámítása, ahová követ lehet helyezni
            int boardX = Math.round((float) (x - view.UNIT_SIZE) / view.UNIT_SIZE);
            int boardY = Math.round((float) (y - view.UNIT_SIZE) / view.UNIT_SIZE);

            // koordináták ellenőrzése
            if (boardX >= 0 && boardX < model.getUNIT_NUMBER() && boardY >= 0 && boardY < model.getUNIT_NUMBER()) {
        
                // megnézzük hogy üres-e a hely és tehetünk-e ide követ
                if ( model.getStones()[boardY][boardX].getColor().equals("blank") &&
                     ( model.getNextPlayer().equals("white") || ( model.getNextPlayer().equals("black") && blackCanPlaceHere(boardY, boardX) ) ) ) {
                    
                    model.getStones()[boardY][boardX].setColor(model.getNextPlayer());
                    model.getSteps().add( new Coordinate(boardX, boardY) );
                    view.repaint();

                    model.setRunning( checkIfCanContinue() );
                    if( model.isRunning() )
                        model.setNextPlayer( model.getNextPlayer().equals("black") ? "white" : "black" );
                    else if( !model.isDraw() ) model.setWinner( model.getNextPlayer() );
    
                }
            }

        } else view.repaint();

    }

    boolean blackCanPlaceHere(int boardY, int boardX) {
        
        // ha a vizsgált hely középen van
        if( boardX >=2 && boardX <= (model.getUNIT_NUMBER()-3) && boardY >=2 && boardY <= (model.getUNIT_NUMBER()-3) ){

            // a vizsgált hely feletti és bal oldalra lévő két kő fekete
            if( checkTopLeft( boardY, boardX ) )
                return false;
            // a vizsgált hely feletti és jobb oldalra lévő két kő fekete
            if( checkTopRight( boardY, boardX ) )
                return false;
            // a vizsgált hely alatti és bal oldalra lévő két kő fekete
            if( checkBottomLeft( boardY, boardX ) )
                return false;
            // a vizsgált hely alatti és jobb oldalra lévő két kő fekete
            if( checkBottomRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha a baloldali sávban van
        else if( boardX < 2 && boardY >=2 && boardY <= (model.getUNIT_NUMBER()-3) ){

            // a vizsgált hely feletti és jobb oldalra lévő két kő fekete
            if( checkTopRight( boardY, boardX ) )
                return false;
            // a vizsgált hely alatti és jobb oldalra lévő két kő fekete
            if( checkBottomRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha a jobboldali sávban van
        else if( boardX > (model.getUNIT_NUMBER()-3) && boardY >=2 && boardY <= (model.getUNIT_NUMBER()-3) ){

            // a vizsgált hely feletti és bal oldalra lévő két kő fekete
            if( checkTopLeft( boardY, boardX ) )
                return false;
            // a vizsgált hely alatti és bal oldalra lévő két kő fekete
            if( checkBottomLeft( boardY, boardX ) )
                return false;
            return true;

        }
        // ha a felső sávban van
        else if( boardX >= 2 && boardX <= (model.getUNIT_NUMBER()-3) && boardY < 2 ){

            // a vizsgált hely alatti és bal oldalra lévő két kő fekete
            if( checkBottomLeft( boardY, boardX ) )
                return false;
            // a vizsgált hely alatti és jobb oldalra lévő két kő fekete
            if( checkBottomRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha az alsó sávban van
        else if( boardX >= 2 && boardX <= (model.getUNIT_NUMBER()-3) && boardY > (model.getUNIT_NUMBER()-3) ){

            // a vizsgált hely feletti és bal oldalra lévő két kő fekete
            if( checkTopLeft( boardY, boardX ) )
                return false;
            // a vizsgált hely feletti és jobb oldalra lévő két kő fekete
            if( checkTopRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha bal felül van
        else if ( boardX < 2 && boardY < 2 ){

            // a vizsgált hely alatti és jobb oldalra lévő két kő fekete
            if( checkBottomRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha jobb felül van
        else if ( boardX > (model.getUNIT_NUMBER()-3) && boardY < 2 ){

            // a vizsgált hely alatti és bal oldalra lévő két kő fekete
            if( checkBottomLeft( boardY, boardX ) )
                return false;
            return true;

        }
        // ha bal alul van
        else if ( boardX < 2 && boardY > (model.getUNIT_NUMBER()-3) ){

            // a vizsgált hely feletti és jobb oldalra lévő két kő fekete
            if( checkTopRight( boardY, boardX ) )
                return false;
            return true;

        }
        // ha jobb alul van
        else{

            // a vizsgált hely feletti és bal oldalra lévő két kő fekete
            if( checkTopLeft( boardY, boardX ) )
                return false;
            else return true;
            
        }
    }

    private boolean checkTopLeft( int boardY, int boardX ){
        // a vizsgált hely feletti és bal oldalra lévő két kő fekete
        return ( model.getStones()[boardY-1][boardX].getColor().equals("black") && model.getStones()[boardY-2][boardX].getColor().equals("black") &&
                model.getStones()[boardY][boardX-1].getColor().equals("black") && model.getStones()[boardY][boardX-2].getColor().equals("black") );

    }

    private boolean checkTopRight( int boardY, int boardX ){
        // a vizsgált hely feletti és jobb oldalra lévő két kő fekete
        return ( model.getStones()[boardY-1][boardX].getColor().equals("black") && model.getStones()[boardY-2][boardX].getColor().equals("black") &&
                model.getStones()[boardY][boardX+1].getColor().equals("black") && model.getStones()[boardY][boardX+2].getColor().equals("black") );
    }

    private boolean checkBottomLeft( int boardY, int boardX ){
        // a vizsgált hely alatti és bal oldalra lévő két kő fekete
        return ( model.getStones()[boardY+1][boardX].getColor().equals("black") && model.getStones()[boardY+2][boardX].getColor().equals("black") &&
                model.getStones()[boardY][boardX-1].getColor().equals("black") && model.getStones()[boardY][boardX-2].getColor().equals("black") );
    }

    private boolean checkBottomRight( int boardY, int boardX ){
        // a vizsgált hely alatti és jobb oldalra lévő két kő fekete
        return ( model.getStones()[boardY+1][boardX].getColor().equals("black") && model.getStones()[boardY+2][boardX].getColor().equals("black") &&
                model.getStones()[boardY][boardX+1].getColor().equals("black") && model.getStones()[boardY][boardX+2].getColor().equals("black") );
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    
}
