import java.io.*;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javax.sound.sampled.*;

/**
 * FXML Controller class
 *
 * @Qian Xuan && Wubang && Jinseng
 */
public class HistogramController implements Initializable {
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
    
    @FXML
    private TextField binInput;

    @FXML
    private TextField histInput;

    @FXML
    private Button navigation;

    @FXML
    private TextField size;

    @FXML
    private Button submit;
    
    @FXML
    private VBox histPane;

    @FXML
    void submitHist(ActionEvent event) {
        playButton();
        // Clear the 'histPane' Pane 
        histPane.getChildren().clear();
        displayHistogram();
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {}
    
    // 'Payment' button is clicked
    // Switch to 'Payment' tab
    @FXML
    void toPayment(ActionEvent event) throws IOException {
        playButton();
        
        Parent paymentParent = FXMLLoader.load(getClass().getResource("Payment.fxml"));
        Scene paymentScene = new Scene(paymentParent);
        
        // Gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        
        window.setScene(paymentScene);
        window.show();
    } 
    
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
    
    // Plot and show the histogram
    private void displayHistogram(){
        playButton();
        
        int histogramSize, bin, temp;
    
        // If there isn't any input
        if (size.getText().isEmpty() || binInput.getText().isEmpty() || histInput.getText().isEmpty()) {
            // Create ImageView for the sleepyCat.gif and set its size
            ImageView cat = new ImageView(new Image("resources/sleepyCat.gif"));
            
            Label sentence = new Label("Please fill in all required fields.");
            sentence.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
            sentence.setTextFill(Color.web("#000000"));

            // Add the 'sentence' Label into 'histPane' VBox
            histPane.setAlignment(Pos.CENTER);
            histPane.getChildren().addAll(sentence, cat);
        }
        
        else {
            try {
                histogramSize = Integer.parseInt(size.getText());
                bin = Integer.parseInt(binInput.getText());

                String[] hists = histInput.getText().split(" ");
                int[] histArr = new int[hists.length];
                int[] cutoffs = new int[Integer.parseInt(binInput.getText())+1];
                int[] counts = new int[Integer.parseInt(binInput.getText())];

                for (int y = 0; y < hists.length;y++)
                    histArr[y] = Integer.parseInt(hists[y]);

                // Initialise counts
                for(int i=0; i<bin; i++)
                    counts[i] = 0;

                // Sort array
                Arrays.sort(histArr);

                // Find required data
                int min = histArr[0];
                int max = histArr[histogramSize-1];
                int increment = (max - min) / bin;
                temp = min;
                int counter = 0;

                // Find cutoffs
                while (temp <= max) {
                    cutoffs[counter] = temp;
                    temp += increment;
                    counter++;
                }

                temp = 1;
                int nextBin = cutoffs[temp];
                counter = 0;

                // Find counts
                for (int j = 0; j < histogramSize; j++) {
                    if (histArr[j] < nextBin)
                        counter++;
                    else {
                        temp++;
                        if (temp == cutoffs.length) {
                            counts[temp-2] = ++counter;
                            break;
                        }

                        counts[temp-2] = counter;
                        counter = 1;
                        nextBin = cutoffs[temp];
                    }
                }

                barChart.setCategoryGap(0);
                barChart.setBarGap(0);
                xAxis.setLabel("Number of Passengers at 5-minute Intervals");
                xAxis.tickLabelFontProperty().set(Font.font("Cambria", 13));
                yAxis.setLabel("Number of Data Point(s)");
                yAxis.tickLabelFontProperty().set(Font.font("Cambria", 13));
                
                XYChart.Series series = new XYChart.Series();
                series.setName("Counts");
                for (int i =0; i<bin; i++)
                    series.getData().add(new XYChart.Data(cutoffs[i]+" - "+cutoffs[i+1], counts[i]));

                barChart.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                barChart.getData().addAll(series);
                histPane.getChildren().add(barChart);
            } catch (Exception e) {
                // Create ImageView for the sleepyCat.gif and set its size
                ImageView cat = new ImageView(new Image("resources/sleepyCat.gif"));

                Label sentence = new Label("Error: The values are invalid");
                sentence.setFont(Font.font("Cambria", FontWeight.BOLD, 16));
                sentence.setTextFill(Color.web("#000000"));

                // Add the 'sentence' Label into 'histPane' Vbox
                histPane.setAlignment(Pos.CENTER);
                histPane.getChildren().addAll(sentence, cat);
            }
        }
    }
    
    // Play the sound when butto is clicked
    void playButton() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("resources/button.wav").getAbsoluteFile( ));
            Clip clip = AudioSystem.getClip( );
            clip.open(audioInputStream);
            clip.start( );
        } catch(IOException | LineUnavailableException | UnsupportedAudioFileException ex) {
            System.out.println("Error with playing sound.");
        }  
    }
}