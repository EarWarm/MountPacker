package ru.mountcode.desktop.mountpacker.application.utils;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

public class FileChooserUtil {

    private static final FileChooser javaProgramChooser = new FileChooser();

    private static final FileChooser.ExtensionFilter javaProgramFilter = new FileChooser.ExtensionFilter("Java programs & archives", "*.jar", "*.zip");

    private static final DirectoryChooser directoryChooser = new DirectoryChooser();
    static {
        javaProgramChooser.setSelectedExtensionFilter(javaProgramFilter);
        javaProgramChooser.setTitle("Выберите Java программу");
        directoryChooser.setTitle("Выберите директорию");
    }

    public static FileChooser getJavaProgramChooser() {
        return javaProgramChooser;
    }

    public static DirectoryChooser getDirectoryChooser() {
        return directoryChooser;
    }
}
