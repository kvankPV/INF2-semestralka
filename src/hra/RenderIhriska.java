package hra;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Trieda RenderIhriska vykresľuje ihrisko.
 * @author quang
 */

public class RenderIhriska {
    private final JLabel stredLabel;
    private final JLabel lavaSestnaskaLabel;
    private final JLabel pravaSestnaskaLabel;

    public RenderIhriska() {

        ImageIcon stredIcon = new ImageIcon("resources/stred.png");
        ImageIcon lavaSestnaskaIcon = new ImageIcon("resources/sestnaska.png");
        ImageIcon pravaSestnaskaIcon = new ImageIcon("resources/sestnaska.png");

        this.stredLabel = new JLabel(stredIcon);
        this.stredLabel.setBounds(540, 275, stredIcon.getIconWidth(), stredIcon.getIconHeight());
        this.lavaSestnaskaLabel = new JLabel(lavaSestnaskaIcon);
        this.lavaSestnaskaLabel.setBounds(-10, 255, lavaSestnaskaIcon.getIconWidth(), lavaSestnaskaIcon.getIconHeight());
        this.pravaSestnaskaLabel = new JLabel(pravaSestnaskaIcon);
        this.pravaSestnaskaLabel.setBounds(1080, 255, pravaSestnaskaIcon.getIconWidth(), pravaSestnaskaIcon.getIconHeight());
    }

    public void zobrazIhrisko() {
        this.stredLabel.setVisible(true);
        this.lavaSestnaskaLabel.setVisible(true);
        this.pravaSestnaskaLabel.setVisible(true);
    }

    public JLabel getStredLabel() {
        return this.stredLabel;
    }

    public JLabel getLavaSestnaskaLabel() {
        return this.lavaSestnaskaLabel;
    }

    public JLabel getPravaSestnaskaLabel() {
        return this.pravaSestnaskaLabel;
    }
}
