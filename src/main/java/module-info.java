module br.edu.ifsc.fln.sistemacarwash {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens br.edu.ifsc.fln.sistemacarwash to javafx.fxml;
    exports br.edu.ifsc.fln.sistemacarwash;
}