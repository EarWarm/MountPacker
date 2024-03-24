package ru.mountcode.desktop.mountpacker.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import ru.mountcode.desktop.mountpacker.application.Application;
import ru.mountcode.desktop.mountpacker.application.utils.FileChooserUtil;
import ru.mountcode.desktop.mountpacker.workspace.Workspace;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class ApplicationController {

    @FXML
    private TextField inputPath;

    @FXML
    private TextField outputPath;

    @FXML
    private TextField directoryPath;

    @FXML
    private RadioButton enableShrinking;

    @FXML
    private RadioButton enableOptimization;

    @FXML
    private Button startButton;

    public void initialize() {
        this.enableShrinking.setSelected(Workspace.getWorkspace().isEnableShrinking());
        this.enableOptimization.setSelected(Workspace.getWorkspace().isEnableOptimization());

        this.enableOptimization.selectedProperty().addListener((observable, oldValue, newValue) -> Workspace.getWorkspace().setEnableOptimization(newValue));
        this.enableShrinking.selectedProperty().addListener((observable, oldValue, newValue) -> Workspace.getWorkspace().setEnableShrinking(newValue));

        this.outputPath.textProperty().addListener((observable, oldValue, newValue) -> {
            loadOutputFile(new File(newValue), false);
        });
    }

    public void chooseInput(ActionEvent event) {
        FileChooser fileChooser = FileChooserUtil.getJavaProgramChooser();
        if (Workspace.getWorkspace().getInputFile() != null) {
            fileChooser.setInitialFileName(Workspace.getWorkspace().getInputFile().getName());
            fileChooser.setInitialDirectory(Workspace.getWorkspace().getInputFile().getParentFile());
        } else {
            fileChooser.setInitialDirectory(new File("."));
        }

        File file = fileChooser.showOpenDialog(Application.getApplication().getStage());

        if (!file.exists()) {
            return;
        }

        if (!file.getName().endsWith(".jar") && !file.getName().endsWith(".zip")) {
            return;
        }

        loadInputFile(file);
    }

    public void onDragOver(DragEvent e) {
        if (e.getGestureSource() != this && e.getDragboard().hasFiles()) {
            if (e.getDragboard().getFiles().size() > 1) {
                e.consume();
                return;
            }

            File file = e.getDragboard().getFiles().get(0);
            if (!file.getName().endsWith(".jar") && !file.getName().endsWith(".zip")) {
                e.consume();
                return;
            }
            e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        e.consume();
    }

    public void onDragDropped(DragEvent e) {
        if(e.getDragboard().hasFiles()) {
            File file = e.getDragboard().getFiles().get(0);
            loadInputFile(file);
            e.setDropCompleted(true);
        }
    }

    public void onStart(ActionEvent event) {
        if (!Workspace.getWorkspace().isExecuting()) {
            if (inputPath.getText().isBlank() || outputPath.getText().isBlank())  {
                return;
            }
            this.startButton.setDisable(true);
            CompletableFuture<Void> future = Workspace.getWorkspace().execute();
            future.thenAccept((result) -> {
                this.startButton.setDisable(false);
            });
        }
    }

    private void loadInputFile(File inputFile) {
        Workspace.getWorkspace().setInputFile(inputFile);
        this.inputPath.setText(inputFile.getAbsolutePath());
        File file = new File(inputFile.getParentFile(), inputFile.getName().replace(".jar", "").replace(".zip", "") + "-packed.jar");
        loadOutputFile(file, true);
    }

    private void loadOutputFile(File outputFile, boolean otherAccess) {
        if (otherAccess) {
            this.outputPath.setText(outputFile.getAbsolutePath());
        }

        Workspace.getWorkspace().setOutputFile(outputFile);
    }
}
