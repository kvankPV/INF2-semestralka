package tim;

import fri.shapesge.FontStyle;
import fri.shapesge.Text;
import hraci.Brankar;
import hraci.Hrac;
import hraci.Obranca;
import hraci.Stredopoliar;
import hraci.Utocnik;
import tvary.Kruh;
import vynimky.NenasielSuborException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda Tim predstavuje tím, ktorý sa skladá z hráčov. Tím má svoje meno, ktoré si manažér môže zvoliť, a počet hráčov v jednotlivých pozíciách.
 * @author quang
 */
public class Tim {
    private final ArrayList<Hrac> tim = new ArrayList<>();
    private final Trening trening;
    private final String menoTimu;
    private final int pocetObrancov;
    private final int pocetStredopoliarov;
    private final int pocetUtocnikov;
    private boolean hralSomProtiTimu = false;
    private Text vypisNaObrazovku;
    private boolean drzimLoptu = false;
    private int pocetBodov = 0;
    private int vystreleneGoly = 0;
    private int inkasovaneGoly = 0;

    /**
     * Inicializácia tímu do Listu hráčov.
     * @param menoTimu - meno tímu
     * @param pocetObrancov - počet obrancov
     * @param pocetStredopoliarov - počet stredopoliarov
     * @param pocetUtocnikov - počet útočníkov
     */
    public Tim (String menoTimu, int pocetObrancov, int pocetStredopoliarov, int pocetUtocnikov) throws NenasielSuborException {

        this.pocetObrancov = pocetObrancov;
        this.pocetStredopoliarov = pocetStredopoliarov;
        this.pocetUtocnikov = pocetUtocnikov;
        this.menoTimu = menoTimu;
        this.tim.add(new Brankar());
        this.vypisNaObrazovku = new Text("");
        this.vypisNaObrazovku.zmenFont("Arial", FontStyle.PLAIN, 20);
        this.vypisNaObrazovku.posunVodorovne(50);

        int cislo = 1;

        while (cislo <= pocetObrancov) {
            this.tim.add(new Obranca());
            cislo++;
        }

        cislo = 1;

        while (cislo <= pocetStredopoliarov) {
            this.tim.add(new Stredopoliar());
            cislo++;
        }

        cislo = 1;

        while (cislo <= pocetUtocnikov) {
            this.tim.add(new Utocnik());
            cislo++;
        }

        this.trening = new Trening();
    }


    public void vykonajTrening() {
        this.trening.vykonajTrening(this.getTim());
    }

    public String getMenoTimu() {
        return this.menoTimu;
    }

    public List<Hrac> getTim() {
        return Collections.unmodifiableList(this.tim);
    }

    public List<Kruh> getKruhy() {
        List<Kruh> kruhy = new ArrayList<>();
        for (Hrac hrac : this.tim) {
            kruhy.add(hrac.getJKruh());
        }
        return Collections.unmodifiableList(kruhy);
    }

    public void setDrzimLoptu(boolean drzimLoptu) {
        this.drzimLoptu = drzimLoptu;
    }

    public boolean getDrzimLoptu() {
        return this.drzimLoptu;
    }

    public int getPocetObrancov() {
        return this.pocetObrancov;
    }

    public int getPocetStredopoliarov() {
        return this.pocetStredopoliarov;
    }

    public int getPocetUtocnikov() {
        return this.pocetUtocnikov;
    }

    public void setHralSomProtiTimu(boolean hralSomProtiTimu) {
        this.hralSomProtiTimu = hralSomProtiTimu;
    }

    public boolean getHralSomProtiTimu() {
        return !this.hralSomProtiTimu;
    }

    public void posunZvisle(int y) {
        this.vypisNaObrazovku.posunZvisle(y);
    }

    public void pripisBody(int pocetBodov) {
        this.pocetBodov += pocetBodov;
    }

    public int getBody() {
        return this.pocetBodov;
    }

    public void pripisGoly(int pocetGolov) {
        this.vystreleneGoly += pocetGolov;
    }

    public void inkasovaneGoly(int inkas) {
        this.inkasovaneGoly += inkas;
    }

    public int getGolovyRozdiel() {
        return this.vystreleneGoly - this.inkasovaneGoly;
    }

    public void setMenoTimuNaObrazovku(String text) {
        this.vypisNaObrazovku.changeText(text);
    }

    public void zobrazText() {
        this.vypisNaObrazovku.zobraz();
    }

    public void skryText() {
        this.vypisNaObrazovku.skry();
    }

    public void obnovText(String nazov) {
        this.vypisNaObrazovku = new Text(nazov);
        this.vypisNaObrazovku.zmenFont("Arial", FontStyle.PLAIN, 20);
        this.vypisNaObrazovku.posunVodorovne(50);
    }
    public void vymazText() {
        this.vypisNaObrazovku = null;
    }
}
