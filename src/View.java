import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class View extends JPanel{

    private Model model;

    public final int UNIT_SIZE = RenjuGame.DEFAULT_UNIT_SIZE;
    private final int BOARDSIZE;
    private  final int TABLE_WIDTH;
    private final int TABLE_HEIGHT;

    private ImageIcon originalWhiteIcon;
    private Image originalWhiteImage;
    private Image resizedWhiteImage;
    private ImageIcon originalBlackIcon;
    private Image originalBlackImage;
    private Image resizedBlackImage;

    public View(Model model){

        this.model = model;

        BOARDSIZE = (model.getUNIT_NUMBER()+1) * UNIT_SIZE;
        TABLE_WIDTH = (model.getUNIT_NUMBER()-1) * UNIT_SIZE;
        TABLE_HEIGHT = (model.getUNIT_NUMBER()-1) * UNIT_SIZE;

        // panel beállításai
        this.setPreferredSize( new Dimension(BOARDSIZE, BOARDSIZE) );
        this.setFocusable(true);

        // kövek image-einek beállítása
        int imagesize = (int)Math.round(UNIT_SIZE*2.0/3);
        originalWhiteIcon = new ImageIcon("images/whitestone.png");
        originalWhiteImage = originalWhiteIcon.getImage();
        resizedWhiteImage = originalWhiteImage.getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);

        originalBlackIcon = new ImageIcon("images/blackstone.png");
        originalBlackImage = originalBlackIcon.getImage();
        resizedBlackImage = originalBlackImage.getScaledInstance(imagesize, imagesize, Image.SCALE_SMOOTH);

    }

    public void paintGame(Graphics g){

        // tábla és vonalak kirajzolása
        this.setBackground( new Color(61, 41, 15));
        g.setColor(new Color(237,191,104));
        g.fillRect(UNIT_SIZE, UNIT_SIZE, TABLE_WIDTH, TABLE_HEIGHT);

        g.setColor( new Color(0,0,0));
        for(int i = 0; i < model.getUNIT_NUMBER(); i++){
            g.drawLine( UNIT_SIZE, (i+1)*UNIT_SIZE, BOARDSIZE-UNIT_SIZE, (i+1)*UNIT_SIZE );
            g.drawLine( (i+1)*UNIT_SIZE, UNIT_SIZE, (i+1)*UNIT_SIZE, BOARDSIZE-UNIT_SIZE);
        }

        //kövek kirajzolása

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        for(int i = 0; i < model.getUNIT_NUMBER(); i++){
            for(int j = 0; j < model.getUNIT_NUMBER(); j++){
                if( model.getStones()[i][j].getColor().equals("white")){
                    g.setColor( new Color(255,255,255) );
                    g.drawImage(resizedWhiteImage, (int)((j+(2.0/3))*UNIT_SIZE), (int)((i+(2.0/3))*UNIT_SIZE), this);
                }
                if( model.getStones()[i][j].getColor().equals("black")){
                    g.setColor( new Color(0,0,0) );
                    g.drawImage(resizedBlackImage, (int)((j+(2.0/3))*UNIT_SIZE), (int)((i+(2.0/3))*UNIT_SIZE), this);
                }
            }
        }

        if( !model.isRunning() )
            gameOver(g);
    
    }

    public void gameOver(Graphics g) {

        // játék vége kiírása
        g.setColor(Color.RED);
        g.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (BOARDSIZE-metrics.stringWidth("Game Over"))/2, BOARDSIZE/2);
        if( model.isDraw() ){
            g.drawString("It's a draw :((", (BOARDSIZE-metrics.stringWidth("It's a draw :(("))/2, BOARDSIZE/2 + 75);
        }
        else g.drawString("The winner is: " + model.getWinner(), (BOARDSIZE-metrics.stringWidth("The winner is: "))/2, BOARDSIZE/2 + 75);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintGame(g);
    }
    
}