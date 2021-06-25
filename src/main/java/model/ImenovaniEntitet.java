package model;

import java.io.Serializable;

/**
 * Sluzi za sve klase koje imaju isto svojstvo "naziv".
 *
 * @author Tomislav Karnas
 */
public abstract class ImenovaniEntitet implements Serializable {

    private Long id;
    private String naziv;

    /**
     * Incijalizira podatak o nazivu
     * @param naziv podatak o nazivu
     * @param id identifikator
     */
    public ImenovaniEntitet(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public ImenovaniEntitet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
