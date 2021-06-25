package controleri;

import Main.BazaPodataka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import model.Bolest;
import model.Simptom;
import model.Virus;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;


public class DodavanjeVirusaController implements Initializable {

    @FXML
    private TextField nazivVirusaTextField;
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

    public void spremanjeVirusa() throws IOException, SQLException, InterruptedException {
        Set<Virus> listaVirusa = BazaPodataka.dohvatiViruse();
        Long idVirusa = (long) listaVirusa.size() + 5;
        try{
            String nazivVirusa = nazivVirusaTextField.getText();
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


            Virus virus = new Virus(idVirusa, nazivVirusa, listaSimptoma);
            BazaPodataka.spremiVirus(virus);
           /* listaVirusa.add(virus);

            try {
                FileWriter writer = new FileWriter(PretragaVirusaController.virusiFile);
                BufferedWriter output = new BufferedWriter(writer);

                for (Virus zapis : listaVirusa) {
                    output.write(zapis.getId() + "\n");
                    output.write(zapis.getNaziv() + "\n");
                    for(Simptom s : zapis.getSimptomi()){
                        output.write(s.getId() + ",");
                    }
                    output.newLine();
                }
                output.close();
                nazivVirusaTextField.clear();*/
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Virus je uspješno spremljen");
                alert.setHeaderText("Podaci o virusu su uspješno spremljeni u datoteku.");
                alert.showAndWait();
            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }catch(RuntimeException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Virus nije spremljen");
            alert.setHeaderText("Niste upisali dobre podatke");
            alert.showAndWait();
        }


    }
}
