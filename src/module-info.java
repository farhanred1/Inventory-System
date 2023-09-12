module proj {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.sql;
	requires itextpdf;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
