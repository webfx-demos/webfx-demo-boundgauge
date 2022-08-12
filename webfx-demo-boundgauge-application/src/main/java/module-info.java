// File managed by WebFX (DO NOT EDIT MANUALLY)

module webfx.demo.boundgauge.application {

    // Direct dependencies modules
    requires javafx.controls;
    requires javafx.graphics;
    requires webfx.lib.medusa;

    // Exported packages
    exports dev.webfx.demo.boundgauge;

    // Provided services
    provides javafx.application.Application with dev.webfx.demo.boundgauge.BoundGaugeApplication;

}