package controleri;

import Main.BazaPodataka;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Simptom;


import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Set;


public class DodavanjeSimptomaController implements Initializable {

    @FXML
    private TextField nazivSimptomaTextField;
    @FXML
    private TextField vrijednostSimptomaTextField;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void spremanjeSimptoma() throws SQLException, IOException, InterruptedException {
            Set<Simptom> listaSimptoma = BazaPodataka.dohvatiSimptome();
            Long idSimptoma = (long) listaSimptoma.size() + 1;
        try {
            String nazivSimptoma = nazivSimptomaTextField.getText();
            String vrijednostSimptoma = vrijednostSimptomaTextField.getText();
            /*for(Simptom s : listaSimptoma){
                if(!vrijednostSimptomaTextField.getText().equals(s.getVrijednost())) {
                    throw new RuntimeException();
                }
            }*/
            Simptom simptom = new Simptom(idSimptoma, nazivSimptoma, vrijednostSimptoma);
            BazaPodataka.spremiSimptom(simptom);
            /*listaSimptoma.add(simptom);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(PretragaSimptomaController.simptomiFile));
                FileWriter writer = new FileWriter(PretragaSimptomaController.simptomiFile);
                BufferedWriter output = new BufferedWriter(writer);

                for (Simptom zapis : listaSimptoma) {
                    output.write(zapis.getId() + "\n");
                    output.write(zapis.getNaziv() + "\n");
                    output.write(zapis.getVrijednost() + "\n");
                }
                output.close();*/
                nazivSimptomaTextField.clear();
                vrijednostSimptomaTextField.clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Simptomi su uspješno spremljeni");
                alert.setHeaderText("Podaci o simptomu su uspješno spremljeni u datoteku.");
                alert.showAndWait();
            /*} catch (IOException e) {
                e.printStackTrace();
            }*/
        }catch(RuntimeException | IOException e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Simptom nije spremljen");
            alert.setHeaderText("Niste upisali dobre podatke");
            alert.showAndWait();
        }
    }
}
