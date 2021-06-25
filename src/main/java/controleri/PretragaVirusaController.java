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
import model.Virus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class PretragaVirusaController  implements Initializable {
    @FXML
    private TextField pretragaPoVirusaTextField;
    @FXML
    private TableView<Virus> tablicaVirusa;
    @FXML
    private TableColumn<Virus, String> nazivVirusaTableColumn;
    @FXML
    private TableColumn<Virus, Simptom> simptomiTableColumn;

    public static File virusiFile = new File("dat/virusi.txt");
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
        nazivVirusaTableColumn.setCellValueFactory(new PropertyValueFactory<Virus, String>("naziv"));
        simptomiTableColumn.setCellValueFactory(new PropertyValueFactory<Virus, Simptom>("simptomi"));

        ObservableList<Virus> obervableVirusi = null;
        try {
            obervableVirusi = FXCollections.observableArrayList(BazaPodataka.dohvatiViruse());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }

        tablicaVirusa.setItems(obervableVirusi);
    }
    public void pretraziPoNazivuVirusa() throws IOException, SQLException, InterruptedException {
        String nazivVirusaZaPretragu;

        if(pretragaPoVirusaTextField.getText().isEmpty()){
            nazivVirusaZaPretragu = pretragaPoVirusaTextField.getText();
        }
        else{
            nazivVirusaZaPretragu =  pretragaPoVirusaTextField.getText();
        }

        List<Virus> filtriranaListaVirusa =  BazaPodataka.dohvatiViruse().stream().filter(s -> s.getNaziv().contains(nazivVirusaZaPretragu))
                .collect(Collectors.toList());
        tablicaVirusa.setItems(FXCollections.observableArrayList(filtriranaListaVirusa));
    }

    /*public static Set<Virus> ucitavanjeVirusa(File virusiFile, Set<Simptom> simptomi) {
        String linija;
        List<String> listaLiija = new ArrayList<>();
        Set<Virus> listaVirusa = new HashSet<>();


        //citanjke iz virusi.txt
        if (virusiFile.exists()) {
            try (BufferedReader citac = new BufferedReader(new FileReader(virusiFile)))
            {
                while((linija = citac.readLine()) != null){
                    listaLiija.add(linija);
                }
                for (int i = 0; i< listaLiija.size(); i+=3){
                    Long idBolesti = Long.parseLong(listaLiija.get(i));
                    String nazivBolesti = listaLiija.get(i+1);
                    String[] poljeSimptoma = (listaLiija.get(i+2).split(","));
                    Set<Simptom> setSimptoma = new HashSet<>();
                    for (String s : poljeSimptoma){
                        for (Simptom simptom : simptomi) {
                            if (s.equals(simptom.getId().toString())) {
                                setSimptoma.add(simptom);
                            }
                        }
                    }

                    Virus virus = new Virus(idBolesti, nazivBolesti, setSimptoma);
                    listaVirusa.add(virus);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("ne postoji");
        }
        return listaVirusa;
    }*/
}
