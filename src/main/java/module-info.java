module com.example.barberappointment {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.barberappointment to javafx.fxml;
    exports com.example.barberappointment;
}