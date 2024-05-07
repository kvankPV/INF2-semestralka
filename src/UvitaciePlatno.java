import fri.shapesge.FontStyle;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;
import fri.shapesge.Text;
import tabulka.Tabulka;
import vynimky.NenasielSuborException;
import vynimky.PrazdnyVstupException;
import vynimky.ZlyVstupException;

import javax.swing.JOptionPane;

/**
 * Trieda, ktorá privíta hráča do hry, tu používam ShapesGE, ako plátno.
 * @author quang
 */
public class UvitaciePlatno {

    private final Text text;
    private final Obrazok tlacidlo;
    private final Manazer manazer = new Manazer();

    public UvitaciePlatno() {

        this.text = new Text("Vitajte vo FRI FM !", 360, 300);
        this.text.zmenFont("Arial", FontStyle.BOLD, 60);
        this.text.zobraz();

        this.tlacidlo = new Obrazok("resources/zacniHratTlacidlo.png", 540, 400);
        this.tlacidlo.zobraz();

        this.manazer.spravujObjekt(this);
    }

    /**
     * Súradnice, ktoré získam z atribútu manazer, ktorý spravuje objekt.
     * Dá sa to spraviť aj cez MouseListener, ale musel by som používať JFrame, ktorý by som musel vytvoriť.
     * Ak platia súradnice, tak sa zobrazí JOptionPane, ktorý získa vstup od hráča.
     * @param x súradnica x
     * @param y súradnica y
     */
    public void vyberSuradnice(int x, int y) throws NenasielSuborException {

        if (x > 540 && x < 740 && y > 400 && y < 500) {
            this.text.skry();
            this.tlacidlo.skry();
            this.manazer.prestanSpravovatObjekt(this);

            String[] spravy = {"Zadajte meno timu: ", "Zadajte pocet obrancov <1 - 5> : ", "Zadajte pocet stredopoliarov <1 - 5> : ", "Zadajte pocet utocnikov <1 - 5> : "};
            String[] vstupy = new String[4];

            for (int i = 0; i < spravy.length; i++) {
                boolean nieJeChyba = true;

                while (nieJeChyba) {

                    try {
                        String input = JOptionPane.showInputDialog(spravy[i]);

                        if (input == null) {
                            System.exit(0);
                        }

                        if (input.trim().isEmpty()) {
                            throw new PrazdnyVstupException("Vstup nemoze byt prazdny ! Prosim skuste znova.");
                        }

                        if (i >= 1) {

                            try {

                                int cislo = Integer.parseInt(input);

                                if (cislo < 1 || cislo > 5) {
                                    throw new ZlyVstupException("Nezadali ste cislo z rozmedzia, skuste znova!");
                                }

                            } catch (NumberFormatException e) {

                                throw new ZlyVstupException("Pri pocte mozte zadat len cislo, skuste znova!");

                            }

                        }

                        vstupy[i] = input;
                        nieJeChyba = false;

                    } catch (PrazdnyVstupException | ZlyVstupException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage());
                    }
                }
            }

            if ((Integer.parseInt(vstupy[1]) + (Integer.parseInt(vstupy[2]) + (Integer.parseInt(vstupy[3])))) != 10) {
                try {
                    throw new ZlyVstupException("Pocet hracov musi byt spolu 11 (10 + brankár, ktory sa nezadáva) ! Prosim skuste znova.");
                } catch (ZlyVstupException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

            try {
                new Tabulka(vstupy[0], Integer.parseInt(vstupy[1]), Integer.parseInt(vstupy[2]), Integer.parseInt(vstupy[3])).vypisTabulku();
            } catch (NenasielSuborException e) {
                throw new NenasielSuborException(e.getMessage());
            }
        }
    }
}
