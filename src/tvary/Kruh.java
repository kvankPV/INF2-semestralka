package tvary;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * Trieda Kruh dedí od triedy JPanel a slúži na vytvorenie kruhu.
 * @author quang
 */

public class Kruh extends JPanel {
    private static final int PRIEMER = 20;
    private Color farba = Color.CYAN;
    public Kruh(int x, int y) {
        this.setBounds(x, y, PRIEMER * 2, PRIEMER * 2);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(this.farba);
        g.fillOval(0 , 0, PRIEMER, PRIEMER);
    }

    public void zmenFarbu(Color farba) {
        this.farba = farba;
        this.repaint();
    }

    public void posunKruh(int x, int y) {
        this.setBounds(this.getX() + x, this.getY() + y,  PRIEMER * 2, PRIEMER * 2);
    }

    public void posunNaMiesto(int x, int y) {
        this.setBounds(x, y, PRIEMER * 2, PRIEMER * 2);
    }

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }
}
