package hra;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Trieda Animacia slúži na animáciu pohybu lopty.
 * @author quang
 */

public class Animacia {
    private final Lopta loptaJ;
    private int x;
    private int y;
    private final ReentrantLock delay = new ReentrantLock();
    private int vzdialenostX;
    private int vzdialenostY;

    public Animacia (Lopta loptaJ) {

        this.x = loptaJ.getX();
        this.y = loptaJ.getY();
        this.loptaJ = loptaJ;

    }

    /**
     * Na začiatku zistí vzdialenosť, ktorú musí lopta prejsť.
     * A po kontrole či nie je vzdialenosť 0 alebo, že atribút this.loptaJ je null, tak sa začne metóda this.zmenaPozicie.
     * Metóda čakaj slúži na vykonanie animácie pomocou metódy Thread.sleep.
     * @param koniecX - koncová pozícia lopty na osi X
     * @param koniecY - koncová pozícia lopty na osi Y
     */
    public void urobAnimaciu(int koniecX, int koniecY) {

        this.vzdialenostX = Math.abs(koniecX - this.x) + 20;
        this.vzdialenostY = Math.abs(koniecY - this.y) + 20;

        while (this.vzdialenostX > 0 || this.vzdialenostY > 0) {

            int iteraciaX = 0;
            int iteraciaY = 0;

            if (this.loptaJ != null) {

                this.zmenaPozicie(this.loptaJ, koniecX, koniecY, this.vzdialenostX, this.vzdialenostY, iteraciaX, iteraciaY);

            }

            this.cakaj();
        }
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    /**
     * Metóda zmenaPozicie slúži na zmenu pozície lopty.
     * @param lopta - lopta, ktorá sa má posunúť
     * @param koniecX - cieľová pozícia lopty na osi X
     * @param koniecY - cieľová pozícia lopty na osi Y
     * @param vzdialenostX - vzdialenosť, ktorú musí lopta prejsť na osi X
     * @param vzdialenostY - vzdialenosť, ktorú musí lopta prejsť na osi Y
     * @param iteraciaX - iterácia čísla, ktoré sa pripočítava k pozícii lopty na osi X
     * @param iteraciaY - iterácia čísla, ktoré sa pripočítava k pozícii lopty na osi Y
     */
    private void zmenaPozicie(Lopta lopta, int koniecX, int koniecY, int vzdialenostX, int vzdialenostY, int iteraciaX, int iteraciaY) {

        int rychlost = 5;

        if (this.x - 20 < koniecX && vzdialenostX > 0) {

            iteraciaX += rychlost;
            this.vzdialenostX -= rychlost;
            this.x += rychlost;

        } else if (this.x + 20 > koniecX && vzdialenostX > 0) {

            iteraciaX -= rychlost;
            this.vzdialenostX -= rychlost;
            this.x -= rychlost;

        }

        if (this.y - 20 < koniecY && vzdialenostY > 0) {

            iteraciaY += rychlost;
            this.vzdialenostY -= rychlost;
            this.y += rychlost;

        } else if (this.y + 20 > koniecY && vzdialenostY > 0) {

            iteraciaY -= rychlost;
            this.vzdialenostY -= rychlost;
            this.y -= rychlost;

        }

        lopta.posunLoptu(iteraciaX, iteraciaY);

    }

    /**
     * Metóda cakaj slúži na vykonanie animácie pomocou metódy Thread.sleep.
     */

    private void cakaj() {

        this.delay.lock();

        try {

            Thread.sleep(50);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);

        } finally {

            this.delay.unlock();

        }
    }
}
