package ru.mountcode.desktop.mountpacker.workspace;

import lombok.Getter;
import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.utils.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Getter
public class ExecutableJar {

    private HashMap<String, ClassNode> classes = null;
    private HashMap<String, byte[]> resources = null;

    public ExecutableJar(File inputFile) {
        this.readJar(inputFile);
    }

    public boolean isValid() {
        return this.classes != null && !this.classes.isEmpty();
    }

    public void writeJar(File outputFile) {
        if (outputFile.exists()) {
            outputFile.delete();
        }

        Set<String> dirs = new HashSet<>();
        try (ZipOutputStream outputStream = new ZipOutputStream(Files.newOutputStream(outputFile.toPath()))) {
            outputStream.setLevel(9);
            for (ClassNode classNode : classes.values()) {
                try {
                    IOUtils.addDirectories(classNode.name, dirs);
                    outputStream.putNextEntry(new ZipEntry(classNode.name + ".class"));
                    outputStream.write(IOUtils.writeClassToBytes(classNode));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    outputStream.closeEntry();
                }
            }
            if (!this.resources.isEmpty()) {
                for (Map.Entry<String, byte[]> entry : this.resources.entrySet()) {
                    IOUtils.addDirectories(entry.getKey(), dirs);
                    outputStream.putNextEntry(new ZipEntry(entry.getKey()));
                    outputStream.write(entry.getValue());
                    outputStream.closeEntry();
                }
            }

            for (String dirPath : dirs) {
                outputStream.putNextEntry(new ZipEntry(dirPath + "/"));
                outputStream.closeEntry();
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readJar(File inputFile) {
        HashMap<String, ClassNode> classes = new HashMap<>();
        HashMap<String, byte[]> resources = new HashMap<>();

        try (ZipInputStream inputStream = new ZipInputStream(new FileInputStream(inputFile))) {
            ZipEntry zipEntry;
            while ((zipEntry = inputStream.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    continue;
                }

                String name = zipEntry.getName();
                if (name.endsWith(".class")) {
                    byte[] bytes = IOUtils.readStreamFully(inputStream);
                    if (bytes.length > 0) {
                        ClassNode classNode = IOUtils.readClassFromBytes(bytes);
                        classes.put(classNode.name, classNode);
                    }
                } else {
                    resources.put(name, IOUtils.readStreamFully(inputStream));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.classes = classes;
        this.resources = resources;
    }
}