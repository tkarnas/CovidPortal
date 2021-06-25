package model;

/**
 * Sucelje koje sluzi za prelazak zarazue s osobe na osobu
 *
 * @author Tomislav Karnas
 */
public interface Zarazno {

     /**
      * Sluzi za povezivanje zaraze s osobe na osobu
      * @param osobe
      */
     void prelazakZarazeNaOsobu(Osoba osobe);

}
