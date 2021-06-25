package Main;

import model.*;
import sortiranje.CovidSorter;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BazaPodataka {

    private static final String DATABASE_FILE = "dat/bazaPodataka.properties";
    public static boolean aktivnaVezaSBazomPodataka = false;

    public synchronized Connection spajanjeNaBazu() throws SQLException, IOException, InterruptedException {

        Properties svojstva = new Properties();
        svojstva.load(new FileReader(DATABASE_FILE));
        String urlBazePodataka = svojstva.getProperty("url");
        String korisnickoIme = svojstva.getProperty("username");
        String lozinka = svojstva.getProperty("password");
        aktivnaVezaSBazomPodataka = true;
        wait();


        return DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
    }

    public void zatvoriVezuBaze(Connection vezaBaze) throws SQLException {
        vezaBaze.close();
        aktivnaVezaSBazomPodataka = false;
        notifyAll();
    }

    public static synchronized SortedSet<Zupanija> dohvatiZupanije() throws SQLException, IOException, InterruptedException {
        SortedSet<Zupanija> zupanije = new TreeSet<>(new CovidSorter());

        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        Statement upit = veza.createStatement();

        ResultSet rezultati = upit.executeQuery("SELECT * FROM ZUPANIJA");

        while (rezultati.next()){
            Long id = rezultati.getLong("ID");
            String naziv = rezultati.getString("NAZIV");
            Integer brojStanovnika = rezultati.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenih = rezultati.getInt("BROJ_ZARAZENIH_STANOVNIKA");

            Zupanija zupanija = new Zupanija(id, naziv, brojStanovnika, brojZarazenih);
            zupanije.add(zupanija);
        }

        baza.zatvoriVezuBaze(veza);
        return zupanije;
    }

    public static Zupanija dohvatJedneZupanije(Long idZupanije) throws SQLException, IOException, InterruptedException {
        Zupanija zupanija = null;

        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM ZUPANIJA WHERE ID = ?");
        upit.setLong(1, idZupanije);

        ResultSet rezultati = upit.executeQuery();

        while (rezultati.next()){
            String naziv = rezultati.getString("NAZIV");
            Integer brojStanovnika = rezultati.getInt("BROJ_STANOVNIKA");
            Integer brojZarazenih = rezultati.getInt("BROJ_ZARAZENIH_STANOVNIKA");

            zupanija = new Zupanija(idZupanije, naziv, brojStanovnika, brojZarazenih);
        }
        baza.zatvoriVezuBaze(veza);
        return zupanija;
    }

    public static void spremiZupaniju(Zupanija zupanija) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("INSERT INTO ZUPANIJA(NAZIV, BROJ_STANOVNIKA, BROJ_ZARAZENIH_STANOVNIKA) VALUES(?, ?, ?)");
        upit.setString(1, zupanija.getNaziv());
        upit.setInt(2, zupanija.getBrojStanovnika());
        upit.setInt(3, zupanija.getBrojZarazenih());
        upit.executeUpdate();
        baza.zatvoriVezuBaze(veza);
    }

    public static Set<Simptom> dohvatiSimptome() throws SQLException, IOException, InterruptedException {
        Set<Simptom> simptomi = new HashSet<>();
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        Statement upit = veza.createStatement();

        ResultSet rezultati = upit.executeQuery("SELECT * FROM SIMPTOM");

        while (rezultati.next()){
            Long id = rezultati.getLong("ID");
            String naziv = rezultati.getString("NAZIV");
            String vrijednost = rezultati.getString("VRIJEDNOST");

            Simptom simtpom = new Simptom(id, naziv, vrijednost);
            simptomi.add(simtpom);
        }

        baza.zatvoriVezuBaze(veza);
        return simptomi;
    }

    public static Simptom dohvatJednogSimptoma(Long idSimptoma) throws SQLException, IOException, InterruptedException {
        Simptom simptom = null;
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM SIMPTOM WHERE ID = ?");
        upit.setLong(1, idSimptoma);

        ResultSet rezultati = upit.executeQuery();

        while (rezultati.next()){
            String naziv = rezultati.getString("NAZIV");
            String vrijednost = rezultati.getString("VRIJEDNOST");
            simptom = new Simptom(idSimptoma, naziv, vrijednost);
        }
        baza.zatvoriVezuBaze(veza);
        return simptom;
    }

    public static void spremiSimptom(Simptom simptom) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("INSERT INTO SIMPTOM(NAZIV, VRIJEDNOST) VALUES(?, ?)");
        upit.setString(1, simptom.getNaziv());
        upit.setString(2, simptom.getVrijednost());
        upit.executeUpdate();
        baza.zatvoriVezuBaze(veza);
    }

    public static Set<Bolest> dohvatiBolesti() throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Set<Bolest> bolesti = new HashSet<>();
        Connection veza = baza.spajanjeNaBazu();
        Statement upit = veza.createStatement();
        ResultSet rezultati = upit.executeQuery("SELECT * FROM BOLEST WHERE VIRUS = FALSE");
        while (rezultati.next()){
            Long id = rezultati.getLong("ID");
            String naziv = rezultati.getString("NAZIV");
            Set<Simptom> simptomi = new HashSet<>();
            List<Long> indexiSimptoma = dohvatIdSimptomaBolesti(id);
            for(Long idSimptoma : indexiSimptoma){
                Simptom simptom = dohvatJednogSimptoma(idSimptoma);
                simptomi.add(simptom);
            }
            Bolest bolest = new Bolest(id, naziv, simptomi);
            bolesti.add(bolest);
        }

        baza.zatvoriVezuBaze(veza);
        return bolesti;
    }

    public static Bolest dohvatJedneBolesti(Long idBolesti) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?");
        upit.setLong(1, idBolesti);
        ResultSet rezultati = upit.executeQuery();
        Bolest bolest = new Bolest();
        while (rezultati.next()){
            String naziv = rezultati.getString("NAZIV");
            Set<Simptom> simptomi = new HashSet<>();
            List<Long> indexiSimptoma = dohvatIdSimptomaBolesti(idBolesti);
            for(Long idSimptoma : indexiSimptoma){
                Simptom simptom = dohvatJednogSimptoma(idSimptoma);
                simptomi.add(simptom);
            }
            bolest.setId(idBolesti);
            bolest.setNaziv(naziv);
            bolest.setSimptomi(simptomi);
        }
        baza.zatvoriVezuBaze(veza);
        return bolest;
    }

    public static List<Long> dohvatIdSimptomaVirusa(Long idBolesti) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM BOLEST_SIMPTOM WHERE BOLEST_ID = ?");
        upit.setLong(1, idBolesti);
        ResultSet rezultati = upit.executeQuery();
        List<Long> indexiSimptoma = new ArrayList<>();
        while (rezultati.next()){
            Long simptom_id = rezultati.getLong("SIMPTOM_ID");
            indexiSimptoma.add(simptom_id);
        }
        baza.zatvoriVezuBaze(veza);
        return indexiSimptoma;
    }

    public static void spremiBolest(Bolest bolest) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
        Long bolestId = null;
        upit.setString(1, bolest.getNaziv());
        upit.setBoolean(2, false);
        upit.executeUpdate();
        try (ResultSet generatedKeys = upit.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                bolestId = generatedKeys.getLong(1);
            }
        }
        List<Long> indexiSimptoma = new ArrayList<>();
        Set<Simptom> simptomi = bolest.getSimptomi();
        for(Simptom simptom : simptomi){
            indexiSimptoma.add(simptom.getId());
        }
        for(Long idSimptoma : indexiSimptoma){
            PreparedStatement upit2 = veza.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES(?, ?)");
            upit2.setLong(1, bolestId);
            upit2.setLong(2, idSimptoma);
            upit2.executeUpdate();
        }
        baza.zatvoriVezuBaze(veza);
    }
    public static Set<Virus> dohvatiViruse() throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Set<Virus> virusi = new HashSet<>();
        Connection veza = baza.spajanjeNaBazu();
        Statement upit = veza.createStatement();
        ResultSet rezultati = upit.executeQuery("SELECT * FROM BOLEST WHERE VIRUS = TRUE");
        while (rezultati.next()){
            Long id = rezultati.getLong("ID");
            String naziv = rezultati.getString("NAZIV");
            Set<Simptom> simptomi = new HashSet<>();
            List<Long> indexiSimptoma = dohvatIdSimptomaBolesti(id);
            for(Long idSimptoma : indexiSimptoma){
                Simptom simptom = dohvatJednogSimptoma(idSimptoma);
                simptomi.add(simptom);
            }
            Virus virus = new Virus(id, naziv, simptomi);
            virusi.add(virus);
        }

        baza.zatvoriVezuBaze(veza);
        return virusi;
    }
    public static Virus dohvatJednogVirusa(Long idVirusa) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM BOLEST WHERE ID = ?");
        upit.setLong(1, idVirusa);
        ResultSet rezultati = upit.executeQuery();
        Virus virus = null;
        while (rezultati.next()){
            String naziv = rezultati.getString("NAZIV");
            Set<Simptom> simptomi = new HashSet<>();
            List<Long> indexiSimptoma = dohvatIdSimptomaVirusa(idVirusa);
            for(Long idSimptoma : indexiSimptoma){
                Simptom simptom = dohvatJednogSimptoma(idSimptoma);
                simptomi.add(simptom);
            }
            virus.setId(idVirusa);
            virus.setNaziv(naziv);
            virus.setSimptomi(simptomi);
        }
        baza.zatvoriVezuBaze(veza);
        return virus;
    }

    public static List<Long> dohvatIdSimptomaBolesti(Long idBolesti) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM BOLEST_SIMPTOM WHERE BOLEST_ID = ?");
        upit.setLong(1, idBolesti);
        ResultSet rezultati = upit.executeQuery();
        List<Long> indexiSimptoma = new ArrayList<>();
        while (rezultati.next()){
            Long simptom_id = rezultati.getLong("SIMPTOM_ID");
            indexiSimptoma.add(simptom_id);
        }
        baza.zatvoriVezuBaze(veza);
        return indexiSimptoma;
    }

    public static void spremiVirus(Virus virus) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("INSERT INTO BOLEST(NAZIV, VIRUS) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS);
        Long virusId = null;
        upit.setString(1, virus.getNaziv());
        upit.setBoolean(2, true);
        upit.executeUpdate();
        try (ResultSet generatedKeys = upit.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                virusId = generatedKeys.getLong(1);
            }
        }
        List<Long> indexiSimptoma = new ArrayList<>();
        Set<Simptom> simptomi = virus.getSimptomi();
        for(Simptom simptom : simptomi){
            indexiSimptoma.add(simptom.getId());
        }
        for(Long idSimptoma : indexiSimptoma){
            PreparedStatement upit2 = veza.prepareStatement("INSERT INTO BOLEST_SIMPTOM(BOLEST_ID, SIMPTOM_ID) VALUES(?, ?)");
            upit2.setLong(1, virusId);
            upit2.setLong(2, idSimptoma);
            upit2.executeUpdate();
        }
        baza.zatvoriVezuBaze(veza);
    }

    public static List<Osoba> dohvatiOsobe() throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        List<Osoba> osobe = new ArrayList<>();
        Connection veza = baza.spajanjeNaBazu();
        Statement upit = veza.createStatement();
        ResultSet rezultati = upit.executeQuery("SELECT * FROM OSOBA");
        while (rezultati.next()){
            Long id = rezultati.getLong("ID");
            String ime = rezultati.getString("IME");
            String prezime = rezultati.getString("PREZIME");
            Date dadtum_rodenja = rezultati.getDate("DATUM_RODJENJA");
            Long zupanija_id = rezultati.getLong("ZUPANIJA_ID");
            Long bolest_id = rezultati.getLong("BOLEST_ID");
            Zupanija zupanijaOsobe = BazaPodataka.dohvatJedneZupanije(zupanija_id);
            Bolest bolestOsobe = BazaPodataka.dohvatJedneBolesti(bolest_id);

            LocalDate datumRodenja = dadtum_rodenja.toLocalDate();


            List<Osoba> kontkatiraneOsobe = new ArrayList<>();
            List<Long> indexiKontakata = dohvatIdKontaktiraneOsobe(id);
            for(Long idKontakta : indexiKontakata){
                Osoba osoba = dohvatJedneOsobe(idKontakta);
                kontkatiraneOsobe.add(osoba);
            }
            Osoba osoba = new Osoba.Builder()
                    .saId(id)
                    .saImenom(ime)
                    .saPrezimenom(prezime)
                    .saDatumomRodjenja(datumRodenja)
                    .saZupanijom(zupanijaOsobe)
                    .saZarazenBolescu(bolestOsobe)
                    .saKontaktiraneOsobe(kontkatiraneOsobe)
                    .build();


            osobe.add(osoba);
        }

        baza.zatvoriVezuBaze(veza);
        return osobe;
    }

    public static List<Long> dohvatIdKontaktiraneOsobe(Long idOsobe) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();
        PreparedStatement upit = veza.prepareStatement("SELECT * FROM KONTAKTIRANE_OSOBE WHERE OSOBA_ID = ?");
        upit.setLong(1, idOsobe);
        ResultSet rezultati = upit.executeQuery();
        List<Long> indexiKontakata = new ArrayList<>();
        while (rezultati.next()){
            Long kontakt_id = rezultati.getLong("KONTAKTIRANA_OSOBA_ID");
            indexiKontakata.add(kontakt_id);
        }
        baza.zatvoriVezuBaze(veza);
        return indexiKontakata;
    }

    public static List<Osoba> dohvatiSveOsobe() throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        List<Osoba> osobe = new ArrayList<>();
        Connection veza = baza.spajanjeNaBazu();

        Statement upit = veza.createStatement();
        ResultSet rezultati = upit.executeQuery("SELECT * FROM OSOBA");

        while (rezultati.next()) {
            Long id = rezultati.getLong("ID");
            String ime = rezultati.getString("IME");
            String prezime = rezultati.getString("PREZIME");
            LocalDate datumRodenja = rezultati.getDate("DATUM_RODJENJA").toLocalDate();

            Long idZupanije = rezultati.getLong("ZUPANIJA_ID");
            Zupanija zupanija = dohvatJedneZupanije(idZupanije);

            Long idBolesti = rezultati.getLong("BOLEST_ID");
            Bolest zarazenBolescu = dohvatJedneBolesti(idBolesti);

            List<Osoba> kontaktiraneOsobe = new ArrayList<>();
            List<Long> indexiKontaktiranihOsoba = dohvatIdKontaktiraneOsobe(id);

            for (Long idOsobe : indexiKontaktiranihOsoba) {
                Osoba osoba = dohvatJedneOsobeBezKontakata(idOsobe);

                kontaktiraneOsobe.add(osoba);
            }

            Osoba osoba = new Osoba.Builder()
                    .saId(id)
                    .saImenom(ime)
                    .saPrezimenom(prezime)
                    .saDatumomRodjenja(datumRodenja)
                    .saZupanijom(zupanija)
                    .saZarazenBolescu(zarazenBolescu)
                    .saKontaktiraneOsobe(kontaktiraneOsobe)
                    .build();

            osobe.add(osoba);
        }

        baza.zatvoriVezuBaze(veza);

        return osobe;
    }

    public static Osoba dohvatJedneOsobe(Long idOsobe) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM OSOBA WHERE ID = ?");
        upit.setLong(1, idOsobe);

        ResultSet rezultati = upit.executeQuery();
        Osoba osoba = new Osoba.Builder()
                .build();

        while (rezultati.next()) {
            Long id = rezultati.getLong("ID");
            String ime = rezultati.getString("IME");
            String prezime = rezultati.getString("PREZIME");
            LocalDate datumRodenja = rezultati.getDate("DATUM_RODJENJA").toLocalDate();

            Long idZupanije = rezultati.getLong("ZUPANIJA_ID");
            Zupanija zupanija = dohvatJedneZupanije(idZupanije);

            Long idBolesti = rezultati.getLong("BOLEST_ID");
            Bolest zarazenBolescu = dohvatJedneBolesti(idBolesti);

            List<Osoba> kontaktiraneOsobe = new ArrayList<>();
            List<Long> indexiKontaktiranihOsoba = dohvatIdKontaktiraneOsobe(id);

            for (Long idKontaktiraneOsobe : indexiKontaktiranihOsoba) {
                Osoba kontaktOsoba = dohvatJedneOsobeBezKontakata(idKontaktiraneOsobe);

                kontaktiraneOsobe.add(kontaktOsoba);
            }

            osoba.setId(idOsobe);
            osoba.setIme(ime);
            osoba.setPrezime(prezime);
            osoba.setDatumRodjenja(datumRodenja);
            osoba.setZupanija(zupanija);
            osoba.setZarazenBolescu(zarazenBolescu);
            osoba.setKontaktiraneOsobe(kontaktiraneOsobe);
        }

        baza.zatvoriVezuBaze(veza);
        return osoba;
    }

    public static Osoba dohvatJedneOsobeBezKontakata(Long idOsobe) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        PreparedStatement upit = veza.prepareStatement("SELECT * FROM OSOBA WHERE ID = ?");
        upit.setLong(1, idOsobe);

        ResultSet rezultati = upit.executeQuery();
        Osoba osoba = new Osoba.Builder()
                .build();

        while (rezultati.next()) {
            Long id = rezultati.getLong("ID");
            String ime = rezultati.getString("IME");
            String prezime = rezultati.getString("PREZIME");
            LocalDate datumRodenja = rezultati.getDate("DATUM_RODJENJA").toLocalDate();

            Long idZupanije = rezultati.getLong("ZUPANIJA_ID");
            Zupanija zupanija = dohvatJedneZupanije(idZupanije);

            Long idBolesti = rezultati.getLong("BOLEST_ID");
            Bolest zarazenBolescu = dohvatJedneBolesti(idBolesti);

            osoba.setId(id);
            osoba.setIme(ime);
            osoba.setPrezime(prezime);
            osoba.setDatumRodjenja( datumRodenja);
            osoba.setZupanija(zupanija);
            osoba.setZarazenBolescu(zarazenBolescu);
        }

        baza.zatvoriVezuBaze(veza);
        return osoba;
    }

    public static void spremiJednuOsobu(Osoba osoba) throws SQLException, IOException, InterruptedException {
        BazaPodataka baza = new BazaPodataka();
        Connection veza = baza.spajanjeNaBazu();

        PreparedStatement upit = veza.prepareStatement("INSERT INTO OSOBA(IME, PREZIME, DATUM_RODJENJA, ZUPANIJA_ID, BOLEST_ID) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        Long osobaId = null;
        upit.setString(1, osoba.getIme());
        upit.setString(2, osoba.getPrezime());

        Date datumRodjenja = Date.valueOf(osoba.getDatumRodjenja());
        upit.setDate(3, datumRodjenja);

        upit.setLong(4, osoba.getZupanija().getId());
        upit.setLong(5, osoba.getZarazenBolescu().getId());

        upit.executeUpdate();

        try (ResultSet generatedKeys = upit.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                osobaId = generatedKeys.getLong(1);
            }
        }

        List<Long> indexiKontaktiranihOsoba = new ArrayList<>();
        List<Osoba> kontaktiraneOsobe = osoba.getKontaktiraneOsobe();
        for (Osoba kontaktOsoba : kontaktiraneOsobe) {
            indexiKontaktiranihOsoba.add(kontaktOsoba.getId());
        }

        for (Long idKontaktOsobe : indexiKontaktiranihOsoba) {
            PreparedStatement upit2 = veza.prepareStatement("INSERT INTO KONTAKTIRANE_OSOBE(OSOBA_ID, KONTAKTIRANA_OSOBA_ID) VALUES(?, ?)");
            upit2.setLong(1, osobaId);
            upit2.setLong(2, idKontaktOsobe);

            upit2.executeUpdate();
        }
        baza.zatvoriVezuBaze(veza);
    }
}
