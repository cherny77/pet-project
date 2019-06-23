package sample;

import javafx.beans.binding.Bindings;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;

import javafx.scene.chart.XYChart;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;


import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static sample.Main.*;

public class Controller {


    @FXML
    private Label sliderLabel;

    @FXML
    private Slider slider;

    @FXML
    private TextField xMin;

    @FXML
    private TextField xMax;

    @FXML
    private TextField yMin;

    @FXML
    private TextField yMax;

    @FXML
    private Label labelException1;

    @FXML
    private Label labelException2;

    @FXML
    private Label labelSaveException;


    private static int counter = 0;


    public void draw(ActionEvent event) {
        boolean a = isNumeric(xMin.getText()) && isNumeric(xMax.getText()) && isNumeric(yMin.getText()) && isNumeric(yMax.getText());

        if (a && Double.parseDouble(xMin.getText()) < Double.parseDouble(xMax.getText()) && Double.parseDouble(yMin.getText()) < Double.parseDouble(yMax.getText())) {
            labelSaveException.setOpacity(0);
            labelException2.setOpacity(0);
            labelException1.setOpacity(0);
            Main.clean();
            if (counter < 1) {

                Main.createGraph(slider.getValue(), Double.parseDouble(xMin.getText()), Double.parseDouble(xMax.getText()), Double.parseDouble(yMin.getText()), Double.parseDouble(yMax.getText()));
                drawGraphic();
                counter++;
            } else {

                drawGraphic();
            }
        } else {
            labelException2.setOpacity(100);
            labelException1.setOpacity(100);
        }

    }

    public void changeLabel() {
        sliderLabel.textProperty().bind(
                Bindings.format(
                        "%.4f",
                        slider.valueProperty()
                )
        );
    }

    public void save(ActionEvent event) throws IOException {
        if (numberLineChart != null) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(Calendar.getInstance().getTime());
            WritableImage snapShot = Main.numberLineChart.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapShot, null), "png",
                    new File("graph-" + timeStamp + ".png"));
        } else {
            labelSaveException.setOpacity(100);
        }
    }

    private void drawGraphic() {
        recreateGraph(slider.getValue(), Double.parseDouble(xMin.getText()), Double.parseDouble(xMax.getText()), Double.parseDouble(yMin.getText()), Double.parseDouble(yMax.getText()));

        XYChart.Series downPart = new XYChart.Series();

        Main.initDots(slider.getValue(), Double.parseDouble(xMin.getText()),
                Double.parseDouble(xMax.getText()), Double.parseDouble(yMin.getText()),
                Double.parseDouble(yMax.getText()));

        for (Dot dot : Main.downPart) {
            downPart.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
        }
        XYChart.Series upPart = new XYChart.Series();
        for (Dot dot : Main.upPart) {

            upPart.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
        }
        Main.dotSort(Main.upPart);
        Main.dotSort(Main.downPart);
        if (Main.downPart.size() != 0 && Main.upPart.size() != 0) {
            Dot dot = Main.upPart.get(0);
            if (!(Double.parseDouble(xMin.getText()) > -1.125)) {
                XYChart.Series leftConnection = new XYChart.Series();

                leftConnection.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
                dot = Main.downPart.get(0);
                leftConnection.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
                numberLineChart.getData().addAll(leftConnection);
                leftConnection.getNode().setStyle("-fx-stroke: rgba( 255,0,0 , 1.0);");
            }
            if (Double.parseDouble(xMax.getText()) >= 1.125) {
                XYChart.Series rightConnection = new XYChart.Series();
                dot = Main.upPart.get(Main.upPart.size() - 1);
                rightConnection.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
                dot = Main.downPart.get(Main.downPart.size() - 1);
                rightConnection.getData().add(new XYChart.Data(dot.getX(), dot.getY()));
                numberLineChart.getData().addAll(rightConnection);
                rightConnection.getNode().setStyle("-fx-stroke: rgba( 255,0,0 , 1.0);");
            }
        }
        numberLineChart.setAxisSortingPolicy(javafx.scene.chart.LineChart.SortingPolicy.X_AXIS);
        numberLineChart.getData().addAll(upPart);
        numberLineChart.getData().addAll(downPart);
        upPart.getNode().setStyle("-fx-stroke: rgba( 255,0,0 , 1.0);");
        downPart.getNode().setStyle("-fx-stroke: rgba( 255,0,0 , 1.0);");
        numberLineChart.setCreateSymbols(false);
    }
}
