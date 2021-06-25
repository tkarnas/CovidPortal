package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Klasa Osoba sadrzi jedan konstruktor.
 *
 * @author Josip Roso
 * @version 3.0
 */
public class Osoba  implements Serializable {
    private Long id;
    private String ime, prezime;
    private LocalDate datumRodjenja;
    private Zupanija zupanija;
    private Bolest zarazenBolescu;
    private List<Osoba> kontaktiraneOsobe;


    /**
     * Konstruktor se poziva pri kreiranju nove osobe koja se kreira u buid metodi podklase Builder i ne prima ulazne parametre.
     *
     */
    private Osoba(){}

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Zupanija getZupanija() {
        return zupanija;
    }

    public void setZupanija(Zupanija zupanija) {
        this.zupanija = zupanija;
    }

    public Bolest getZarazenBolescu() {
        return zarazenBolescu;
    }

    public void setZarazenBolescu(Bolest zarazenBolescu) {
        this.zarazenBolescu = zarazenBolescu;
    }

    public List<Osoba> getKontaktiraneOsobe() {
        return kontaktiraneOsobe;
    }

    public void setKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
        this.kontaktiraneOsobe = kontaktiraneOsobe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Osoba osoba = (Osoba) o;
        return Objects.equals(id, osoba.id) && Objects.equals(ime, osoba.ime) && Objects.equals(prezime, osoba.prezime) && Objects.equals(datumRodjenja, osoba.datumRodjenja) && Objects.equals(zupanija, osoba.zupanija) && Objects.equals(zarazenBolescu, osoba.zarazenBolescu) && Objects.equals(kontaktiraneOsobe, osoba.kontaktiraneOsobe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ime, prezime, datumRodjenja, zupanija, zarazenBolescu, kontaktiraneOsobe);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    /**
     * Klasa Osoba sadrzi jedan konstruktor i nasljeduje klasu Osoba.
     *
     */
    public static class Builder {
        private Long id;
        private String ime, prezime;
        private LocalDate datumRodjenja;
        private Zupanija zupanija;
        private Bolest zarazenBolescu;
        private List<Osoba> kontaktiraneOsobe;

        public Builder() {
        }

        /**
         * Konstruktor se poziva pri kreiranju nove osobe i prima dva parametra.
         *
         * @param ime tipa String oznacuje ime osobe
         * @param prezime tipa String oznacuje prezime osobe
         */
        public Builder(String ime, String prezime) {
            this.ime = ime;
            this.prezime = prezime;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo id
         *
         * @param id tipa Long oznacuje id osobe
         * @return vraca objekt osobe sa dodanim svojstvom id
         */
        public Builder saId(Long id) {
            this.id = id;
            return this;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo ime
         *
         * @param ime tipa String oznacuje ime osobe
         * @return vraca objekt osobe sa dodanim svojstvom ime
         */
        public Builder saImenom(String ime) {
            this.ime = ime;
            return this;
        }
        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo prezime
         *
         * @param prezime tipa String oznacuje prezime osobe
         * @return vraca objekt osobe sa dodanim svojstvom prezime
         */
        public Builder saPrezimenom(String prezime) {
            this.prezime = prezime;
            return this;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo datumRodjenja
         *
         * @param datumRodjenja tipa LocalDate oznacuje datum rodjenja osobe
         * @return vraca objekt osobe sa dodanim svojstvom datumRodjenja
         */
        public Builder saDatumomRodjenja(LocalDate datumRodjenja) {
            this.datumRodjenja = datumRodjenja;
            return this;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo zupanija
         *
         * @param zupanija tipa Zupanija oznacuje zupaniju prebivalista osobe
         * @return vraca objekt osobe sa dodanim svojstvom zupanija
         */
        public Builder saZupanijom(Zupanija zupanija) {
            this.zupanija = zupanija;
            return this;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo zarazenBolescu
         *
         * @param zarazenBolescu tipa Bolest oznacuje bolest s kojom je osoba zarazena
         * @return vraca objekt osobe sa dodanim svojstvom zarazenBolescu
         */
        public Builder saZarazenBolescu(Bolest zarazenBolescu) {
            this.zarazenBolescu = zarazenBolescu;
            return this;
        }

        /**
         * Metoda se poziva da se objektu osobe dodijeli svojstvo kontaktiraneOsobe
         *
         * @param kontaktiraneOsobe tipa Osoba[] oznacuje osobe s kojima je zarazena osoba bila u kontaktu
         * @return vraca objekt osobe sa dodanim svojstvom kontaktiraneOsobe
         */
        public Builder saKontaktiraneOsobe(List<Osoba> kontaktiraneOsobe) {
            this.kontaktiraneOsobe = kontaktiraneOsobe;
            return this;
        }

        /**
         * Metoda se poziva da bi kreirala objekt osobe sa svim dodatnim podatcima koji su
         * dodani na pocetni objket kreiran preko konstruktora.
         *
         * @return vraca objekt osobe
         */
        public Osoba build(){
            Osoba osoba = new Osoba();
            osoba.id = this.id;
            osoba.ime = this.ime;
            osoba.prezime = this.prezime;
            osoba.datumRodjenja = this.datumRodjenja;
            osoba.zupanija = this.zupanija;
            osoba.zarazenBolescu = this.zarazenBolescu;
            osoba.kontaktiraneOsobe = this.kontaktiraneOsobe;
            if (zarazenBolescu instanceof Virus) {
                // koristim stari nacin jer mi nije htjelo izgenerirati javaDoc s novim nacinom
                Virus virus = (Virus)zarazenBolescu;
                if (kontaktiraneOsobe.size() > 0) {
                    for (int i = 0; i < kontaktiraneOsobe.size(); i++){
                        virus.prelazakZarazeNaOsobu(kontaktiraneOsobe.get(i));
                    }
                }
            }

            return osoba;
        }
    }
}