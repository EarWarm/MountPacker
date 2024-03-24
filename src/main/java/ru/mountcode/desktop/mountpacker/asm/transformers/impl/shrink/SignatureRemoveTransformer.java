package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;

public class SignatureRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().forEach(classNode -> {
            if (classNode.signature != null) {
                classNode.signature = null;
            }

            classNode.methods.stream()
                    .filter(methodWrapper -> methodWrapper.signature != null)
                    .forEach(methodWrapper -> methodWrapper.signature = null);

            classNode.fields.stream()
                    .filter(fieldWrapper -> fieldWrapper.signature != null)
                    .forEach(fieldWrapper -> fieldWrapper.signature = null);
        });
    }
}
