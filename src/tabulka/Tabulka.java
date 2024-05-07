package tabulka;

import fri.shapesge.FontStyle;
import fri.shapesge.Manazer;
import fri.shapesge.Obrazok;
import fri.shapesge.Text;
import hra.LogikaHry;
import tabulka.generatory.GeneraciaHodnotTimu;
import tim.Tim;
import vynimky.NenasielSuborException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Trieda Tabulka predstavuje tabuľku tímov, ktoré sa zúčastňujú na turnaji. Tabuľka je zoradená podľa bodov, gólov alebo abecedy. Taktiež sa vypisuje na obrazovku.
 * @author quang
 */
public class Tabulka {
    private List<Tim> tabulka = new ArrayList<>(11);
    private final Manazer manazer = new Manazer();
    private final Tim hracovTim;
    private final Obrazok zacniZapas = new Obrazok("resources/zacniZapasTlacidlo.png");
    private final Obrazok zacniTrening = new Obrazok("resources/vykonajTreningTlacidlo.png");
    private final String[][] vypisTabulky = new String[10][3];
    private final GeneraciaHodnotTimu generaciaHodnotTimu = new GeneraciaHodnotTimu();
    private String format;

    public Tabulka(String menoTimu, int pocetObrancov, int pocetStredopoliarov, int pocetUtocnikov) throws NenasielSuborException {

        this.hracovTim = new Tim(menoTimu, pocetObrancov, pocetStredopoliarov, pocetUtocnikov);
        this.tabulka.add(0, this.hracovTim);

        int[] kontrolaPoctuHracov = this.generaciaHodnotTimu.pridavanieHracovKOstatnymTimom();

        for (int i = 1; i < 10; i++) {
            this.tabulka.add(i, new Tim(this.generaciaHodnotTimu.kontrolaMienTimu(this.tabulka), kontrolaPoctuHracov[0], kontrolaPoctuHracov[1], kontrolaPoctuHracov[2]));
            kontrolaPoctuHracov = this.generaciaHodnotTimu.pridavanieHracovKOstatnymTimom();
        }

        this.tabulka = new ZoradovanieZoznamu().zoradTabulkuPodlaAbecedy(this.tabulka);
    }

    public void vypisTabulku() {

        this.aktualizujVysledky();
        this.formatujTabulku();
        this.zobrazTimyDoTabulky(this.format);
        this.vypisNepodstatneVeciNaObrazovku();

    }

    public List<Tim> getTabulka() {
        return Collections.unmodifiableList(this.tabulka);
    }

    /**
     * Metóda vyberSuradnice zistí, či hráč klikol na niektoré z tlačidiel na obrazovke.
     * @param x súradnica x
     * @param y súradnica y
     */
    public void vyberSuradnice(int x, int y) {

        if (x > 1000 && x < 1150 && y > 150 && y < 200) {
            this.manazer.prestanSpravovatObjekt(this);
            this.skryVsetko();

            new LogikaHry(this.hracovTim, this).hrajHru();

            this.vygenerujNahodneVysledkyDoTabulky();
            this.skryVsetko();
            this.tabulka = new ZoradovanieZoznamu().zoradTabulkuPodlaBodov(this.tabulka);
            this.aktualizujVysledky();
            this.formatujTabulku();
            this.vymazTextZTabulky();
            this.obnovTextVTabulke();
            this.zobrazTimyDoTabulky(this.format);
            this.zobrazVsetko();

            this.manazer.spravujObjekt(this);
        }

        if (x > 1000 && x < 1200 && y > 250 && y < 300) {
            this.hracovTim.vykonajTrening();
        }

    }

    private void vygenerujNahodneVysledkyDoTabulky() {
        this.generaciaHodnotTimu.generujNahodneVysledky(this.tabulka, this.hracovTim);
    }

    private void formatujTabulku() {
        int[] maxDlzka = new int[this.vypisTabulky[0].length];

        for (String[] riadok : this.vypisTabulky) {
            for (int i = 0; i < riadok.length; i++) {
                maxDlzka[i] = Math.max(maxDlzka[i], riadok[i].length());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int max : maxDlzka) {
            sb.append("%-").append(max + 2).append("s");
        }

        this.format = sb.toString();
    }

    /**
     * Zobrazí zvyšné veci na obrazovku, ktoré sú potrebné na ovládanie hry.
     */
    private void vypisNepodstatneVeciNaObrazovku() {

        Text menoTimu = new Text("Meno tímu: ", 45, 45);
        menoTimu.zmenFont("Arial", FontStyle.PLAIN, 20);
        menoTimu.zobraz();

        Text gd = new Text("Gólový rozdiel: ", 160, 45);
        gd.zmenFont("Arial", FontStyle.PLAIN, 20);
        gd.zobraz();

        Text body = new Text("Body: ", 300, 45);
        body.zmenFont("Arial", FontStyle.PLAIN, 20);
        body.zobraz();

        this.zacniZapas.posunVodorovne(900);
        this.zacniZapas.posunZvisle(50);
        this.zacniZapas.zobraz();

        this.zacniTrening.posunVodorovne(900);
        this.zacniTrening.posunZvisle(150);
        this.zacniTrening.zobraz();
        this.manazer.spravujObjekt(this);

    }

    private void aktualizujVysledky() {

        for (int i = 0; i < this.vypisTabulky.length; i++) {
            this.vypisTabulky[i][0] = this.tabulka.get(i).getMenoTimu();
            this.vypisTabulky[i][1] = String.valueOf(this.tabulka.get(i).getGolovyRozdiel());
            this.vypisTabulky[i][2] = String.valueOf(this.tabulka.get(i).getBody());
        }

    }

    private void zobrazTimyDoTabulky(String format) {

        int pozicia = 1;
        int vzdialenost = 50;

        for (Tim tim : this.tabulka) {

            tim.setMenoTimuNaObrazovku(String.format("%d. " + format, pozicia, this.vypisTabulky[pozicia - 1][0], this.vypisTabulky[pozicia - 1][1], this.vypisTabulky[pozicia - 1][2]));
            tim.posunZvisle(vzdialenost);
            tim.zobrazText();
            pozicia++;
            vzdialenost += 20;

        }

    }

    private void vymazTextZTabulky() {
        for (Tim tim : this.tabulka) {
            tim.vymazText();
        }
    }

    private void obnovTextVTabulke() {
        for (Tim tim : this.tabulka) {
            tim.obnovText(tim.getMenoTimu());
        }
    }

    private void skryVsetko() {
        for (Tim tim : this.tabulka) {
            tim.skryText();
        }
        this.zacniZapas.skry();
        this.zacniTrening.skry();
    }

    private void zobrazVsetko() {
        for (Tim tim : this.tabulka) {
            tim.zobrazText();
        }
        this.zacniZapas.zobraz();
        this.zacniTrening.zobraz();
    }
}
