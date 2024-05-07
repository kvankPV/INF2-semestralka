package hraci;

import vynimky.NenasielSuborException;

/**
 * Trieda Brankar predstavuje brankára, ktorý je hráčom v tíme. Brankár má svoje vlastnosti, ktoré sú chytanie a inteligencia.
 * @author quang
 */
public class Brankar extends Hrac {
    public Brankar() throws NenasielSuborException {
        super();
    }

    @Override
    public boolean urobSpecialnuVlastnost() {

        double navysenieChytania = 1 - this.getChytanie();

        if (super.getSpecialnaVlastnost()) {

            this.setChytanie(navysenieChytania);
            this.setSpecialnaVlastnost(true);
            return true;

        } else {

            return false;

        }

    }

    @Override
    public void odstranSpecialnuVlastnost() {
        this.setChytanie(-(1 - this.getChytanie()));
        this.setSpecialnaVlastnost(false);
    }
}
