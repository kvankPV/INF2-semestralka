package tabulka;

import tim.Tim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Trieda ZoradovanieZoznamu zoraďuje zoznamy podla abecedy alebo podľa počtu bodov.
 * @author quang
 */
public class ZoradovanieZoznamu {

    public List<Tim> zoradTabulkuPodlaAbecedy(List<Tim> tabulka) {

        ArrayList<String> zoznamTimovPodlaMena = new ArrayList<>();
        ArrayList<Tim> docasnyZoznam = new ArrayList<>();

        for (Tim tim : tabulka) {
            zoznamTimovPodlaMena.add(tim.getMenoTimu());
        }

        Collections.sort(zoznamTimovPodlaMena);
        int i = 0;
        boolean nieJeUsporiadany = true;

        while (nieJeUsporiadany) {
            for (Tim team : tabulka) {

                if (team.getMenoTimu().equals(zoznamTimovPodlaMena.get(i))) {
                    docasnyZoznam.add(i, team);
                    i++;
                }

                if (i > 9) {
                    nieJeUsporiadany = false;
                    break;
                }
            }
        }
        return Collections.unmodifiableList(docasnyZoznam);
    }

    public List<Tim> zoradTabulkuPodlaBodov(List<Tim> tabulka) {

        ArrayList<Integer> zoznamBodov = new ArrayList<>();
        List<Tim> docasnyZoznam = new ArrayList<>();

        for (Tim tim : tabulka) {
            zoznamBodov.add(tim.getBody());
        }

        zoznamBodov.sort(Comparator.reverseOrder());

        int i = 0;
        boolean nieJeUsporiadany = true;

        while (nieJeUsporiadany) {
            for (Tim team : tabulka) {

                if (team.getBody() == zoznamBodov.get(i)) {
                    docasnyZoznam.add(i, team);
                    i++;
                }

                if (i > 9) {
                    nieJeUsporiadany = false;
                    break;
                }
            }
        }

        return Collections.unmodifiableList(docasnyZoznam);
    }
}
