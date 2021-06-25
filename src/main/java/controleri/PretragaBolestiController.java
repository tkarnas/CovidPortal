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
import model.Bolest;
import model.Simptom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PretragaBolestiController implements Initializable {

    @FXML
    private TextField pretragaPoBolestiTextField;
    @FXML
    private TableView<Bolest> tablicaBolesti;
    @FXML
    private TableColumn<Bolest, String> nazivBolestiTableColumn;
    @FXML
    private TableColumn<Bolest, Simptom> simptomiTableColumn;

    public static File bolestiFile = new File("dat/bolesti.txt");
    public static Set<Bolest> bolesti;

    static {
        try {
            bolesti = BazaPodataka.dohvatiBolesti();
        } catch (SQLException | InterruptedException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nazivBolestiTableColumn.setCellValueFactory(new PropertyValueFactory<Bolest, String>("naziv"));
        simptomiTableColumn.setCellValueFactory(new PropertyValueFactory<Bolest, Simptom>("simptomi"));


        ObservableList<Bolest> obervableBolesti = null;
        try {
            obervableBolesti = FXCollections.observableArrayList(BazaPodataka.dohvatiBolesti());
        } catch (SQLException | InterruptedException | IOException throwables) {
            throwables.printStackTrace();
        }

        tablicaBolesti.setItems(obervableBolesti);
    }
    public void pretraziPoNazivuBolesti() throws IOException, SQLException, InterruptedException {
        String nazivBolestiZaPretragu;

        if(pretragaPoBolestiTextField.getText().isEmpty()){
            nazivBolestiZaPretragu = pretragaPoBolestiTextField.getText();
        }
        else{
            nazivBolestiZaPretragu =  pretragaPoBolestiTextField.getText();
        }

        List<Bolest> filtriranaListaBolesti =  BazaPodataka.dohvatiBolesti().stream().filter(s -> s.getNaziv().contains(nazivBolestiZaPretragu))
                .collect(Collectors.toList());
        tablicaBolesti.setItems(FXCollections.observableArrayList(filtriranaListaBolesti));
    }

    /*public static Set<Bolest> ucitavanjeBolesti(File bolestiFile, Set<Simptom> simptomi) {
        String linija;
        List<String> listaLiija = new ArrayList<>();
        Set<Bolest> listaBolesti = new HashSet<>();

        // citanje iz bolesti.txt
        if (bolestiFile.exists()) {
            try (BufferedReader citac = new BufferedReader(new FileReader(bolestiFile))) {
                while ((linija = citac.readLine()) != null) {
                    listaLiija.add(linija);
                }
                for (int i = 0; i < listaLiija.size(); i += 3) {
                    Long idBolesti = Long.parseLong(listaLiija.get(i));
                    String nazivBolesti = listaLiija.get(i + 1);
                    String[] poljeSimptoma = (listaLiija.get(i + 2).split(","));
                    Set<Simptom> setSimptoma = new HashSet<>();
                    for (String s : poljeSimptoma) {
                        for (Simptom simptom : simptomi) {
                            if (s.equals(simptom.getId().toString())) {
                                setSimptoma.add(simptom);
                            }
                        }
                    }
                    Bolest bolest = new Bolest(idBolesti, nazivBolesti, setSimptoma);
                    listaBolesti.add(bolest);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ne postoji");
        }
        return listaBolesti;
    }*/
}
