package dev.webfx.demo.boundgauge;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Bruno Salmon
 */
public final class BoundGaugeApplication extends Application {

    @Override
    public void start(Stage stage) {
        Gauge gauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .prefSize(400, 400)
                .sectionTextVisible(true)
                .title("MODERN")
                .unit("UNIT")
                .thresholdVisible(true)
                .animated(false)
                .build();

        Slider valueSlider = new Slider(0, 100, 50);
        Slider thresholdSlider = new Slider(0, 100, 85);

        // This improves slider performance on low devices (reduce click target possibilities)
        gauge.setMouseTransparent(true);

        // Binding Gauge value and threshold with sliders
        gauge.valueProperty().bind(valueSlider.valueProperty());
        gauge.tresholdProperty().bind(thresholdSlider.valueProperty());

        Pane root = new Pane(gauge, valueSlider, thresholdSlider) {
            @Override
            public void layoutChildren() {
                double width = getWidth(), height = getHeight();
                Orientation sliderOrientation;
                if (width >= height) {
                    sliderOrientation = Orientation.VERTICAL;
                    double sliderAreaWidth = Math.max(40, (width - height) / 2), sliderAreaHeight = 0.8 * height, sliderY = (height - sliderAreaHeight) / 2;
                    layoutInArea(gauge, sliderAreaWidth, 0, width - 2 * sliderAreaWidth, height, 0, HPos.CENTER, VPos.CENTER);
                    layoutInArea(valueSlider, 0, sliderY, sliderAreaWidth, sliderAreaHeight, 0, HPos.CENTER, VPos.CENTER);
                    layoutInArea(thresholdSlider, width - sliderAreaWidth, sliderY, sliderAreaWidth, sliderAreaHeight, 0, HPos.CENTER, VPos.CENTER);
                } else {
                    sliderOrientation = Orientation.HORIZONTAL;
                    double sliderAreaHeight = Math.max(40, (height - width) / 2), sliderAreaWidth = 0.8 * width, sliderX = (width - sliderAreaWidth) / 2;
                    layoutInArea(gauge, 0, sliderAreaHeight, width, height - 2 * sliderAreaHeight, 0, HPos.CENTER, VPos.CENTER);
                    layoutInArea(valueSlider, sliderX, 0, sliderAreaWidth, sliderAreaHeight, 0, HPos.CENTER, VPos.CENTER);
                    layoutInArea(thresholdSlider, sliderX, height - sliderAreaHeight, sliderAreaWidth, sliderAreaHeight, 0, HPos.CENTER, VPos.CENTER);
                }
                valueSlider.setOrientation(sliderOrientation);
                thresholdSlider.setOrientation(sliderOrientation);
            }
        };
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root,800, 600);
        stage.setScene(scene);
        stage.setTitle("Modern Gauge");
        stage.show();
    }

}
