package ru.mountcode.desktop.mountpacker.application.utils;

import javafx.stage.FileChooser;

public class FileChooserUtil {

    private static final FileChooser javaProgramChooser = new FileChooser();

    private static final FileChooser.ExtensionFilter javaProgramFilter = new FileChooser.ExtensionFilter("Java programs & archives", "*.jar", "*.zip");

    static {
        javaProgramChooser.setSelectedExtensionFilter(javaProgramFilter);
        javaProgramChooser.setTitle("Выберите Java программу");
    }

    public static FileChooser getJavaProgramChooser() {
        return javaProgramChooser;
    }
}
