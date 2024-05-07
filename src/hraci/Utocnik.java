package hraci;

import vynimky.NenasielSuborException;

/**
 * Trieda Utocnik predstavuje útočníka, ktorý je hráčom v tíme. Útočník má svoje vlastnosti, ktoré sú rýchlosť, obrana a inteligencia.
 * @author quang
 */

public class Utocnik extends Hrac {

    public Utocnik() throws NenasielSuborException {
        super();
    }

    @Override
    public boolean urobSpecialnuVlastnost() {

        double navysenieInteligencie = 1 - this.getInteligencia();

        if (super.getSpecialnaVlastnost()) {

            this.setChytanie(navysenieInteligencie);
            this.setSpecialnaVlastnost(true);
            return true;

        } else {

            return false;

        }

    }

    @Override
    public void odstranSpecialnuVlastnost() {
        this.setChytanie(-(1 - this.getInteligencia()));
        this.setSpecialnaVlastnost(false);
    }
}
