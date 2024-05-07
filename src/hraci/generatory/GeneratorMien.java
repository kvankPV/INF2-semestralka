package hraci.generatory;

import vynimky.NenasielSuborException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Trieda GeneratorMien generuje náhodné mená hráčov z textového súboru.
 * @author quang
 */
public class GeneratorMien {
    public String generatorMena() throws NenasielSuborException {
        Random nahoda = new Random();
        ArrayList<String> mena = new ArrayList<>();
        ArrayList<String> priezviska = new ArrayList<>();
        Scanner citac;

        try {
            citac = new Scanner(new File("resources/Mena.txt"));
        } catch (FileNotFoundException e) {
            throw new NenasielSuborException("Nenasiel som subor, skuste premenovat subor alebo zmenit typ suboru.");
        }

        while (citac.hasNextLine()) {
            mena.add(citac.nextLine());
        }
        citac.close();

        try {
            citac = new Scanner(new File("resources/Priezviska.txt"));
        } catch (FileNotFoundException e) {
            throw new NenasielSuborException("Nenasiel som subor, skuste premenovat subor alebo zmenit typ suboru.");
        }

        while (citac.hasNextLine()) {
            priezviska.add(citac.nextLine());
        }
        citac.close();

        String meno = mena.get(nahoda.nextInt(mena.size()));
        String priezvisko = priezviska.get(nahoda.nextInt(priezviska.size()));
        return meno + " " + priezvisko;
    }
}
