package ru.mountcode.desktop.mountpacker.workspace;

import lombok.Getter;
import lombok.Setter;
import ru.mountcode.desktop.mountpacker.asm.transformers.TransformersExecutor;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Getter
public class Workspace {

    private static final Workspace workspace = new Workspace();

    private Set<ExecutableJar> executables = new HashSet<>();
    private ExecutableJar executable = new ExecutableJar();

    @Setter
    private File directoryFile = null;

    @Setter
    private boolean enableShrinking = true;
    @Setter
    private boolean enableOptimization = true;

    private boolean executing = false;

    public static Workspace getWorkspace() {
        return workspace;
    }

    public boolean isExecutable() {
        return this.executable != null && this.executable.isValid();
    }

    public CompletableFuture<Void> executeSingle() {
        this.executing = true;
        return CompletableFuture.runAsync(() -> {
            this.getExecutable().readJar();
            if (this.executable.isValid()) {
                if (this.isEnableShrinking()) {
                    TransformersExecutor.executeShrink(this.getExecutable().getClasses());
                }
                if (this.isEnableOptimization()) {
                    TransformersExecutor.executeOptimization(this.getExecutable().getClasses());
                }
            }
            this.getExecutable().writeJar();
            this.getExecutable().clear();
            this.executing = false;
        });
    }

    public CompletableFuture<Void> executeList() {
        this.executing = true;
        return CompletableFuture.runAsync(() -> {
            loadDirectoryExecutables(this.getDirectoryFile());
            for (ExecutableJar executable : this.getExecutables()) {
                executable.readJar();
                if (!executable.isValid()) {
                    continue;
                }

                if (this.isEnableShrinking()) {
                    TransformersExecutor.executeShrink(this.getExecutable().getClasses());
                }
                if (this.isEnableOptimization()) {
                    TransformersExecutor.executeOptimization(this.getExecutable().getClasses());
                }

                executable.writeJar();
            }
            this.getExecutables().clear();
            this.executing = false;
        });
    }

    private void loadDirectoryExecutables(File directoryFile) {
        if (!directoryFile.isDirectory() || directoryFile.listFiles() == null) {
            return;
        }

        Arrays.stream(directoryFile.listFiles()).forEach(file -> {

            if (file.isDirectory()) {
                loadDirectoryExecutables(file);
            } else {
                if (file.getName().toLowerCase().endsWith(".jar") || file.getName().toLowerCase().endsWith(".zip")) {
                    this.executables.add(new ExecutableJar(file, file));
                }
            }
        });
    }
}
