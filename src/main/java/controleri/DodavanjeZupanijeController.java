package controleri;

import Main.BazaPodataka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Zupanija;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class DodavanjeZupanijeController implements Initializable {

    @FXML
    private TextField nazivZupanijeTextField;
    @FXML
    private TextField brojStanovnikaTextField;
    @FXML
    private TextField brojZarazenihTextField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void spremanjeZupanije() throws SQLException, IOException, InterruptedException {
        SortedSet<Zupanija> listaZupanija = BazaPodataka.dohvatiZupanije();
        Long idZupanije = (long) listaZupanija.size() + 1;
        try{
            String nazivZupanije = nazivZupanijeTextField.getText();
            Integer brojStanovnika = Integer.parseInt(brojStanovnikaTextField.getText());
            Integer brojZarazenih = Integer.parseInt(brojZarazenihTextField.getText());

            Zupanija zupanija = new Zupanija(idZupanije, nazivZupanije,brojStanovnika,brojZarazenih);
            BazaPodataka.spremiZupaniju(zupanija);
           /* try {
                BufferedReader reader = new BufferedReader(new FileReader(PretragaZupanijaController.zupanijeFile));
                FileWriter writer = new FileWriter(PretragaZupanijaController.zupanijeFile);
                BufferedWriter output = new BufferedWriter(writer);

                for (Zupanija zapis : listaZupanija) {
                    output.write(zapis.getId() + "\n");
                    output.write(zapis.getNaziv() + "\n");
                    output.write(zapis.getBrojStanovnika() + "\n");
                    output.write(zapis.getBrojZarazenih()+ "\n");
                }
                output.close();*/
                nazivZupanijeTextField.clear();
                brojStanovnikaTextField.clear();
                brojZarazenihTextField.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Županija je uspješno spremljena");
                alert.setHeaderText("Podaci o županiji su uspješno spremljeni u datoteku.");
                alert.showAndWait();
/*
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }catch(RuntimeException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Županija nije spremljena");
            alert.setHeaderText("Niste upisali dobre podatke");
            alert.showAndWait();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
