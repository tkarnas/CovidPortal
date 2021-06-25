package model;


import sortiranje.CovidSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Sluzi za kreiranje zupanija i prihvaca parametre klase "ImenovaniEntitet"
 *
 * @author Tomislav Karnas
 */
public class Zupanija extends ImenovaniEntitet{

    private Integer brojStanovnika;
    private Integer brojZarazenih;

    /**
     * Inicijalizira poddatak o nazivu i broju stanovnika zupanije
     * @param naziv podatak o nazivu zupanije
     * @param brojStanovnika podatak o broju stanovnika zupanije
     */
    public Zupanija(Long id, String naziv, Integer brojStanovnika, Integer brojZarazenih) {
        super(id, naziv);
        this.brojStanovnika = brojStanovnika;
        this.brojZarazenih = brojZarazenih;
    }

    public Zupanija() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Zupanija)) return false;
        Zupanija zupanija = (Zupanija) o;
        return getBrojStanovnika().equals(zupanija.getBrojStanovnika()) && getBrojZarazenih().equals(zupanija.getBrojZarazenih());
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrojStanovnika(), getBrojZarazenih());
    }

    public Integer getBrojStanovnika() {
        return brojStanovnika;
    }

    public void setBrojStanovnika(Integer brojStanovnika) {
        this.brojStanovnika = brojStanovnika;
    }

    public Integer getBrojZarazenih() {
        return brojZarazenih;
    }

    public void setBrojZarazenih(Integer brojZarazenih) {
        this.brojZarazenih = brojZarazenih;
    }


}
