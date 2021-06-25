package genericsi;



import model.Osoba;
import model.Virus;

import java.util.ArrayList;
import java.util.List;

/**
 * Genericka klasa sluzi za filtriranje virusa po osobama
 * @param <T> generik koji koristi virus klasu
 * @param <S> generik koji koristi osoba klasu
 */

public class KlinikaZaInfektivneBolesti<T extends Virus, S extends Osoba> {

       private List<T> listaVirusa;
       private List<S> listaOsoba;

    public KlinikaZaInfektivneBolesti(List<T> listaVirusa, List<S> listaOsoba) {
            this.listaVirusa = listaVirusa;
            this.listaOsoba = listaOsoba;
    }

    @Override
    public String toString() {
        return "KlinikaZaInfektivneBolesti{" +
                "listaVirusa=" + listaVirusa +
                ", listaOsoba=" + listaOsoba +
                '}';
    }

    public List<T> getListaVirusa() {
        return listaVirusa;
    }

    public void setListaVirusa(List<T> listaVirusa) {
        this.listaVirusa = listaVirusa;
    }

    public List<S> getListaOsoba() {
        return listaOsoba;
    }

    public void setListaOsoba(List<S> listaOsoba) {
        this.listaOsoba = listaOsoba;
    }
}


