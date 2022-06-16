package com.example.histograms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Arrays;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class HistogramController extends Application {

    private Scene scene;
    private Parent root;
    int histogramSize ;
    int bin;
    int [] histArr;
    int  temp;
    int[] cutoffs;
    int[] counts;

    public void start(Stage stage) throws IOException {

        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        scene = new Scene(root , 503, 547);
        stage.setScene(scene);
        stage.show();

    }


    public void createStage1(){
        Stage primaryStage = new Stage();
        Calculation();
        Label labelInfo = new Label();
        labelInfo.setText("Presented by WDC");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart = new BarChart<>(xAxis,yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);

        xAxis.setLabel("Range of Number of Passengers at 5min interval");
        yAxis.setLabel("Number of Data point(s)");

        //yAxis.setAutoRanging(false);
        //yAxis.setLowerBound(0);
        //yAxis.setUpperBound(1000);
        //yAxis.setTickUnit(100);


        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Counts");
        for(int i =0; i<bin; i++){
            series1.getData().add(new XYChart.Data(cutoffs[i]+"-"+cutoffs[i+1], counts[i]));
        }


        barChart.getData().addAll(series1);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, barChart);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 800, 450);

        primaryStage.setTitle("WIA1002 HISTOGRAM");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private void Calculation(){

        //initialise counts
        for(int i=0; i<bin; i++){
            counts[i]=0;
        }

        //sort array
        Arrays.sort(histArr);

        //find required data
        int min = histArr[0];
        int max = histArr[histogramSize-1];
        int increment = (max - min) / bin;
        temp = min;
        int counter = 0;

        //find cutoffs
        while (temp <= max) {
            cutoffs[counter] = temp;
            temp += increment;
            counter++;
        }

        temp = 1;
        int nextBin = cutoffs[temp];
        counter = 0;

        //find counts
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
    }

}