package hraci;

import hraci.generatory.GeneratorMien;
import hraci.generatory.GeneratorVlastnosti;
import tvary.Kruh;
import vynimky.NenasielSuborException;

/**
 * Trieda Hrac, ktorá predstavuje predka pre ostatné triedy, ako Utocnik, Stredopoliar. Tu sa inicializujú hodnoty vlastností hráča, ktorý potomkovia zdedia.
 * @author quang
 */
public abstract class Hrac implements SpecialnaVlastnost {
    private int rychlost;
    private double obrana;
    private double chytanie;
    private int energia;
    private final String meno;
    private int kruhX;
    private int kruhY;
    private final Kruh jKruh;
    private double inteligencia;
    private int dlzkaPrihravania;
    private boolean specialnaVlastnost;

    public Hrac() throws NenasielSuborException {

        this.rychlost = new GeneratorVlastnosti(this).generujRychlost();
        this.obrana = new GeneratorVlastnosti(this).generujObrana();
        this.chytanie = new GeneratorVlastnosti(this).generujChytanie();
        this.inteligencia = new GeneratorVlastnosti(this).generujInteligencia();

        this.meno = new GeneratorMien().generatorMena();
        this.energia = 100;
        this.kruhX = -15;
        this.kruhY = 0;
        this.dlzkaPrihravania = 250;

        this.jKruh = new Kruh(this.kruhX, this.kruhY);

        this.specialnaVlastnost = false;

    }

    public void setRychlost(int rychlost) {
        this.rychlost += rychlost;
    }

    public void setObranu(double obrana) {
        this.obrana += obrana;
    }

    public void setChytanie(double chytanie) {
        this.chytanie += chytanie;
    }

    public void setInteligenciu(double inteligencia) {
        this.inteligencia += inteligencia;
    }

    public int getRychlost() {
        return this.rychlost;
    }

    public double getObrana() {
        return this.obrana;
    }

    public double getChytanie() {
        return this.chytanie;
    }

    public double getInteligencia() {
        return this.inteligencia;
    }

    public void setEnergia(int energia) {
        this.energia += energia;
    }

    public int getEnergia() {
        return this.energia;
    }

    public String getMeno() {
        return this.meno;
    }

    public Kruh getJKruh() {
        return this.jKruh;
    }

    public void posunJKruh(int x, int y) {
        this.kruhX += x;
        this.kruhY += y;
        this.jKruh.posunKruh(x, y);
    }

    public void posunNaMiestoJKruh(int x, int y) {
        this.kruhX = x;
        this.kruhY = y;
        this.jKruh.posunNaMiesto(x, y);
    }

    public int getJKruhX() {
        return this.jKruh.getX();
    }

    public int getJKruhY() {
        return this.jKruh.getY();
    }

    public void setDlzkuPrihravania(int dlzka) {
        this.dlzkaPrihravania += dlzka;
    }

    public int getDlzkuPrihravania() {
        return this.dlzkaPrihravania;
    }

    public void setSpecialnaVlastnost(boolean specialnaVlastnost) {
        this.specialnaVlastnost = specialnaVlastnost;
    }

    public boolean getSpecialnaVlastnost() {
        return this.specialnaVlastnost;
    }

    @Override
    public abstract boolean urobSpecialnuVlastnost();

    @Override
    public abstract void odstranSpecialnuVlastnost();
}