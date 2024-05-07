package hra;

import hraci.Brankar;
import hraci.Hrac;
import tabulka.Tabulka;
import tim.Tim;
import tvary.Kruh;
import tvary.Text;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Random;
import java.util.Objects;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Trieda LogikaHry predstavuje logiku hry. V tejto triede sa nachádza väčšina metód, ktoré sú potrebné pre hru.
 * @author quang
 */

public class LogikaHry {
    private final List<Hrac> listMojhoTimu;
    private final List<Hrac> listProtivnika;
    private final Lopta loptaJ = new Lopta();
    private final Animacia animacia = new Animacia(this.loptaJ);
    private final ArrayList<Hrac> zoznamMojichHracovVRadiuse = new ArrayList<>();
    private final ArrayList<Hrac> zoznamProtivnikovVRadiuse = new ArrayList<>();
    private final List<Tim> listTimov;
    private final Tim mojTim;
    private final Tim protivnik;
    private final JFrame frame = new JFrame();
    private ZoradenieHracovNaIhrisku zoradenieMojichHracovNaIhrisku;
    private ZoradenieHracovNaIhrisku zoradenieProtivnikovNaIhrisku;
    private final Text mojeSkore;
    private final Text protivnikoveSkore;
    private final Text cas;
    private final Text oznamy = new Text(null, 560, 100);
    private int mojeGoly = 0;
    private int protivnikoveGoly = 0;
    public LogikaHry(Tim mojTim, Tabulka tabulka) {

        RenderIhriska renderIhriska = new RenderIhriska();
        renderIhriska.zobrazIhrisko();
        this.mojTim = mojTim;
        this.listMojhoTimu = mojTim.getTim();

        this.zoradenieMojichHracovNaIhrisku = new ZoradenieHracovNaIhrisku(mojTim, false);
        this.zoradenieMojichHracovNaIhrisku.zoradHracov();
        this.listTimov = tabulka.getTabulka();

        this.protivnik = this.vyberProtivnika();
        this.listProtivnika = Objects.requireNonNull(this.protivnik).getTim();
        this.zoradenieProtivnikovNaIhrisku = new ZoradenieHracovNaIhrisku(Objects.requireNonNull(this.protivnik), true);
        this.zoradenieProtivnikovNaIhrisku.zoradHracov();

        this.mojeSkore = new Text("0", 580, 50);
        this.frame.add(this.mojeSkore);
        this.frame.add(new Text(":", 630, 50));
        this.protivnikoveSkore = new Text("0", 670, 50);
        this.frame.add(this.protivnikoveSkore);
        this.cas = new Text("0", 590, 10);
        this.frame.add(this.cas);
        Text menoTimu = new Text(this.mojTim.getMenoTimu(), 300, 50);
        Text menoProtivnika = new Text(Objects.requireNonNull(this.protivnik).getMenoTimu(), 800, 50);
        this.frame.add(menoTimu);
        this.frame.add(menoProtivnika);

        this.frame.setSize(1280, 720);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(null);
        this.frame.setResizable(false);

        for (Kruh k : mojTim.getKruhy()) {
            this.frame.add(k);
        }

        for (Kruh k : this.protivnik.getKruhy()) {
            k.zmenFarbu(Color.red);
            this.frame.add(k);
        }

        this.frame.add(this.loptaJ.getLopta());
        this.frame.add(renderIhriska.getStredLabel());
        this.frame.add(renderIhriska.getLavaSestnaskaLabel());
        this.frame.add(renderIhriska.getPravaSestnaskaLabel());
        this.frame.getContentPane().setBackground(new Color(151, 157, 152));
        this.frame.setVisible(true);
    }

    /**
     * Metóda vypíše text a začne sa 5 minútový časovač. V tomto čase sa vykonáva cyklus, kde sa hrá hra.
     * Po 5 minútach sa vypíše text a hra sa ukončí.
     */
    public void hrajHru() {

        this.vypisOznam("Zacala Hra");

        this.mojTim.setDrzimLoptu(true);

        AtomicBoolean kontrolaCiJeKoniecHry = new AtomicBoolean(false);

        Timer casovac = new Timer();
        TimerTask ulohy = new TimerTask() {
            @Override
            public void run() {
                kontrolaCiJeKoniecHry.set(true);
            }
        };

        casovac.schedule(ulohy, 2 * 60 * 1000);

        long start = System.nanoTime();

        while (!kontrolaCiJeKoniecHry.get()) {
            this.najdiNablizsiehoHracaKLopte();
            String time = String.format("%.1f", (System.nanoTime() - start) / (60.0 * 1_000_000_000));
            this.cas.zmenText(time + " min");
        }

        if (this.mojeGoly > this.protivnikoveGoly) {
            this.mojTim.pripisBody(3);
        } else if (this.mojeGoly < this.protivnikoveGoly) {
            Objects.requireNonNull(this.protivnik).pripisBody(3);
        } else {
            this.mojTim.pripisBody(1);
            Objects.requireNonNull(this.protivnik).pripisBody(1);
        }

        for (Hrac h : this.listMojhoTimu) {
            h.odstranSpecialnuVlastnost();
            h.setEnergia(100 - h.getEnergia());
        }

        for (Hrac h : Objects.requireNonNull(this.protivnik).getTim()) {
            h.odstranSpecialnuVlastnost();
        }

        this.oznamy.zmenPolohu(530, 100);
        this.vypisOznam("Koniec Hry");
        this.frame.dispose();
    }

    /**
     * Metóda vypíše oznam na obrazovku a po 3,5 sekundách sa oznam skryje.
     * @param text Text, ktorý sa má vypísať.
     */
    private void vypisOznam(String text) {

        this.oznamy.zmenText(text);
        this.oznamy.zobraz();
        javax.swing.Timer cakaj = new javax.swing.Timer(3500, e -> this.oznamy.skry());
        cakaj.setRepeats(false);
        cakaj.start();
        this.frame.revalidate();
        this.frame.repaint();
        this.frame.add(this.oznamy);

    }

    /**
     * Metóda hľadá najbližšieho hráča k lopte. Dôležitú podmienku, ktorý jeden z tímov musí spĺňať je, že kto drží loptu, aby došlo k prihrávke.
     * Po nájdení sa vykonáva metóda animáciaLopty, ktorá zabezpečí pohyb lopty.
     */

    private void najdiNablizsiehoHracaKLopte() {
        if (this.mojTim.getDrzimLoptu()) {
            this.najdiHracovKtorySuBlizkoKLopte(this.listMojhoTimu, this.zoznamMojichHracovVRadiuse);
        } else {
            this.najdiHracovKtorySuBlizkoKLopte(this.listProtivnika, this.zoznamProtivnikovVRadiuse);
        }
        this.animaciaLopty();
    }

    /**
     * Metóda zistí, či je hráč blízko lopty. Ak je, tak sa pridá do zoznamu.
     * @param tim - tím, ktorého hráčov chcem zistiť pozíciu
     * @param zoznam - zoznam hráčov, ktorí sú blízko lopty
     */
    private void najdiHracovKtorySuBlizkoKLopte(List<Hrac> tim, List<Hrac> zoznam) {
        for (Hrac h : tim) {
            if (this.zistujemKtoJeBlizkoLopty(h, h.getDlzkuPrihravania(), this.loptaJ)) {
                if (h.getJKruhX() <= 400 && h.getJKruhX() >= 780) {
                    zoznam.clear();
                    zoznam.add(h);
                    break;
                } else {
                    zoznam.add(h);
                }
            }
        }
    }

    /**
     * Metóda vykoná animáciu lopty. Lopta sa posunie na pozíciu hráča, posunie hráčov vyššie ihriskom a hľadá kto drží loptu.
     */
    private void animaciaLopty() {

        Hrac hrac = this.hracZDocasnehoZoznamu();
        assert hrac != null;
        this.animacia.urobAnimaciu(hrac.getJKruhX() - 25, hrac.getJKruhY() - 25);

        this.posunZvysokHracovVyssie(hrac, this.listMojhoTimu);

        this.posunZvysokHracovVyssie(hrac, this.listProtivnika);

        this.najdiHracaCoDrziLoptu(hrac);


    }

    /**
     * Dostávam hráča zo zoznamu, buď priamo, alebo cez Random.
     * @return - hráč
     */
    private Hrac hracZDocasnehoZoznamu() {

        if (this.mojTim.getDrzimLoptu()) {

            if (this.zoznamMojichHracovVRadiuse.size() >= 2) {

                Random rand = new Random();
                int i = rand.nextInt(this.zoznamMojichHracovVRadiuse.size());
                return this.zoznamMojichHracovVRadiuse.get(i);

            } else if (this.zoznamMojichHracovVRadiuse.size() == 1) {

                return this.zoznamMojichHracovVRadiuse.get(0);

            }

        } else {

            if (this.zoznamProtivnikovVRadiuse.size() >= 2) {

                Random rand = new Random();
                int i = rand.nextInt(this.zoznamProtivnikovVRadiuse.size());
                return this.zoznamProtivnikovVRadiuse.get(i);

            } else if (this.zoznamProtivnikovVRadiuse.size() == 1) {

                return this.zoznamProtivnikovVRadiuse.get(0);

            }
        }
        return null;
    }

    /**
     * Ak hráč drží loptu, tak má druhý tím šancu zakročiť loptu, a ten čo drží loptu strieľať ak spĺňajú podmienky.
     * @param drziLoptu - hráč, ktorý drží loptu
     */
    private void najdiHracaCoDrziLoptu(Hrac drziLoptu) {

        if (this.zoznamProtivnikovVRadiuse.contains(drziLoptu)) {

            this.zakroc(this.listMojhoTimu, drziLoptu);
            this.moznostStrely(this.listProtivnika, drziLoptu);

        } else {

            this.zakroc(this.listProtivnika, drziLoptu);
            this.moznostStrely(this.listMojhoTimu, drziLoptu);

        }

        /*
          5% šanca na to, že hráč, ktorý drží loptu, dostane specialnu vlastnosť, ktorá sa odstráni po ukončení hry.
         */

        if (new Random().nextDouble() <= 0.05 && drziLoptu.urobSpecialnuVlastnost()) {
            this.vypisOznam("Hrac " + drziLoptu.getMeno() + " ma specialnu vlastnost!");
        }
    }

    /**
     * Vykoná sa zakročenie lopty ak sa splnia podmienky. Či je hráč, ktorý zakročí blízko lopty, a či má vyššiu obranu, ako inteligenciu hráča, ktorý prihráva.
     * @param tim - tím, ktorého hráči zakročia
     * @param prihravac - hráč, ktorý prihráva
     */
    private void zakroc(List<Hrac> tim, Hrac prihravac) {

        for (Hrac h : tim) {

            if (this.zistujemKtoJeBlizkoLopty(h, 30, this.animacia) && h.getObrana() > prihravac.getInteligencia()) {
                this.animacia.urobAnimaciu(h.getJKruhX() - 25, h.getJKruhY() - 25);
                this.oznamy.zmenPolohu(400, 100);

                if (tim == this.listMojhoTimu) {

                    this.mojTim.setDrzimLoptu(true);
                    this.protivnik.setDrzimLoptu(false);
                    this.vypisOznam("Stratil loptu " + h.getMeno() + " z " +  this.protivnik.getMenoTimu() + "!");

                } else {

                    this.mojTim.setDrzimLoptu(false);
                    this.protivnik.setDrzimLoptu(true);
                    this.vypisOznam("Stratil loptu " + h.getMeno() + " z " +  this.mojTim.getMenoTimu() + "!");

                }

                break;
            }
        }
    }

    /**
     * Posúva hráčov na ihrisku vyššie podľa ich rýchlosti okrem brankára a hráča, ktorý drží loptu.
     * @param h - hráč, ktorý drží loptu
     * @param tim - tím, ktorého hráči sa posúvajú
     */
    private void posunZvysokHracovVyssie(Hrac h, List<Hrac> tim) {

        for (Hrac hrac : tim) {

            if (!(hrac == h || hrac instanceof Brankar)) {
                if (tim == this.listMojhoTimu) {
                    hrac.posunJKruh(hrac.getRychlost(), 0);
                } else {
                    hrac.posunJKruh(-hrac.getRychlost(), 0);
                }
            }
        }
    }

    /**
     * Zisťujem kto je blízko lopty, používam pytagorovú vetu na výpočet vzdialenosti.
     * A a B sú suradnicaX a suradnicaY, radiusNaDruhu je C na druhú aby som nemusel počítať odmocninu.
     * Ak je vzdialenosť hráča od lopty menšia, ako rádius, tak je hráč v danom rádiuse.
     * @param hrac - hráč, ktorého vzdialenosť od lopty zisťujem
     * @param radius - vzdialenosť, ktorú musí hráč mať od lopty
     * @return - true ak je hráč v danom rádiuse, inak false
     */
    private boolean zistujemKtoJeBlizkoLopty(Hrac hrac, int radius, Object obj) {

        if (obj instanceof Lopta) {

            int suradnicaX = hrac.getJKruhX() - ((Lopta)obj).getX();
            int suradnicaY = hrac.getJKruhY() - ((Lopta)obj).getY();
            int vzdialenostNaDruhu = suradnicaX * suradnicaX + suradnicaY * suradnicaY;
            int radiusNaDruhu = radius * radius;

            return vzdialenostNaDruhu <= radiusNaDruhu;

        } else if (obj instanceof Animacia) {

            int suradnicaX = hrac.getJKruhX() - ((Animacia)obj).getX();
            int suradnicaY = hrac.getJKruhY() - ((Animacia)obj).getY();
            int vzdialenostNaDruhu = suradnicaX * suradnicaX + suradnicaY * suradnicaY;
            int radiusNaDruhu = radius * radius;

            return vzdialenostNaDruhu <= radiusNaDruhu;

        } else {

            return false;

        }
    }

    /**
     * Ak hráč drží loptu, tak má možnosť strieľať ak spĺňa podmienky.
     * @param tim - tím, ktorého hráči majú možnosť strieľať
     * @param drziLoptu - hráč, ktorý drží loptu
     */
    private void moznostStrely(List<Hrac> tim, Hrac drziLoptu) {

        if (tim == this.listProtivnika && drziLoptu.getJKruhX() <= 250) {

            Brankar brankar = (Brankar)this.listMojhoTimu.get(0);
            this.animacia.urobAnimaciu(brankar.getJKruhX(), brankar.getJKruhY());

            if (drziLoptu.getInteligencia() > brankar.getChytanie()) {

                this.animacia.urobAnimaciu(brankar.getJKruhX() - 200, brankar.getJKruhY());
                this.protivnik.pripisGoly(1);
                this.protivnikoveGoly++;
                this.protivnikoveSkore.setText(String.valueOf(this.protivnikoveGoly));
                this.mojTim.inkasovaneGoly(1);
                this.oznamy.zmenPolohu(600, 100);
                this.vypisOznam("Gól!");

            } else {

                this.oznamy.zmenPolohu(500, 100);
                this.vypisOznam("Brankár chytil loptu!");

            }

            this.reset();
            this.mojTim.setDrzimLoptu(true);
            this.protivnik.setDrzimLoptu(false);

        } else if (drziLoptu.getJKruhX() >= 1050 && tim == this.listMojhoTimu) {

            Brankar brankar = (Brankar)this.listProtivnika.get(0);
            this.animacia.urobAnimaciu(brankar.getJKruhX(), brankar.getJKruhY());

            if (drziLoptu.getInteligencia() > brankar.getChytanie()) {

                this.animacia.urobAnimaciu(brankar.getJKruhX() + 200, brankar.getJKruhY());
                this.mojTim.pripisGoly(1);
                this.mojeGoly++;
                this.mojeSkore.setText(String.valueOf(this.mojeGoly));
                this.protivnik.inkasovaneGoly(1);
                this.oznamy.zmenPolohu(600, 100);
                this.vypisOznam("Gól!");

            } else {

                this.oznamy.zmenPolohu(500, 100);
                this.vypisOznam("Brankár chytil loptu!");

            }

            this.reset();
            this.mojTim.setDrzimLoptu(false);
            this.protivnik.setDrzimLoptu(true);
        }
    }

    /**
     * Výber protivníka, ktorý ešte nehral proti môjmu tímu.
     * @return - protivník, ktorý ešte nehral proti môjmu tímu
     */

    private Tim vyberProtivnika() {

        for (Tim t : this.listTimov) {
            if (!t.getTim().equals(this.listMojhoTimu) && t.getHralSomProtiTimu()) {
                t.setHralSomProtiTimu(true);
                return t;
            }
        }

        int maxBody = 0;
        Tim vyherca = null;
        assert false;
        for (Tim t : this.listTimov) {
            if (maxBody < t.getBody()) {
                maxBody = t.getBody();
                vyherca = t;
            } else if (maxBody == t.getBody()) {
                if (t.getGolovyRozdiel() > vyherca.getGolovyRozdiel()) {
                    vyherca = t;
                }
            }
        }

        JOptionPane.showMessageDialog(null, "Všetky tímy už hrali proti vám, ligu vyhral tím " + vyherca.getMenoTimu() + " s " + maxBody + " bodmi.", "Koniec hry", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        return null;
    }

    /**
     * Vráti späť hráčov, loptu na ich pôvodné pozície a začne hru.
     */

    private void reset() {

        this.zoradenieMojichHracovNaIhrisku = new ZoradenieHracovNaIhrisku(this.mojTim, false);
        this.zoradenieMojichHracovNaIhrisku.zoradHracov();

        this.zoradenieProtivnikovNaIhrisku = new ZoradenieHracovNaIhrisku(this.protivnik, true);
        this.zoradenieProtivnikovNaIhrisku.zoradHracov();

        this.animacia.urobAnimaciu(630, 360);

    }
}