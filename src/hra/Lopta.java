package hra;


import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * Trieda Lopta vykresľuje loptu.
 * PNG súbor prevzatý z : <a href="https://www.flaticon.com/free-icon/soccer-ball_5438899">...</a>
 * @author quang
 */

public class Lopta {

    private final JLabel lopta;
    private final ImageIcon obrazok = new ImageIcon("resources/lopta.png");
    private int x;
    private int y;
    public Lopta() {
        this.x = 630;
        this.y = 360;
        this.lopta = new JLabel(this.obrazok);
        this.lopta.setBounds(this.x, this.y, this.obrazok.getIconWidth(), this.obrazok.getIconHeight());
    }

    public void posunLoptu(int x, int y) {
        this.x += x;
        this.y += y;
        this.lopta.setBounds(this.x, this.y, this.obrazok.getIconWidth(), this.obrazok.getIconHeight());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public JLabel getLopta() {
        return this.lopta;
    }
}
