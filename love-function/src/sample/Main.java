package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main extends Application {
    public static Stage graphFrame;
    public static ArrayList<Dot> downPart = new ArrayList<>();
    public static ArrayList<Dot> upPart = new ArrayList<>();
    public static LineChart<Number, Number> numberLineChart;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("MyGraph");
        primaryStage.setScene(new Scene(root, 400, 350));
        primaryStage.show();
    }


    public static void createGraph(double step, double xMin, double xMax, double yMin, double yMax) {
        graphFrame = new Stage();
        graphFrame.setTitle("lOVE");
        recreateGraph(step, xMin, xMax, yMin, yMax);
    }

    public static void recreateGraph(double step, double xMin, double xMax, double yMin, double yMax) {
        NumberAxis xN = new NumberAxis();
        NumberAxis yN = new NumberAxis();
        xN.setAutoRanging(false);
        yN.setAutoRanging(false);
        xN.setTickUnit(0.1);
        yN.setTickUnit(0.1);
        xN.setLowerBound(xMin);
        xN.setUpperBound(xMax);
        yN.setLowerBound(yMin);
        yN.setUpperBound(yMax);
        numberLineChart = new LineChart<Number, Number>(xN, yN);
        numberLineChart.setTitle("Love Formula " + "\n" + " step є { " + step + " }" + "\n" + " x є [" + xMin + " ; " + xMax + "] " + "\n" + " y є [" + yMin + " ; " + yMax + "]");
        numberLineChart.setLegendVisible(false); // !!!!
        Scene scene = new Scene(numberLineChart, 800, 800);
        graphFrame.setScene(scene);
        graphFrame.show();
    }

    public static void clean() {
        downPart = new ArrayList<>();
        upPart = new ArrayList<>();

    }

    public static void initDots(double step, double x1Range, double x2Range, double y1Range, double y2Range) {
        for (double x = 0; x < 1.5; x += step) {
            double y1 = (1 / 2.0) * ((Math.pow(x, 2 / 3.0)) + Math.sqrt(Math.pow(x, 4 / 3.0) + 4 * (1 - x * x)));
            double y2 = (1 / 2.0) * ((Math.pow(x, 2 / 3.0)) - Math.sqrt(Math.pow(x, 4 / 3.0) + 4 * (1 - x * x)));
            if (!Double.isNaN(y1))
                downPart.add(new Dot(x, Math.min(y1, y2)));

            if (!Double.isNaN(y2))
                upPart.add(new Dot(x, Math.max(y1, y2)));
        }

        if (x1Range < 0) {
            for (double x = 0; x < 1.5; x += step) {

                double y1 = (1 / 2.0) * ((Math.pow(x, 2 / 3.0)) + Math.sqrt(Math.pow(x, 4 / 3.0) + 4 * (1 - x * x)));
                double y2 = (1 / 2.0) * ((Math.pow(x, 2 / 3.0)) - Math.sqrt(Math.pow(x, 4 / 3.0) + 4 * (1 - x * x)));

                if (!Double.isNaN(y1))
                    upPart.add(new Dot(-x, Math.max(y1, y2)));
                if (!Double.isNaN(y2))
                    downPart.add(new Dot((-x), Math.min(y1, y2)));
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


    public static void dotSort(ArrayList<Dot> list) {
        Collections.sort(list, new Comparator<Dot>() {
            @Override
            public int compare(Dot o1, Dot o2) {
                return Double.compare(o1.getX(), o2.getX());
            }
        });

    }

    public static void dotSortY(ArrayList<Dot> list) {
        Collections.sort(list, new Comparator<Dot>() {
            @Override
            public int compare(Dot o1, Dot o2) {
                return Double.compare(o1.getY(), o2.getY());
            }
        });

    }

    public static boolean isNumeric(String s) {
        boolean numeric = true;

        try {
            Double num = Double.parseDouble(s);
        } catch (NumberFormatException e) {
            numeric = false;
        }

        if (numeric)
            return true;
        else
            return false;
    }



}
