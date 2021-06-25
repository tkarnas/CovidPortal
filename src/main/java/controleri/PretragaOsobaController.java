package controleri;

import Main.BazaPodataka;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public class PretragaOsobaController implements Initializable {

    @FXML
    private TextField pretragaPoOsobamaTextField;
    @FXML
    private TableView<Osoba> tablicaOsoba;
    @FXML
    private TableColumn<Osoba, String> imeOsobeTableColumn;
    @FXML
    private TableColumn<Osoba, String> prezimeOsobeTableColumn;
    @FXML
    private TableColumn<Osoba, Integer> starostOsobeTableColumn;
    @FXML
    private TableColumn<Osoba, Zupanija> zupanijaOsobeTableColumn;
    @FXML
    private TableColumn<Osoba, Bolest> zarazenBolescuTableColumn;
    @FXML
    private TableColumn<Osoba, List<Osoba>> kontaktiraneOsobeTableColumn;

    public static File osobeFile = new File("dat/osobe.txt");
    public static List<Osoba> osobe;

    static {
        try {
            osobe = BazaPodataka.dohvatiOsobe();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imeOsobeTableColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("ime"));
        prezimeOsobeTableColumn.setCellValueFactory(new PropertyValueFactory<Osoba, String>("prezime"));
        starostOsobeTableColumn.setCellValueFactory(cellData -> new ObservableValueBase<>() {
            @Override
            public Integer getValue() {
                LocalDate datumRodjenjaOsobe = cellData.getValue().getDatumRodjenja();
                LocalDate now = LocalDate.now();
                Period vremenskaRazlika = Period.between(datumRodjenjaOsobe, now);
                return vremenskaRazlika.getYears();
            }
        });
        zupanijaOsobeTableColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Zupanija>("zupanija"));
        zarazenBolescuTableColumn.setCellValueFactory(new PropertyValueFactory<Osoba, Bolest>("zarazenBolescu"));
        kontaktiraneOsobeTableColumn.setCellValueFactory(new PropertyValueFactory<Osoba, List<Osoba>>("kontaktiraneOsobe"));

        ObservableList<Osoba> obervableSimptoma = null;
        try {
            obervableSimptoma = FXCollections.observableArrayList(BazaPodataka.dohvatiOsobe());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        tablicaOsoba.setItems(obervableSimptoma);
    }

    public void pretragaPoPrezimenu() throws IOException, SQLException, InterruptedException {
        String prezimevOsobeZaPretragu;

        if(pretragaPoOsobamaTextField.getText().isEmpty()){
            prezimevOsobeZaPretragu = pretragaPoOsobamaTextField.getText();
        }
        else{
            prezimevOsobeZaPretragu =  pretragaPoOsobamaTextField.getText();
        }

        List<Osoba> filtriranaListaOsoba =  BazaPodataka.dohvatiOsobe().stream().filter(o -> o.getPrezime().contains(prezimevOsobeZaPretragu))
                .collect(Collectors.toList());
        tablicaOsoba.setItems(FXCollections.observableArrayList(filtriranaListaOsoba));
    }

    /*public static List<Osoba> ucitavanjeOsoba(File osobeFile, Set<Bolest> bolesti, SortedSet<Zupanija> zupanije, Set<Virus> virusi){
        String linija;
        List<String> listaLiija = new ArrayList<>();
        List<Osoba> listaOsoba = new ArrayList<>();

        if(osobeFile.exists()){
            try(BufferedReader citac = new BufferedReader(new FileReader(osobeFile))){
                while((linija = citac.readLine())  != null){
                    listaLiija.add(linija);
                }

                for(int i = 0; i<listaLiija.size(); i+=6){
                    String ime = listaLiija.get(i);
                    String prezime = listaLiija.get(i+1);
                    Integer starost = Integer.parseInt(listaLiija.get(i+2));
                    String zupanijaString = listaLiija.get(i+3);
                    String zarazenBolescuString = listaLiija.get(i+4);
                    String[] kontaktiraneOsobeString = listaLiija.get(i+5).split(",");

                    Zupanija zupanija = null;
                    for(Zupanija z : zupanije){
                        if(z.getId().toString().equals(zupanijaString)){
                            zupanija = z;
                        }
                    }
                    Bolest zarazenBolescu = null;
                    for(Bolest b : bolesti){
                        if(b.getId().toString().equals(zarazenBolescuString)){
                            zarazenBolescu = b;
                        }
                    }
                    for (Virus v : virusi){
                        if(v.getId().toString().equals(zarazenBolescuString)){
                            zarazenBolescu = v;
                        }
                    }

                    List<Osoba> kontaktiraneOsobe = new ArrayList<>();
                    for(String s : kontaktiraneOsobeString){
                        for (Osoba osoba : listaOsoba) {
                            if (s.equals(osoba.toString())) {
                                kontaktiraneOsobe.add(osoba);
                            }
                        }
                    }

                    Osoba osoba = new Osoba.Builder(ime, prezime)
                            .isOld(starost)
                            .liveIn(zupanija)
                            .suffersFrom(zarazenBolescu)
                            .hasContactWith(kontaktiraneOsobe)
                            .build();
                    listaOsoba.add(osoba);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File ne postoji!");
        }
        return listaOsoba;
    }*/
}
