package hraci;

import vynimky.NenasielSuborException;

/**
 * Trieda Stredopoliar predstavuje stredopoliara, ktorý je hráčom v tíme. Stredopoliar má svoje vlastnosti, ktoré sú rýchlosť, obrana a inteligencia.
 * @author quang
 */

public class Stredopoliar extends Hrac {

    public Stredopoliar() throws NenasielSuborException {
        super();
    }

    @Override
    public boolean urobSpecialnuVlastnost() {

        if (super.getSpecialnaVlastnost()) {

            this.setDlzkuPrihravania(150);
            this.setSpecialnaVlastnost(true);
            return true;

        } else {

            return false;

        }

    }

    @Override
    public void odstranSpecialnuVlastnost() {
        this.setDlzkuPrihravania(-150);
        this.setSpecialnaVlastnost(false);
    }
}
