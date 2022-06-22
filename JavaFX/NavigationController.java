import java.io.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.*;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.sound.sampled.*;

/**
 * FXML Controller class
 *
 * @Qian Xuan
 */
public class NavigationController implements Initializable {
    Navigation nav = new Navigation();
    // Check if the user has clicked the 'search' button
    boolean searched = false;
    
    @FXML
    private ComboBox<String> destination;

    @FXML
    private ComboBox<String> source;
    
    @FXML
    private VBox route;
    
    @FXML
    private Pane gif;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nav.createGraph("navigation0.txt");
        ArrayList<String> loc = nav.getVertex();
        
        // Cast the ArrayList of locations into ObservableList for ComboBox
        ObservableList<String> locations = FXCollections.observableArrayList(loc);
        source.setItems(locations);
        destination.setItems(locations);
        
        // Set default value for 'source' and 'destination' ComboBox
        source.getSelectionModel().selectFirst();
        destination.getSelectionModel().selectFirst();
    }    
    
    // 'search' button is clicked
    @FXML
    void searchPath(ActionEvent event) {
        // Clean the 'route' VBox and 'gif' pane if there is another route searched previously
        if (searched) {
            route.getChildren().clear();
            gif.getChildren().clear();
            searched = false;
        }
        // Show the route in 'route' VBox
        showShortestPath(source.getValue(), destination.getValue()); 
        // Display the mrt.gif in 'gif' pane
        showMRT();
    }
    
    // 'Payment' button is clicked
    // Switch to 'Payment' tab
    @FXML
    void toPayment(ActionEvent event) throws IOException {
        playButton();
        Parent paymentParent = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        Scene paymentScene = new Scene(paymentParent);
        
        // Gets the Stage information
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(paymentScene);
        stage.show();
    } 
    
    // 'Histogram' button is clicked
    // Switch to 'Histogram' tab
    @FXML
    void toHistogram(ActionEvent event) throws IOException {
        playButton();
        Parent histogramParent = FXMLLoader.load(getClass().getResource("Histogram.fxml"));
        Scene histogramScene = new Scene(histogramParent);
        
        // Gets the Stage information
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(histogramScene);
        stage.show();
    } 
    
    void showShortestPath(String source, String destination) {
        playButton();
        
        // Set spacing betwen each node in 'route' VBox
        route.setSpacing(20);
        
        // Call shortestPath() in Navigation class to get path info
        ArrayList<String> path = nav.shortestPath(source, destination);
        
        // ArrayList to store BorderPane for each station
        ArrayList<BorderPane> panes = new ArrayList<>();
                
        // Call showStations() to get BorderPane for each station along the path
        // and add the BorderPane into 'panes' ArrayList
        for (int i = path.size()-1; i >= 0; i--) {
            String station = path.get(i);
            panes.add(showStations(station));
        }
        
        int pass, lastRowSize = 0;
        
        // Calculate the number of rows
        // and check if the number of stations is a multiple of 4
        if (path.size() % 4 != 0) {
            pass = path.size()/4;
            lastRowSize = path.size()%4;
        }
        else {
            pass = path.size()/4 - 1;
            lastRowSize = 4;
        }
       
        // For the rows of stations before the last row
        for (int i = 0; i < pass; i++) {
            // Create new HBox for each row
            HBox currentRow = new HBox();
            currentRow.setSpacing(13);
            // Add 4 station's BorderPane into current HBox (row)
            for (int j = 0; j < 4; j++) {
                currentRow.getChildren().add(panes.get(i*4 + j));
                // Add arrow.png after each station
                currentRow.getChildren().add(showArrow());
            }
            // Add the current HBox (row) to the 'route' VBox
            route.getChildren().add(currentRow);
        }
       
        // For the last row of stations
        HBox lastRow = new HBox();        
        lastRow.setSpacing(13);
        for (int i = 0; i < lastRowSize; i++) {
            lastRow.getChildren().add(panes.get(pass*4 + i));
            // Add arrow.png after each station if it's not the last station
            if (i < lastRowSize-1)
                lastRow.getChildren().add(showArrow());
        }
        // Add the last HBox (row) to the 'route' VBox
        route.getChildren().add(lastRow);
        
        searched = true;
    }
    
    // Create and return BorderPane for each of the station
    BorderPane showStations(String station) {
        BorderPane pane = new BorderPane();
        // Create ImageView for the MRT.png and set its size
        ImageView mrt = new ImageView(new Image("resources/MRT.png"));
        mrt.setFitWidth(35);
        mrt.setFitHeight(50);
        
        // Create Label to display the name of the station
        Label loc = new Label(station);
        loc.setFont(Font.font("Cambria", 15));
        loc.setTextFill(Color.web("#000000"));
        loc.setWrapText(true);
        loc.setMaxWidth(200);
        
        // Add ImageView and Label to the BorderPane
        pane.setCenter(mrt);
        pane.setBottom(loc);

        return pane;
    }
    
    // Create and return BorderPane to display arrow between stations
    BorderPane showArrow() {
        BorderPane pane = new BorderPane();
        // Create ImageView for the Arrow.png and set its size
        ImageView arrow = new ImageView(new Image("resources/Arrow.png"));
        arrow.setFitWidth(33);
        arrow.setFitHeight(13);
        // Add ImageView to the BorderPane
        pane.setCenter(arrow);
        return pane;
    }
    
    // Create Pane to display mrtGoing.gif
    void showMRT() {
        Pane pane = new Pane();
        // Create ImageView for the mrtGoing.gif and set its size
        ImageView train = new ImageView(new Image("resources/mrtGoing.gif"));
        train.setFitWidth(500);
        train.setFitHeight(100);
        // Add ImageView to the Pane
        pane.getChildren().add(train);
        gif.getChildren().add(pane);
    }
    
    // Play the sound when butto is clicked
    void playButton() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/button.wav").getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        }catch(IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Error with playing sound.");
        }  
    }
}
