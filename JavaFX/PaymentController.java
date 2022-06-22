import java.io.*;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.sound.sampled.*;

/**
 * FXML Controller class
 *
 * @Qian Xuan
 */
public class PaymentController implements Initializable {
    Payment payment = new Payment();
    // Check if the user has clicked 'submit' button (the transactions have been sorted)
    boolean sorted = false;
    
    @FXML
    private ImageView cat;
        
    @FXML
    private Pane enterID;

    @FXML
    private TextField idInput;

    @FXML
    private Button navigation;

    @FXML
    private Button submitPayment;
    
    @FXML
    private Pane summary;

    @FXML
    private GridPane tablePane;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    // 'Navigation' button is clicked
    // Switch to 'Navigation' tab
    @FXML
    void toNavigation(ActionEvent event) throws IOException {
        playButton();
        
        Parent navigationParent = FXMLLoader.load(getClass().getResource("Navigation.fxml"));
        Scene navigationScene = new Scene(navigationParent);
        
        // Gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(navigationScene);
        window.show();
    } 
    
    // 'Histogram' button is clicked
    // Switch to 'Histogram' tab
    @FXML
    void toHistogram(ActionEvent event) throws IOException {
        playButton();
        
        Parent histogramParent = FXMLLoader.load(getClass().getResource("Histogram.fxml"));
        Scene histogramScene = new Scene(histogramParent);
        
        // Gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(histogramScene);
        window.show();
    } 
    
    // 'submit' button is clicked
    @FXML
    void searchTransaction(ActionEvent event) {
        playButton();
        
        // Sort the transactions data in .txt if they're not sorted before
        if (!sorted) {
            payment.sortTransaction("payment0.txt");
            sorted = true;
        }
        
        // Get the tier and waiting time of the transaction ID
        // and the time (second) the transaction is done 
        String tier = payment.getTier(idInput.getText());
        int waitingTime = payment.getWaitingTime(idInput.getText());
        int transSec = payment.getTransSec(idInput.getText());
        
        // Clear the 'tablePane' GridPane and 'summary' Label
        tablePane.getChildren().clear();
        summary.getChildren().clear();
        
        // Display a table of the transaction details
        showTable(waitingTime, transSec, tier);
    } 
    
    void showTable(int waitingSec, int transSec, String tier) {
        // Create a Label 'idHeader' for the 'Transaction ID' row
        Label idHeader = new Label("Transaction ID");
        idHeader.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        idHeader.setStyle("-fx-background-color: #800000;");
        idHeader.setTextFill(Color.web("#ffffff"));
        idHeader.setPadding(new Insets(10));
        idHeader.setAlignment(Pos.CENTER_LEFT);
        idHeader.setMinWidth(140);
        idHeader.setMinHeight(50);
        
        // Create a Label 'waitingHeader' for the 'Waiting Time' row
        Label waitingHeader = new Label("Waiting Time");
        waitingHeader.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        waitingHeader.setStyle("-fx-background-color: #800000;");
        waitingHeader.setTextFill(Color.web("#ffffff"));
        waitingHeader.setPadding(new Insets(10));
        waitingHeader.setAlignment(Pos.CENTER_LEFT);
        waitingHeader.setMinWidth(140);
        waitingHeader.setMinHeight(50);
        
        // Create a Label 'tierHeader' for the 'Tier' row
        Label tierHeader = new Label("Tier");
        tierHeader.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        tierHeader.setStyle("-fx-border-color:#ffd1cc;-fx-border-width: 1 0 1 0;"
                            + "-fx-background-color: #800000;");
        tierHeader.setTextFill(Color.web("#ffffff"));
        tierHeader.setPadding(new Insets(10));
        tierHeader.setAlignment(Pos.CENTER_LEFT);
        tierHeader.setMinWidth(140);
        tierHeader.setMinHeight(50);
        
        // Exceptions
        String id, waitingTime;
        if (idInput.getText().isEmpty()) 
            id = tier = waitingTime = "Error: Please enter your transaction ID";
        
        else if (waitingSec < 0 || transSec < 0) 
            id = waitingTime = tier;
        
        else {
            id = idInput.getText();
            waitingTime = Integer.toString(waitingSec) + " second(s)";
        }
        
        // Create a Label 'transactionID' for the id
        Label transactionID = new Label(id);
        transactionID.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        transactionID.setStyle("-fx-border-color: #800000; -fx-background-color: #ffd1cc;");
        transactionID.setTextFill(Color.web("#000000"));
        transactionID.setAlignment(Pos.CENTER);
        transactionID.setMinWidth(320);
        transactionID.setMinHeight(50);
        
        // Create a Label 'waiting' for the waitingTime
        Label waiting = new Label(waitingTime);
        waiting.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        waiting.setStyle("-fx-border-color:#800000; -fx-background-color: #ffd1cc;");
        waiting.setTextFill(Color.web("#000000"));
        waiting.setAlignment(Pos.CENTER);
        waiting.setMinWidth(320);
        waiting.setMinHeight(50);
        
        // Create a Label 'meowTier' for the tier
        Label meowTier = new Label(tier);
        meowTier.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        meowTier.setStyle("-fx-border-color:#800000;-fx-border-width: 0 1 0 1;"
                          + "-fx-background-color: #ffd1cc;");
        meowTier.setTextFill(Color.web("#000000"));
        meowTier.setAlignment(Pos.CENTER);
        meowTier.setMinWidth(320);
        meowTier.setMinHeight(50);
        
        // Set constraints to place the labels in their respactive cells
        GridPane.setConstraints(idHeader, 0, 0);
        GridPane.setConstraints(transactionID, 1, 0);
        GridPane.setConstraints(tierHeader, 0, 1);
        GridPane.setConstraints(meowTier, 1, 1);
        GridPane.setConstraints(waitingHeader, 0, 2);
        GridPane.setConstraints(waiting, 1, 2);
        // Add all labels into 'tablePane' GridPane
        tablePane.getChildren().addAll(idHeader, tierHeader, waitingHeader, transactionID, meowTier, waiting);
        
        // Create a Label 'sentence' for the summary sentence
        Label sentence = new Label();
        sentence.setFont(Font.font("Cambria", FontWeight.BOLD, 15));
        sentence.setTextFill(Color.web("#800000"));
        
        int transSecCopy = transSec;
        while (transSecCopy > 9)
            transSecCopy %= 10;

        if(transSecCopy == 0)
            sentence.setText("Your transaction was done at the " + (transSec+1) + "st second. ");
        else if (transSecCopy == 1)
            sentence.setText("Your transaction was done at the " + (transSec+1) + "nd second. ");
        else if (transSecCopy == 2)
            sentence.setText("Your transaction was done at the " + (transSec+1) + "rd second. ");
        else
            sentence.setText("Your transaction was done at the " + (transSec+1) + "th second. ");
             
        // Add the 'sentence' Label into 'summary' Pane
        summary.getChildren().add(sentence);
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
