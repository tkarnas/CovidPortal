package enumeracija;

/**
 * Sluzi sa konstante vrijednosti simptoma.
 */
public enum VrijednostiSimptoma {
    RIJETKO("RIJETKO"),
    SREDNJE("SREDNJE"),
    CESTO("ČESTO"),
    PRODUKTIVNI("PRODUKTIVNI"),
    INTEZIVNO("INTEZIVNO"),
    VISOKA("VISOKA"),
    JAKA("JAKA");

    private String vrijednost;

    VrijednostiSimptoma(String vrijednost) {
        this.vrijednost = vrijednost;
    }

    public String getVrijednost() {
        return vrijednost;
    }
}
