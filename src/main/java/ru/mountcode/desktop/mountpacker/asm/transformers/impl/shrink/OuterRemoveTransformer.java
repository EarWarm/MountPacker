package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;

public class OuterRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().stream().filter(classNode -> classNode.outerClass != null).forEach(classNode -> {
            classNode.outerClass = null;
            classNode.outerMethod = null;
            classNode.outerMethodDesc = null;
        });
    }
}
