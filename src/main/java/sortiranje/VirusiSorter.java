package sortiranje;

import model.Virus;

import java.util.Comparator;

/**
 * Sluzi za sortiranje virusa po nazivu od Z-A
 */

public class VirusiSorter implements Comparator<Virus> {

    @Override
    public int compare(Virus o1, Virus o2) {
        return o2.getNaziv().compareTo(o1.getNaziv());
    }
}
