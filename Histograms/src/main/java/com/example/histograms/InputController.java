package com.example.histograms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;


public class InputController {


        @FXML
        private TextField bin;

        @FXML
        private TextField hist;

        @FXML
        private TextField size;


        public void submit(javafx.event.ActionEvent actionEvent) throws IOException {
                HistogramController input = new HistogramController();
                input.histogramSize = Integer.parseInt(size.getText());
                input.bin = Integer.parseInt(bin.getText());
                String [] hists = hist.getText().split(", ");
                input.histArr = new int [hists.length];
                input.cutoffs = new int [Integer.parseInt(bin.getText())+1];
                input.counts = new int [Integer.parseInt(bin.getText())];
                for (int y = 0; y< hists.length;y++) {
                        input.histArr[y] = Integer.parseInt(hists[y]);
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("graph.fxml"));
                Parent root = loader.load();
                input.createStage1();


        }


}