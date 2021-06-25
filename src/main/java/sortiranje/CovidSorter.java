package sortiranje;

import model.Zupanija;

import java.util.Comparator;


/**
 * Sluzi za komparaciju zupanija po postotku zarazenosti
 */
public class CovidSorter implements Comparator<Zupanija> {

    @Override
    public int compare(Zupanija z1, Zupanija z2) {
        double postotak1 = (double) z1.getBrojZarazenih() / (double)z1.getBrojStanovnika() * 100;
        double postotak2 = (double) z2.getBrojZarazenih() / (double)z2.getBrojStanovnika() * 100;

        if(postotak1 < postotak2){
            return 1;
        }
        else {
            return -1;
        }
    }
}
