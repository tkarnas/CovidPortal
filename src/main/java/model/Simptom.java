package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Služi za kreiranje simptoma koji prihvaća parametre klase ImenovaniEntitet
 *
 * @author Tomislav Karnas
 */
public class Simptom extends ImenovaniEntitet{


    private String vrijednost;


    public Simptom() {
    }

    /**
     * Sluzi za inicijalizaciju podatka simptoma s parametrima
     * @param naziv podatak o nazivu simptoma
     * @param vrijednost podatak o jakosti/ucestalosti simptoma
     */


    public Simptom(Long id, String naziv, String vrijednost) {
        super(id, naziv);
        this.vrijednost = vrijednost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Simptom)) return false;
        Simptom simptom = (Simptom) o;
        return Objects.equals(vrijednost, simptom.vrijednost) && Objects.equals(getNaziv(), simptom.getNaziv());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVrijednost());
    }

    public String getVrijednost() {
        return vrijednost;
    }

    public void setVrijednost(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    @Override
    public String toString() {
        return super.getNaziv() +
                " - " + vrijednost;
    }
}
