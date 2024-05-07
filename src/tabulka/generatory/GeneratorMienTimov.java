package tabulka.generatory;

import vynimky.NenasielSuborException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Trieda GeneratorMienTimov generuje náhodné mená tímov z textového súboru.
 * @author quang
 */

public class GeneratorMienTimov {
    public String generatorMenaTimu() throws NenasielSuborException {
        Random nahoda = new Random();
        ArrayList<String> mena = new ArrayList<>();
        Scanner citac;
        try {
            citac = new Scanner(new File("resources/MenaTimov.txt"));
        } catch (FileNotFoundException e) {
            throw new NenasielSuborException("Nenasiel som subor, skuste premenovat subor alebo zmenit typ suboru.");
        }

        while (citac.hasNextLine()) {
            mena.add(citac.nextLine());
        }
        citac.close();

        return mena.get(nahoda.nextInt(mena.size() - 1));
    }
}
