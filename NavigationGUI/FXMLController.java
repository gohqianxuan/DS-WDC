import javafx.event.ActionEvent;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class FXMLController implements Initializable {
    
    Navigation nav = new Navigation();
    boolean searched = false;
    
    // Select source and destination
    @FXML
    private ComboBox<String> destination;

    @FXML
    private ComboBox<String> source;
    
    @FXML
    private VBox stops;
    
    @FXML
    private Pane gif;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nav = nav.createGraph("case0.txt");
        ArrayList<String> loc = nav.getVertex();
        
        // cast the ArrayList of locations into ObservableList
        ObservableList<String> locations = FXCollections.observableArrayList(loc);
        source.setItems(locations);
        destination.setItems(locations);
        
        // set default value for source and destination combobox
        source.getSelectionModel().selectFirst();
        destination.getSelectionModel().selectFirst();
    }    
    
    @FXML
    void searchPath(ActionEvent event) {
        if (searched)
            stops.getChildren().clear();
        showShortestPath(source.getValue(), destination.getValue()); 
        showTrain();
    }
    
    void showShortestPath(String source, String destination) {
        ArrayList<String> path = nav.shortestPath(source, destination);
        ArrayList<BorderPane> panes = new ArrayList<>();
        ArrayList<HBox> rows = new ArrayList<>();
        stops.setSpacing(20);
                
        for (int i = path.size()-1; i >= 0; i--) {
            String location = path.get(i);
            panes.add(showStops(location));
        }
        
        int pass, lastRowSize = 0;
        if (path.size() % 4 != 0) {
            pass = path.size()/4;
            lastRowSize = path.size()%4;
        }
        else {
            pass= path.size()/4 - 1;
            lastRowSize = 4;
        }
        
        for (int i = 0; i < pass; i++) {
            rows.add(new HBox());
            HBox currentRow = rows.get(i);
            currentRow.setSpacing(13);
            
            for (int j = 0; j < 4; j++) {
                currentRow.getChildren().add(panes.get(i*4 + j));
                currentRow.getChildren().add(showArrow());
            }
            stops.getChildren().add(currentRow);
        }
       
        rows.add(new HBox());
        HBox lastRow = rows.get(rows.size()-1);
        lastRow.setSpacing(13);
        for (int i = 0; i < lastRowSize; i++) {
            lastRow.getChildren().add(panes.get(pass*4 + i));
            if (i < lastRowSize-1)
                lastRow.getChildren().add(showArrow());
        }
        stops.getChildren().add(lastRow);
        
        searched = true;
        
//        AnchorPane an = new AnchorPane();
//        an.setPrefWidth(590);
//        ImageView mrt = new ImageView(new Image("MRT.png"));
//        mrt.setFitWidth(35);
//        mrt.setFitHeight(50);
//        
//        for (int i = 0; i < path.size(); i++) {
//            Label location = new Label(path.get(i));
//            location.setFont(new Font(13));
//            location.setTextAlignment(TextAlignment.RIGHT);
//            location.setMaxWidth(400);
//
//            Label loc = new Label(path.get(0));
//            loc.setFont(new Font(18));
//            loc.setStyle("-fx-background-color: rgba(255, 99, 71, 0.6)");
//            loc.setStyle("-fx-background-radius: 8");
//            loc.setPadding(new Insets(5, 10, 5, 10));
//            loc.setWrapText(true);
//            loc.setMaxWidth(400);
//
//            an.getChildren().addAll(mrt,location,loc);
//        }
//
//        AnchorPane.setLeftAnchor(mrt, 537.0);
//        AnchorPane.setTopAnchor(mrt, 25.0);
//
//        AnchorPane.setRightAnchor(loc, 56.0);
//        AnchorPane.setTopAnchor(loc, 25.0);
//
//        AnchorPane.setRightAnchor(loc, 56.0);
//        AnchorPane.setTopAnchor(loc, 55.0);
//
//        stops.getChildren().add(an);
    }
    
    BorderPane showStops(String location) {
        BorderPane pane = new BorderPane();
        ImageView mrt = new ImageView(new Image("resources/MRT.png"));
        mrt.setFitWidth(35);
        mrt.setFitHeight(50);

        Label loc = new Label(location);
        loc.setFont(Font.font("Cambria", 15));
        loc.setStyle("-fx-font-color: #800000");
        loc.setWrapText(true);
        loc.setMaxWidth(200);
        
        pane.setCenter(mrt);
        pane.setBottom(loc);

        return pane;
    }
    
    BorderPane showArrow() {
        BorderPane pane = new BorderPane();
        ImageView arrow = new ImageView(new Image("resources/Arrow.png"));
        arrow.setFitWidth(33);
        arrow.setFitHeight(13);
        pane.setCenter(arrow);
        return pane;
    }
    
    void showTrain() {
        AnchorPane an = new AnchorPane();
//        an.setPrefWidth(590);
        ImageView train = new ImageView(new Image("resources/mrtGoing.gif"));
        train.setFitWidth(500);
        train.setFitHeight(100);
        an.getChildren().add(train);
        gif.getChildren().add(an);
    }
    
//    void clearStopsVBox() {
//        stops = new VBox();
//        searched = false;
//    }
}
