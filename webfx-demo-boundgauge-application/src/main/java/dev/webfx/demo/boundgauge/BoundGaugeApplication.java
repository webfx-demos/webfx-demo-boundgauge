package dev.webfx.demo.boundgauge;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
        Text valueText = createText("Value"), thresholdText = createText("Threshold");

        // Binding Gauge value and threshold with sliders
        gauge.valueProperty().bind(valueSlider.valueProperty());
        gauge.tresholdProperty().bind(thresholdSlider.valueProperty());

        // This improves slider performance on low devices (reduce click target possibilities)
        gauge.setMouseTransparent(true);
        Rectangle clip = new Rectangle();
        gauge.setClip(clip);

        Pane root = new Pane(gauge, valueText, valueSlider, thresholdText, thresholdSlider) {
            @Override
            public void layoutChildren() {
                double w = getWidth(), h = getHeight(), vtx, vty, vsx, vsy, ttx, tty, tsx, tsy, tw, th, sw, sh, gx, gy, gw, gh;
                Orientation so;
                tw = thresholdText.getLayoutBounds().getWidth() * 1.2;
                if (w >= h) {
                    so = Orientation.VERTICAL; // Sliders orientation
                    sw = Math.max(tw, (w - h) / 2); sh = 0.8 * h; // Sliders area width & height
                    vsx = 0; vsy = (h - sh) / 2; // Value slider x & y
                    tsx = w - sw; tsy = vsy; // Threshold slider x & y
                    gx = sw; gy = 0; // Gauge x & y
                    gw = w - 2 * sw; gh = h; // Gauge width & height
                    vtx = vsx; vty = 0; tw = sw; th = vsy; // Value text
                    ttx = tsx; tty = 0; // Threshold text
                } else {
                    vtx = ttx = 0;
                    so = Orientation.HORIZONTAL;  // Sliders orientation
                    sh = Math.max(40, (h - w) / 2); sw = 0.95 * (w - tw); // Slider areas width & height
                    vsx = tw ; vsy = 0; // Value Slider x & y
                    tsx = vsx; tsy = h - sh; // Threshold slider x & y
                    gx = 0; gy = sh; // Gauge x & y
                    gw = w; gh = h - 2 * sh; // Gauge width & height
                    vty = vsy; th = sh; // Value text
                    tty = tsy; // Threshold text
                }
                valueSlider.setOrientation(so);
                thresholdSlider.setOrientation(so);
                clip.setWidth(gw); clip.setHeight(gh);
                layoutInArea(gauge, gx, gy, gw, gh, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(valueText, vtx, vty, tw, th, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(valueSlider, vsx, vsy, sw, sh, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(thresholdText, ttx, tty, tw, th, 0, HPos.CENTER, VPos.CENTER);
                layoutInArea(thresholdSlider, tsx, tsy, sw, sh, 0, HPos.CENTER, VPos.CENTER);
            }
        };
        root.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        Scene scene = new Scene(root,800, 600);
        stage.setScene(scene);
        stage.setTitle("Bound Gauge");
        stage.show();
    }

    private Text createText(String s) {
        Text text = new Text(s);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Verdana", 28));
        return text;
    }

}
