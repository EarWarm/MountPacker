package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.ArrayList;
import java.util.Map;

public class InnerClassRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().stream().filter(classNode -> classNode.innerClasses != null).forEach(classWrapper -> {
            classWrapper.innerClasses = new ArrayList<>();
        });
    }
}
