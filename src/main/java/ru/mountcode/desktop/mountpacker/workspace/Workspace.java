package ru.mountcode.desktop.mountpacker.workspace;

import lombok.Getter;
import lombok.Setter;
import ru.mountcode.desktop.mountpacker.asm.transformers.TransformersExecutor;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Getter
public class Workspace {

    private static final Workspace workspace = new Workspace();

    private ExecutableJar executable = null;

    @Setter
    private boolean enableShrinking = true;
    @Setter
    private boolean enableOptimization = true;

    @Setter
    private File inputFile = null;
    @Setter
    private File outputFile = null;

    private boolean executing = false;

    public static Workspace getWorkspace() {
        return workspace;
    }

    public boolean isExecutable() {
        return this.executable != null && this.executable.isValid();
    }

    public void loadInputJar() {
        this.executable = new ExecutableJar(this.getInputFile());
    }

    public CompletableFuture<Void> execute() {
        this.executable = null;
        this.executing = true;
        return CompletableFuture.runAsync(() -> {
            this.loadInputJar();
            if (this.executable.isValid()) {
                if (this.isEnableShrinking()) {
                    TransformersExecutor.executeShrink(this.getExecutable().getClasses());
                }
                if (this.isEnableOptimization()) {
                    TransformersExecutor.executeOptimization(this.getExecutable().getClasses());
                }
            }
            this.getExecutable().writeJar(this.getOutputFile());
            this.executing = false;
        });
    }
}
