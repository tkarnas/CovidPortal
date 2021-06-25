package controleri;

import Main.Main;
import Main.BazaPodataka;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;
import model.Zupanija;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.SortedSet;

public class PocetniEkranController implements Initializable {

    @FXML
    public void prikaziEkranZaPretraguZupanija() throws IOException {
        Parent pretragaZupanijaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaZupanija.fxml")));
        Scene pretragaZupanijaScene = new Scene(pretragaZupanijaFrame, 800, 600);
        Main.getMainStage().setScene(pretragaZupanijaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguSimptoma() throws IOException {
        Parent pretragaSimptomaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaSimptoma.fxml")));
        Scene pretragaSimptomaScene = new Scene(pretragaSimptomaFrame, 800, 600);
        Main.getMainStage().setScene(pretragaSimptomaScene);
    }
    @FXML
    public void prikaziEkranZaPretraguBolesti() throws IOException {
        Parent pretragaBolestiFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaBolesti.fxml")));
        Scene pretragaBolestiScene = new Scene(pretragaBolestiFrame, 800, 600);
        Main.getMainStage().setScene(pretragaBolestiScene);
    }
    @FXML
    public void prikaziEkranZaPretraguVirusa() throws IOException {
        Parent pretragaVirusaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaVirusa.fxml")));
        Scene pretragaVirusaScene = new Scene(pretragaVirusaFrame, 800, 600);
        Main.getMainStage().setScene(pretragaVirusaScene);
    }
    @FXML
    public void pretraziPoPrezimenuOsobe() throws IOException {
        Parent pretragaOsobeFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "pretragaOsoba.fxml")));
        Scene pretragaOsobeScene = new Scene(pretragaOsobeFrame, 800, 600);
        Main.getMainStage().setScene(pretragaOsobeScene);
    }
    @FXML
    public void dodavanjeZupanije() throws IOException {
        Parent dodavanjeZupanijeFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeZupanije.fxml")));
        Scene dodavanjeZupanijeScene = new Scene(dodavanjeZupanijeFrame, 800, 600);
        Main.getMainStage().setScene(dodavanjeZupanijeScene);
    }
    @FXML
    public void dodavanjeSimptoma() throws IOException {
        Parent dodavanjeSimptomaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeSimptoma.fxml")));
        Scene dodavanjeSimptomaScene = new Scene(dodavanjeSimptomaFrame, 800, 600);
        Main.getMainStage().setScene(dodavanjeSimptomaScene);
    }
    @FXML
    public void dodavanjeBolesti() throws IOException {
        Parent dodavanjeBolestiFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeBolesti.fxml")));
        Scene dodavanjeBolestiScene = new Scene(dodavanjeBolestiFrame, 800, 600);
        Main.getMainStage().setScene(dodavanjeBolestiScene);
    }
    @FXML
    public void dodavanjeVirusa() throws IOException {
        Parent dodavanjeVirusaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeVirusa.fxml")));
        Scene dodavanjeVirusaScene = new Scene(dodavanjeVirusaFrame, 800, 600);
        Main.getMainStage().setScene(dodavanjeVirusaScene);
    }
    @FXML
    public void dodavanjeOsoba() throws IOException {
        Parent dodavanjeOsobaFrame =
                FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "dodavanjeOsoba.fxml")));
        Scene dodavanjeOsobaScene = new Scene(dodavanjeOsobaFrame, 800, 600);
        Main.getMainStage().setScene(dodavanjeOsobaScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
