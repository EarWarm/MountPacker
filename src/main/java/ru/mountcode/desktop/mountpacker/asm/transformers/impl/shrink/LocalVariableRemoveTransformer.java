package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;

public class LocalVariableRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().forEach(classNode ->
                classNode.methods.stream()
                        .filter(methodWrapper ->  methodWrapper.localVariables != null)
                        .forEach(methodNode -> methodNode.localVariables = null));
    }
}
