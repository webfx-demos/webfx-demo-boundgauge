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
                .threshold(85)
                .thresholdVisible(true)
                .animated(false)
                .build();
        Slider slider = new Slider(0, 100, 50);
        slider.setOrientation(Orientation.VERTICAL);
        gauge.valueProperty().bind(slider.valueProperty());

        Pane root = new Pane(slider, gauge) {
            @Override
            public void layoutChildren() {
                double width = getWidth(), height = getHeight();
                layoutInArea(slider, 0, 0.1 * height , (width - height) / 2, 0.8 * height, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(gauge, 0, 0, width, height, 0, HPos.CENTER, VPos.CENTER);
            }
        };
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root,800, 600);
        stage.setScene(scene);
        stage.setTitle("Modern Gauge");
        stage.show();
    }

}
