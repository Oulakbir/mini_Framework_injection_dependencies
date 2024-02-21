module ma.enset.miniprojet_jee {
    requires javafx.controls;
    requires javafx.fxml;


    opens ma.enset.miniprojet_jee to javafx.fxml;
    exports ma.enset.miniprojet_jee;
}