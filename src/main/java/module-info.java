module org.parsetj.parsetj {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.parsetj.parsetj to javafx.fxml;
    exports org.parsetj.parsetj;
}