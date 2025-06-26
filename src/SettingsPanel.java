import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class SettingsPanel extends JPanel {
    
    private BufferedImage backgroundImage;
    static private MyButton setUnitSizeButton = new MyButton("Set the size of a unit");
    static private MyButton setUnitNumberButton = new MyButton("Set the number of units");
    public static MyButton mainMenuButtonSettings = new MyButton("Return to main menu");

    public SettingsPanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        // set background
        try {
             backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        setButtons();

        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Center buttons horizontally and vertically
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        this.add(setUnitSizeButton, gbc);

        gbc.gridy++;
        this.add(setUnitNumberButton, gbc);

        gbc.gridy++;
        this.add(mainMenuButtonSettings, gbc);

    }

    public void setButtons() {

        setUnitSizeButton.addActionListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                JFrame frame = new JFrame("Settings");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 200);

                JPanel inputPanel = new JPanel();
                inputPanel.setOpaque(false);
                inputPanel.setLayout(new GridLayout(1, 2));

                JLabel blockSizeLabel = new JLabel("Select block size? (between 20 and 35):");
                JTextField blockSizeField = new JTextField();
                inputPanel.add(blockSizeLabel);
                inputPanel.add(blockSizeField);

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{

                            int blockSize = Integer.parseInt(blockSizeField.getText());
                            if( blockSize > 35)
                                blockSize = 35;
                            if( blockSize < 20 )
                                blockSize = 20;

                            RenjuGame.DEFAULT_UNIT_SIZE = blockSize;

                            frame.dispose();

                         } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
                        }
                    }
                });

                frame.add(inputPanel, BorderLayout.CENTER);
                frame.add(submitButton, BorderLayout.SOUTH);

                frame.setVisible(true);

            }
        });

        setUnitNumberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                
                JFrame frame = new JFrame("Settings");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 200);

                JPanel inputPanel = new JPanel();
                inputPanel.setOpaque(false);
                inputPanel.setLayout(new GridLayout(1, 2));

                JLabel blockSizeLabel = new JLabel("Select block number: (between 5 and 20):");
                JTextField blockSizeField = new JTextField();
                inputPanel.add(blockSizeLabel);
                inputPanel.add(blockSizeField);

                JButton submitButton = new JButton("Submit");
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try{

                            int blockNumber = Integer.parseInt(blockSizeField.getText());
                            if ( blockNumber > 20 )
                                blockNumber = 20;
                            if ( blockNumber < 5)
                                blockNumber = 5;

                            RenjuGame.DEFAULT_UNIT_NUMBER = blockNumber;

                            frame.dispose();

                         } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter valid numbers.");
                        }
                    }
                });

                frame.add(inputPanel, BorderLayout.CENTER);
                frame.add(submitButton, BorderLayout.SOUTH);

                frame.setVisible(true);

            }
        });

        // SETTINGS board -> mainmenu
        mainMenuButtonSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                RenjuGame.layout.show(RenjuGame.mainPanel, "MENU");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

}
