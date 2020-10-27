module brick {
    requires javafx.controls;
    requires javafx.fxml;

    opens brick to javafx.fxml;
    exports brick;
}