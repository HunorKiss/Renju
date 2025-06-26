import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel{
    
    private BufferedImage backgroundImage;
    private JPanel tableHolderPanel;
    private JLabel gameButtonPanel;
    public static MyButton mainMenuButtonGame = new MyButton("Return to main menu");
    private JLabel white;
    private JLabel black;

    public static MyButton stepBackButton = new MyButton("Step back");
    public static MyButton saveGameButton = new MyButton("Save Game");
    public static MyButton restartGameButton = new MyButton("Restart");

    private Controller controller;
    private Model model;
    private View view;

    public GamePanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        // set background
        try {
            File im = new File("images/background.jpg");
            backgroundImage = ImageIO.read(im);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set the layout for GamePanel
        setLayout( new GridLayout(1,2));

        setButtons();

        //  left side
        tableHolderPanel = new JPanel();
        tableHolderPanel.setPreferredSize( new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT) );
        tableHolderPanel.setBackground( new Color(0, 0, 0, 0) );
        
        
        // right side
        gameButtonPanel = new JLabel();
        gameButtonPanel.setPreferredSize( new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT) );
        gameButtonPanel.setBackground( new Color(0, 0, 0, 0) );
        gameButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(20, 20, 20, 20);
        gbc2.gridx = 1;
        gameButtonPanel.add(stepBackButton, gbc2);
        gameButtonPanel.add(saveGameButton, gbc2);
        gameButtonPanel.add(restartGameButton, gbc2);
        gameButtonPanel.add(mainMenuButtonGame, gbc2);
    }

    public void setButtons(){

        stepBackButton.addActionListener((ActionListener)new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e){
                stepBack();
            }
        });

        restartGameButton.addActionListener((ActionListener)new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                restart();
            }
        });

        saveGameButton.addActionListener((ActionListener)new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {

                if ( model.isRunning() ){

                    JFrame frame = new JFrame("Save Game");
                    frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
                    frame.setSize(500, 200);

                    JPanel inputPanel = new JPanel();
                    inputPanel.setOpaque(false);
                    inputPanel.setLayout(new GridLayout(1, 2));

                    JLabel textLabel = new JLabel("Give a name for the saved file: ");
                    JTextField NameField = new JTextField();
                    inputPanel.add(textLabel);
                    inputPanel.add(NameField);

                    JButton submitButton = new JButton("Submit");
                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try{

                                String fileName = NameField.getText();
                                FileOutputStream fos = new FileOutputStream( new File( "games", fileName + ".dat" ));
                                ObjectOutputStream oos = new ObjectOutputStream(fos);

                                oos.writeObject(model);

                                oos.close();
                                fos.close();
                                frame.dispose();

                            } catch (IOException i) {
                                i.printStackTrace();
                            } 
                    }});

                    frame.add(inputPanel, BorderLayout.CENTER);
                    frame.add(submitButton, BorderLayout.SOUTH);

                    frame.setVisible(true);

                } else JOptionPane.showMessageDialog(null, "The game has ended!");
            }
        });

        /**GAME board -> mainmenu */
        mainMenuButtonGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                RenjuGame.layout.show( RenjuGame.mainPanel, "MENU" );
            }
        });

    }

    public void startGame(Model inputModel){

        tableHolderPanel.removeAll();

        // tableholdert frissítjük
        tableHolderPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        setComponents(inputModel);

        tableHolderPanel.add(view);

        this.add(tableHolderPanel);
        this.add(gameButtonPanel);

        controller.startGame();

    }

    private void setComponents(Model inputModel) {

        if ( inputModel != null ) {
            model = inputModel;
        } else model = new Model();

        view = new View(model);
        controller = new Controller(model, view);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void restart() {
        if (controller != null) {
            model.initialize();
            controller.startGame();
        }
    }

    public void stepBack() {
        if ( controller.isRunning() )
            controller.stepBack();
    }

}
