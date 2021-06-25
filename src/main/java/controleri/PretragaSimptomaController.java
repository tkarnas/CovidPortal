package controleri;

import Main.BazaPodataka;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Simptom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PretragaSimptomaController implements Initializable {
    @FXML
    private TextField pretragaPoSimptomuTextField;
    @FXML
    private TableView<Simptom> tablicaSimptoma;
    @FXML
    private TableColumn<Simptom, String> nazivSimptomaTableColumn;
    @FXML
    private TableColumn<Simptom, String> vrijednostTableColumn;

    public static File simptomiFile = new File("dat/simptomi.txt");
    public static Set<Simptom> simptomi;

    static {
        try {
            simptomi = BazaPodataka.dohvatiSimptome();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazivSimptomaTableColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("naziv"));
        vrijednostTableColumn.setCellValueFactory(new PropertyValueFactory<Simptom, String>("vrijednost"));

        ObservableList<Simptom> obervableSimptoma = null;
        try {
            obervableSimptoma = FXCollections.observableArrayList(BazaPodataka.dohvatiSimptome());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        tablicaSimptoma.setItems(obervableSimptoma);
    }

    public void pretraziPoNazivuSimptoma() throws SQLException, IOException, InterruptedException {
        String nazivSimptomaZaPretragu;

        if(pretragaPoSimptomuTextField.getText().isEmpty()){
            nazivSimptomaZaPretragu =  pretragaPoSimptomuTextField.getText();
        }
        else{
            nazivSimptomaZaPretragu =  pretragaPoSimptomuTextField.getText();
        }

        List<Simptom> filtriranaListaSimptoma =  BazaPodataka.dohvatiSimptome().stream().filter(s -> s.getNaziv().contains(nazivSimptomaZaPretragu))
                .collect(Collectors.toList());
        tablicaSimptoma.setItems(FXCollections.observableArrayList(filtriranaListaSimptoma));
    }
    /*public static Set<Simptom> ucitavanjeSimptoma(File simptomiFile){
        String linija;
        List<String> listaLiija = new ArrayList<>();
        Set<Simptom> listaSimptoma = new HashSet<>();

        if (simptomiFile.exists()) {
            try (BufferedReader citac = new BufferedReader(new FileReader(simptomiFile)))
            {
                while((linija = citac.readLine()) != null){
                    listaLiija.add(linija);
                }
                for (int i = 0; i< listaLiija.size(); i+=3){
                    Long idSimptoma = Long.parseLong(listaLiija.get(i));
                    String nazivSimptoma = listaLiija.get(i+1);
                    String VrijednostSimmptoma = (listaLiija.get(i+2));

                    Simptom simptom = new Simptom(idSimptoma, nazivSimptoma, VrijednostSimmptoma.toUpperCase());
                    listaSimptoma.add(simptom);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ne postoji");
        }
        return listaSimptoma;
    }*/
}
