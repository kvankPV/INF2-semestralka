package hraci;

import vynimky.NenasielSuborException;

/**
 * Trieda Obranca predstavuje obrancu, ktorý je hráčom v tíme. Obranca má svoje vlastnosti, ktoré sú rýchlosť, obrana a inteligencia.
 * @author quang
 */

public class Obranca extends Hrac {

    public Obranca() throws NenasielSuborException {
        super();
    }

    @Override
    public boolean urobSpecialnuVlastnost() {

        double navysenieObrany = 1 - this.getObrana();

        if (super.getSpecialnaVlastnost()) {

            this.setObranu(navysenieObrany);
            this.setSpecialnaVlastnost(true);
            return true;

        } else {

            return false;

        }

    }

    @Override
    public void odstranSpecialnuVlastnost() {
        this.setObranu(-(1 - this.getObrana()));
        this.setSpecialnaVlastnost(false);
    }

}
