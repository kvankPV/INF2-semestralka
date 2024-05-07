package tabulka.generatory;

import tim.Tim;
import vynimky.NenasielSuborException;

import java.util.List;
import java.util.Random;

public class GeneraciaHodnotTimu {

    /**
     * Metóda kontroluje, či sa v tabuľke nenachádza rovnaké meno tímu.
     * @param tabulka - tabuľka tímov
     * @return menoTimu-náhodne vygenerované meno tímu
     * @author quang
     */
    public String kontrolaMienTimu(List<Tim> tabulka) throws NenasielSuborException {

        String menoTimu = new GeneratorMienTimov().generatorMenaTimu();

        if (tabulka.size() >= 2) {
            boolean jeTamIsteMeno = true;
            while (jeTamIsteMeno) {
                jeTamIsteMeno = false;

                for (int i = 1; i <= tabulka.size() - 1; i++) {

                    if (tabulka.get(i).getMenoTimu().equals(menoTimu)) {
                        jeTamIsteMeno = true;
                        menoTimu = new GeneratorMienTimov().generatorMenaTimu();
                        break;
                    }
                }
            }
        }
        return menoTimu;
    }

    /**
     * Metóda generuje náhodný počet jednotlivých hráčov pre jednotlivé pozície v tíme.
     * @return uloziskoHodnot-pole s hodnotami počtu hráčov pre jednotlivé pozície.
     */
    public int[] pridavanieHracovKOstatnymTimom () {
        int[] uloziskoHodnot = new int[3];
        Random rand = new Random();
        int sucet;
        boolean platiSucet = true;

        while (platiSucet) {
            int pocetObrancov = rand.nextInt(3) + 3;
            int pocetStredopoliarov = rand.nextInt(3) + 3;
            int pocetUtocnikov = rand.nextInt(3) + 1;
            sucet = pocetObrancov + pocetStredopoliarov + pocetUtocnikov;

            if (sucet == 10) {
                platiSucet = false;
                uloziskoHodnot[0] = pocetObrancov;
                uloziskoHodnot[1] = pocetStredopoliarov;
                uloziskoHodnot[2] = pocetUtocnikov;
            }
        }
        return uloziskoHodnot;
    }

    /**
     * Metóda, ktorá generuje náhodne výsledky bodov, a koľko gólov dostali a skórovali.
     * @param tabulka - tabulka
     * @param mojTim - tím hráča, aby sa negenerovali výsledky aj preňho
     */

    public void generujNahodneVysledky(List<Tim> tabulka, Tim mojTim) {
        for (Tim tim : tabulka) {

            if (mojTim != tim && tim.getHralSomProtiTimu()) {

                int randBody = new Random().nextInt(3);
                int randGoly = new Random().nextInt(7);

                if (randBody == 2) {

                    tim.pripisBody(3);
                    tim.pripisGoly(randGoly);

                } else if (randBody == 1) {

                    tim.pripisBody(1);

                } else {

                    tim.pripisBody(0);
                    tim.inkasovaneGoly(randGoly);

                }
            }
        }
    }
}
