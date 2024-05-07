package hraci.generatory;

import hraci.Hrac;
import hraci.Obranca;
import hraci.Stredopoliar;
import hraci.Utocnik;

import java.util.Random;

/**
 * Trieda GeneratorVlastnosti generuje náhodné vlastnosti hráča.
 * @author quang
 */

public class GeneratorVlastnosti {
    private final Random nahoda = new Random();
    private final Hrac hrac;

    public GeneratorVlastnosti(Hrac hrac) {
        this.hrac = hrac;
    }

    public int generujRychlost() {
        if (this.hrac instanceof Utocnik) {
            int min = 8;
            int max = 15;
            return this.nahoda.nextInt((max - min) + 1) + min;

        } else if (this.hrac instanceof Stredopoliar) {
            int min = 5;
            int max = 12;
            return this.nahoda.nextInt((max - min) + 1) + min;

        } else if (this.hrac instanceof Obranca) {
            int min = 6;
            int max = 11;
            return this.nahoda.nextInt((max - min) + 1) + min;
        }
        return 0;
    }

    public double generujObrana() {
        if (this.hrac instanceof Utocnik) {
            double min = 0.0;
            double max = 0.3;
            return this.nahoda.nextDouble() * (max - min) + min;

        } else if (this.hrac instanceof Stredopoliar) {
            double min = 0.3;
            double max = 0.7;
            return this.nahoda.nextDouble() * (max - min) + min;

        } else if (this.hrac instanceof Obranca) {
            double min = 0.4;
            double max = 0.8;
            return this.nahoda.nextDouble() * (max - min) + min;
        }
        return 0.0;
    }

    public double generujInteligencia() {
        if (this.hrac instanceof Utocnik) {
            double min = 0.0;
            double max = 0.7;
            return this.nahoda.nextDouble() * (max - min) + min;

        } else if (this.hrac instanceof Stredopoliar) {
            double min = 0.5;
            double max = 0.8;
            return this.nahoda.nextDouble() * (max - min) + min;

        } else if (this.hrac instanceof Obranca) {
            double min = 0.3;
            double max = 0.7;
            return this.nahoda.nextDouble() * (max - min) + min;

        }
        return 0.0;
    }

    public double generujChytanie() {
        double min = 0.3;
        double max = 0.8;
        return this.nahoda.nextDouble() * (max - min) + min;
    }
}
