package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Sluzi za kreiranje bolesti za zadanim parametrima koja prihvaca parametre klase ImenovaniEntitet
 *
 * @author Tomislav Karnas
 */
public class Bolest extends ImenovaniEntitet {


    private Set<Simptom> simptomi;

    /**
     * Inicijalizira podatak o nazivu bolesti i njezinim simptomima
     * @param naziv podatak o nazivu bolesti
     * @param simptomi podatak o simptomima
     */
    public Bolest(Long id,String naziv, Set<Simptom> simptomi) {
        super(id,naziv);
        this.simptomi = simptomi;
    }

    public Bolest() {
    }

    public Set<Simptom> getSimptomi() {
        return simptomi;
    }

    public void setSimptomi(Set<Simptom> simptomi) {
        this.simptomi = simptomi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bolest bolest = (Bolest) o;
        return Objects.equals(getId(), bolest.getId()) && Objects.equals(getNaziv(), bolest.getNaziv()) && simptomi.equals(bolest.simptomi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(simptomi);
    }

    @Override
    public String toString() {
        return "" + getNaziv();
    }


}
