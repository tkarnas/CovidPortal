package controleri;

import Main.BazaPodataka;
import Main.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Zupanija;
import niti.NajviseZarazenihNit;
import sortiranje.CovidSorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PretragaZupanijaController implements Initializable {



    @FXML
    private TextField pretragaPoZupanijiTextField;
    @FXML
    private TableView<Zupanija> tablicaZupanija;
    @FXML
    private TableColumn<Zupanija, String> nazivZupanijeTableColumn;
    @FXML
    private TableColumn<Zupanija, Integer> brojStanovnikaTableColumn;
    @FXML
    private TableColumn<Zupanija, Integer> brojZarazenihTableColumn;

    public static File zupanijeFile = new File("dat/zupanije.txt");
    public static SortedSet<Zupanija> zupanije;

    static {
        try {
            zupanije = BazaPodataka.dohvatiZupanije();
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nazivZupanijeTableColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, String>("naziv"));
        brojStanovnikaTableColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojStanovnika"));
        brojZarazenihTableColumn.setCellValueFactory(new PropertyValueFactory<Zupanija, Integer>("brojZarazenih"));


        ObservableList<Zupanija> obervableZupanije = null;
        try {
            obervableZupanije = FXCollections.observableArrayList(BazaPodataka.dohvatiZupanije());
        } catch (SQLException | IOException | InterruptedException throwables) {
            throwables.printStackTrace();
        }
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        executorService.execute(new NajviseZarazenihNit());

        tablicaZupanija.setItems(obervableZupanije);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), e -> {
                    SortedSet<Zupanija> zupanije = null;
                    try {
                        zupanije = BazaPodataka.dohvatiZupanije();
                    } catch (SQLException | IOException | InterruptedException throwables) {
                        throwables.printStackTrace();
                    }
                    Zupanija zupanija = zupanije.first();
                    Main.getMainStage().setTitle(zupanija.getNaziv());
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }



    public void pretraziPoNazivuZupanije() throws SQLException, IOException, InterruptedException {
        String nazivZupanijeZaPretragu = pretragaPoZupanijiTextField.getText();

        List<Zupanija> filtriranaListaZupanija = BazaPodataka.dohvatiZupanije().stream().filter(z -> z.getNaziv().contains(nazivZupanijeZaPretragu))
                .collect(Collectors.toList());
        tablicaZupanija.setItems(FXCollections.observableArrayList(filtriranaListaZupanija));
    }

    /*public static SortedSet<Zupanija> ucitavanjeZupanija(File zupanijeFile){
        String linija;
        List<String> listaLinija = new ArrayList<>();
        SortedSet<Zupanija> listaZupanija = new TreeSet<>(new CovidSorter());

        if (zupanijeFile.exists()) {
            try (BufferedReader citac = new BufferedReader(new FileReader(zupanijeFile)))
            {
                while((linija = citac.readLine()) != null){
                    listaLinija.add(linija);
                }
                for (int i = 0; i< listaLinija.size(); i+=4){
                    Long idZupanije = Long.parseLong(listaLinija.get(i));
                    String nazivZupanije = listaLinija.get(i+1);
                    Integer brojStanovnika = Integer.parseInt(listaLinija.get(i+2));
                    Integer brojZarazenih = Integer.parseInt(listaLinija.get(i+3));

                    Zupanija zupanija = new Zupanija(idZupanije, nazivZupanije, brojStanovnika, brojZarazenih);
                    listaZupanija.add(zupanija);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("ne postoji");
        }
        return listaZupanija;
    }*/
}




