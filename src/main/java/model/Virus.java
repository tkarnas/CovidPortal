package model;


import java.util.Set;

/**
 * Sluzi za razvrstavanje virusa od bolesti. Sadrzi sve parametre kao i bolest, te implementira
 * sucelje Zarazno
 *
 * @author Tomislav Karnas
 */
public class Virus extends Bolest implements Zarazno{

    /**
     * Sluzi za inicijalizaciju virusa sa parametrima.
     * @param naziv podatak o nazivu virusa
     * @param simptomi podatak o svim simptomima koji su povezani s virusom
     */
    public Virus(Long id, String naziv, Set<Simptom> simptomi) {
        super(id, naziv, simptomi);
    }

    public Virus() {
    }

    /**
     * Sluzi za prelazak virusa s jedne osobe na drugu ukoliko su one bile u kontaktu.
     * @param osobe dohvaca osobe
     */
    @Override
    public void prelazakZarazeNaOsobu(Osoba osobe) {
        Virus virus = new Virus(super.getId(), super.getNaziv(), super.getSimptomi());
        osobe.setZarazenBolescu(virus);
    }


    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
