module org.example.term_porject_12_part_two {
    requires javafx.controls;
    requires javafx.fxml;

    // Allow the JavaFX runtime to access all classes in these packages via reflection
    opens org.example.term_porject_12_part_two to javafx.fxml, javafx.graphics;
    opens org.example.term_porject_12_part_two.Scenes to javafx.fxml, javafx.graphics;

    // Export the primary package for public use
    exports org.example.term_porject_12_part_two;
}
