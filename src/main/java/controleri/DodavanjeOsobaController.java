package controleri;

import Main.BazaPodataka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class DodavanjeOsobaController implements Initializable {

    @FXML
    private TextField imeOsobeTextField;
    @FXML
    private TextField prezimeOsobeTextField;
    @FXML
    private DatePicker datumRodjenjaDatePicker;
    @FXML
    private ComboBox zupanijaOsobeComboBox;
    @FXML
    private ComboBox zarazenBolescuComboBox;
    @FXML
    private CheckBox pericaCheckBox;
    @FXML
    private CheckBox marijaCheckBox;
    @FXML
    private CheckBox mladenCheckBox;
    @FXML
    private CheckBox ivanaCheckBox;
    @FXML
    private CheckBox markoCheckBox;

    public static File osobeFile = PretragaOsobaController.osobeFile;
    public static Set<Bolest> bolesti;

    static {
        try {
            bolesti = BazaPodataka.dohvatiBolesti();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    public static SortedSet<Zupanija> zupanije;

    static {
        try {
            zupanije = BazaPodataka.dohvatiZupanije();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    public static Set<Virus> virusi;

    static {
        try {
            virusi = BazaPodataka.dohvatiViruse();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        zupanijaOsobeComboBox.getItems().addAll(zupanije);
        zarazenBolescuComboBox.getItems().addAll(bolesti);
        zarazenBolescuComboBox.getItems().addAll(virusi);
    }

    public void spremanjeOsobe(){
        try{
            List<Osoba> listaOsoba = BazaPodataka.dohvatiSveOsobe();
            Long idOsobe = (long) (listaOsoba.size() + 1);
            String imeOsobe = imeOsobeTextField.getText();
            String prezimeOsobe = prezimeOsobeTextField.getText();
            LocalDate datumRodjenja = LocalDate.of(datumRodjenjaDatePicker.getValue().getYear(), datumRodjenjaDatePicker.getValue().getMonth(), datumRodjenjaDatePicker.getValue().getDayOfMonth());
            List<Osoba> listakontakata = new ArrayList<>(Collections.emptySet());
            Zupanija zupanija = (Zupanija) zupanijaOsobeComboBox.getSelectionModel().getSelectedItem();
            Bolest zarazenBolescu = (Bolest) zarazenBolescuComboBox.getSelectionModel().getSelectedItem();

            if (pericaCheckBox.isSelected()) {
                listakontakata.add(PretragaOsobaController.osobe.stream().filter(s -> s.getIme().equals("Perica") && s.getPrezime().equals("Perić")).findFirst().get());
            }
            if (marijaCheckBox.isSelected()) {
                listakontakata.add(PretragaOsobaController.osobe.stream().filter(s -> s.getIme().equals("Marija") && s.getPrezime().equals("Horvat")).findFirst().get());
            }
            if (mladenCheckBox.isSelected()) {
                listakontakata.add(PretragaOsobaController.osobe.stream().filter(s -> s.getIme().equals("Mladen") && s.getPrezime().equals("Barić")).findFirst().get());
            }
            if (ivanaCheckBox.isSelected()) {
                listakontakata.add(PretragaOsobaController.osobe.stream().filter(s -> s.getIme().equals("Ivana") && s.getPrezime().equals("Milić")).findFirst().get());
            }
            if (markoCheckBox.isSelected()) {
                listakontakata.add(PretragaOsobaController.osobe.stream().filter(s -> s.getIme().equals("Marko") && s.getPrezime().equals("Kovačević")).findFirst().get());
            }

            Osoba osoba = new Osoba.Builder()
                    .saId(idOsobe)
                    .saImenom(imeOsobe)
                    .saPrezimenom(prezimeOsobe)
                    .saDatumomRodjenja(datumRodjenja)
                    .saZupanijom(zupanija)
                    .saZarazenBolescu(zarazenBolescu)
                    .saKontaktiraneOsobe(listakontakata)
                    .build();

            BazaPodataka.spremiJednuOsobu(osoba);

           /* listaOsoba.add(osoba);

            try {
                FileWriter writer = new FileWriter(PretragaOsobaController.osobeFile);
                BufferedWriter output = new BufferedWriter(writer);

                for (Osoba zapis : listaOsoba) {
                    output.write(zapis.getIme() + "\n");
                    output.write(zapis.getPrezime() + "\n");
                    output.write(zapis.getStarost() + "\n");
                    output.write(zapis.getZupanija().getId() + "\n");
                    output.write(zapis.getZarazenBolescu().getId() + "\n");
                    for(Osoba o : zapis.getKontaktiraneOsobe()){
                        output.write(o.getIme() + " " + o.getPrezime() + ",");
                    }
                    output.newLine();
                    imeOsobeTextField.clear();
                    prezimeOsobeTextField.clear();
                    starostTextField.clear();*/
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Osoba je uspješno spremljena");
                    alert.setHeaderText("Podaci o osobi su uspješno spremljeni u datoteku.");
                    alert.showAndWait();
               /* }
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }catch(RuntimeException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Osoba nije spremljena");
            alert.setHeaderText("Niste upisali dobre podatke");
            alert.showAndWait();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

    }
}
