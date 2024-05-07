package tim;

import fri.shapesge.FontStyle;
import fri.shapesge.Text;
import hraci.Brankar;
import hraci.Hrac;
import hraci.Obranca;
import hraci.Stredopoliar;
import hraci.Utocnik;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Trening {

    /**
     * Vykoná tréning. Prejde celý List hráčov, zistí aký typ hráča je, a vykoná random kde je 8 % šanca, že sa mu pridá atribút.
     * Po tréningu sa zníži energia hráča, aby manažér nemohol neustále mať tréning s hráčmi.
     * @author quang
     **/
    public void vykonajTrening (List<Hrac> tim) {

        List<String> vypisNaObrazovku = new ArrayList<>();

        for (Hrac hrac : tim) {

            if (hrac.getEnergia() > 10) {

                if (hrac instanceof Brankar) {

                    if (this.maSancu()) {
                        hrac.setChytanie(0.025);
                        vypisNaObrazovku.add(hrac.getMeno() + " ziskal chytanie.");
                    }

                    if (this.maSancu()) {
                        hrac.setInteligenciu(0.025);
                        vypisNaObrazovku.add(hrac.getMeno() + " ziskal inteligenciu.");
                    }

                    hrac.setEnergia(-25);

                } else if (hrac instanceof Obranca || hrac instanceof Stredopoliar || hrac instanceof Utocnik) {

                    this.pridavanieVlastnosti(hrac, vypisNaObrazovku);
                }

            } else {

                Text vycerpalSiEnergiu = new Text("Vycerpal si energiu hračov, už nemôžeš trénovať.");
                this.casovac(vycerpalSiEnergiu);
                vycerpalSiEnergiu.posunVodorovne(500);
                vycerpalSiEnergiu.posunZvisle(20);
                vycerpalSiEnergiu.zmenFont("Arial", FontStyle.PLAIN, 16);
                vycerpalSiEnergiu.zobraz();
                return;

            }


        }
        this.vypisNaObrazovku(vypisNaObrazovku);
    }

    /**
     * Vypíše na obrazovku, ktorý hráč získal atribút.
     * @param vypisNaObrazovku List, kde sú uložení hráči, ktorý dostali atribút.
     */

    private void vypisNaObrazovku(List<String> vypisNaObrazovku) {

        if (vypisNaObrazovku.isEmpty()) {
            Text niktoNedostalAtribut = new Text("Nikto nedostal atribut.");
            this.casovac(niktoNedostalAtribut);
            niktoNedostalAtribut.posunVodorovne(500);
            niktoNedostalAtribut.posunZvisle(20);
            niktoNedostalAtribut.zmenFont("Arial", FontStyle.PLAIN, 16);
            niktoNedostalAtribut.zobraz();
            this.casovac(niktoNedostalAtribut);
            return;
        }

        int vzdialenost = 20;
        for (String sprava : vypisNaObrazovku) {
            Text vypis = new Text(sprava);
            vypis.posunVodorovne(500);
            vypis.posunZvisle(vzdialenost);
            vypis.zmenFont("Arial", FontStyle.PLAIN, 16);
            vypis.zobraz();
            vzdialenost += 20;
            this.casovac(vypis);
        }
    }
    /**
     5% šanca, že je True, inak vyhodí false.
     **/
    private boolean maSancu() {
        Random rand = new Random();
        return rand.nextDouble() <= 0.05;
    }


    private void pridavanieVlastnosti(Hrac hrac, List<String> vypisNaObrazovku) {

        if (this.maSancu()) {
            hrac.setRychlost(1);
            vypisNaObrazovku.add(hrac.getMeno() + " ziskal rychlost.");
        }

        if (this.maSancu()) {
            hrac.setObranu(0.025);
            vypisNaObrazovku.add(hrac.getMeno() + " ziskal obranu.");
        }

        if (this.maSancu()) {
            hrac.setInteligenciu(0.025);
            vypisNaObrazovku.add(hrac.getMeno() + " ziskal inteligenciu.");
        }

        hrac.setEnergia(-25);
    }

    private void casovac(Text text) {
        Timer timer = new Timer(3500, e -> text.skry());
        timer.setRepeats(false);
        timer.start();
    }
}