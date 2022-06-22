import java.io.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javax.sound.sampled.*;

/**
 *
 * @Qian Xuan
 */
public class MeowsLikeTrains extends Application {
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        Scene scene = new Scene(root, 800, 513);

        stage.setTitle("Meows Like Trains");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        playBGM();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    public void playBGM() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/MRT.wav").getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Error with playing sound.");
        }  
    }
    
}
