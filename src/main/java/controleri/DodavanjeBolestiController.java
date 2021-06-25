package controleri;

import Main.BazaPodataka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Bolest;
import model.Simptom;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class DodavanjeBolestiController implements Initializable {

    @FXML
    private TextField nazivBolestiTextField;
    @FXML
    private CheckBox kasaljCheckBox;
    @FXML
    private CheckBox glavoboljaCheckBox;
    @FXML
    private CheckBox temperaturaCheckBox;
    @FXML
    private CheckBox nosCheckBox;
    @FXML
    private CheckBox grloboljaCheckBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void spremanjeBolesti() throws IOException, SQLException, InterruptedException {
        Set<Bolest> listaBolesti = BazaPodataka.dohvatiBolesti();
        Long idBolesti = (long) listaBolesti.size() + 1;
        try{
            String nazivBolesti = nazivBolestiTextField.getText();
            Set<Simptom> listaSimptoma = new HashSet<>(Collections.emptySet());

            if(nosCheckBox.isSelected()){
                listaSimptoma.add(PretragaSimptomaController.simptomi.stream().filter(s-> s.getNaziv().equals("Curenje nosa")).findFirst().get());
            }
            if(glavoboljaCheckBox.isSelected()){
                listaSimptoma.add(PretragaSimptomaController.simptomi.stream().filter(s-> s.getNaziv().equals("Glavobolja")).findFirst().get());
            }
            if(temperaturaCheckBox.isSelected()){
                listaSimptoma.add(PretragaSimptomaController.simptomi.stream().filter(s-> s.getNaziv().equals("Temperatura")).findFirst().get());
            }
            if(grloboljaCheckBox.isSelected()){
                listaSimptoma.add(PretragaSimptomaController.simptomi.stream().filter(s-> s.getNaziv().equals("Grlobolja")).findFirst().get());
            }
            if(kasaljCheckBox.isSelected()){
                listaSimptoma.add(PretragaSimptomaController.simptomi.stream().filter(s-> s.getNaziv().equals("Kašalj")).findFirst().get());
            }
            Bolest bolest = new Bolest(idBolesti, nazivBolesti, listaSimptoma);
            BazaPodataka.spremiBolest(bolest);
            /* listaBolesti.add(bolest);

            try {
                BufferedReader reader = new BufferedReader(new FileReader(PretragaBolestiController.bolestiFile));
                FileWriter writer = new FileWriter(PretragaBolestiController.bolestiFile);
                BufferedWriter output = new BufferedWriter(writer);

                for (Bolest zapis : listaBolesti) {
                    output.write(zapis.getId() + "\n");
                    output.write(zapis.getNaziv() + "\n");
                    for(Simptom s : zapis.getSimptomi()){
                        output.write(s.getId() + ",");
                    }
                    output.newLine();*/
                    nazivBolestiTextField.clear();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bolest je uspješno spremljena");
                    alert.setHeaderText("Podaci o bolesti su uspješno spremljeni u datoteku.");
                    alert.showAndWait();
              //  }
           /*     output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        catch(RuntimeException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Bolest nije spremljena");
            alert.setHeaderText("Niste upisali dobre podatke");
            alert.showAndWait();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }
}
