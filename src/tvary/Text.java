package tvary;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

/**
 * Trieda Text dedí od triedy JLabel a slúži na vytvorenie textu.
 * @author quang
 */
public class Text extends JLabel {

    public Text(String text, int x, int y) {
        super(text);
        this.setBounds(x, y, 1000, 50);
        this.setForeground(Color.BLACK);
        this.setFont(new Font("Arial", Font.BOLD, 30));
    }

    public void zobraz() {
        this.setVisible(true);
    }

    public void skry() {
        this.setVisible(false);
    }

    public void zmenText(String novyText) {
        this.setText(novyText);
    }
    public void zmenPolohu(int x, int y) {
        this.setBounds(x, y, 1000, 50);
    }
}
