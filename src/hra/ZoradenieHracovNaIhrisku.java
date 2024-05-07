package hra;

import fri.shapesge.Text;
import hraci.Brankar;
import hraci.Hrac;
import hraci.Obranca;
import hraci.Stredopoliar;
import hraci.Utocnik;
import tim.Tim;

import java.util.List;

/**
 * Trieda ZoradenieHracovNaIhrisku zoradí hráčov na ihrisku podľa ich pozície.
 * @author quang
 */

public class ZoradenieHracovNaIhrisku {

    private final boolean protivnik;
    private final List<Hrac> tim;
    private int x;
    private int y;
    private final int pocetObrancov;
    private final int pocetStredopoliarov;
    private final int pocetUtocnikov;


    public ZoradenieHracovNaIhrisku(Tim tim, boolean protivnik) {
        this.protivnik = protivnik;
        this.tim = tim.getTim();
        this.pocetObrancov = tim.getPocetObrancov();
        this.pocetStredopoliarov = tim.getPocetStredopoliarov();
        this.pocetUtocnikov = tim.getPocetUtocnikov();
    }

    /**
     * Metóda umiestni hráčov na ich pozíciu na ihrisku.
     */
    public void zoradHracov() {

        if (!this.protivnik) {
            this.x = 60;
        } else {
            this.x = 1200;
        }

        this.y = 360;

        boolean prvy = false;

        for (int i = 0; i < this.tim.size(); i++) {

            if (this.tim.get(i) instanceof Brankar) {

                this.tim.get(i).posunNaMiestoJKruh(this.x, this.y);
                continue;

            }

            if (this.tim.get(i) instanceof Obranca) {
                if (!prvy) {
                    this.y = 0;
                    prvy = true;
                    this.x = this.posunX(this.x, this.protivnik);
                }
                this.umiestenieHracov(i, this.pocetObrancov);
                continue;
            }

            if (this.tim.get(i) instanceof Stredopoliar) {
                if (prvy) {
                    this.y = 0;
                    prvy = false;
                    this.x = this.posunX(this.x, this.protivnik);
                }
                this.umiestenieHracov(i, this.pocetStredopoliarov);
                continue;
            }

            if (this.tim.get(i) instanceof Utocnik) {
                if (!prvy) {
                    this.y = 0;
                    prvy = true;
                    this.x = this.posunX(this.x, this.protivnik);
                }
                this.umiestenieHracov(i, this.pocetUtocnikov);
            }
        }
    }

    /**
     * Metóda posúva hráčov o fixných 160 po x osi.
     * @param x - pozícia hráča
     * @param protivnik - true ak je hráč protivník, false ak nie je
     * @return - pozícia hráča po posunutí
     */
    private int posunX(int x, boolean protivnik) {
        if (!protivnik) {
            return x + 160;
        } else {
            return x - 160;
        }
    }

    /**
     * Metóda umiestňuje hráčov na ich pozíciu na ihrisku. Hodnoty v poli sú určené predom aby sa hráči umiestili na správne pozície.
     * @param index - index hráča v tíme
     * @param pocetHracov - počet hráčov na pozícii
     */
    private void umiestenieHracov(int index, int pocetHracov) {

        int[] velkosti = { 360, 300, 240, 180, 120 };

        if (pocetHracov < 1 || pocetHracov > velkosti.length) {
            new Text("Nespravny pocet hracov", 600, 600).zobraz();
            return;
        }

        int velkost = velkosti[pocetHracov - 1] + this.y;
        this.y += 120;

        this.tim.get(index).posunNaMiestoJKruh(this.x, velkost);
    }
}
