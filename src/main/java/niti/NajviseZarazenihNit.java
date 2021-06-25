package niti;

import Main.BazaPodataka;
import Main.Main;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.Zupanija;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.SortedSet;

public class NajviseZarazenihNit implements Runnable{
    @Override
    public void run() {
        while(true){
            try {
                SortedSet<Zupanija> zupanije = BazaPodataka.dohvatiZupanije();
                Zupanija z = zupanije.first();
                System.out.println(z.getNaziv());
                Thread.sleep(10000);
            } catch (SQLException | IOException | InterruptedException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
