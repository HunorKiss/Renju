import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class MyButton extends JButton {

    MyButton(String name){

        setPreferredSize(new Dimension(300, 50));
        setText(name);

        setBorder(BorderFactory.createLineBorder(Color.WHITE,5));
        setMargin(new Insets(30,30,30,30));
        setFont( new Font("Arial", Font.BOLD, 24));
        setOpaque(false);
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
    }
}
