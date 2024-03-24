package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;

public class SyntheticAccessRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().forEach(classNode -> {
            if (isSynthetic(classNode)) {
                classNode.access = classNode.access & ~ACC_SYNTHETIC;
            }

            classNode.methods.stream()
                    .filter(mw -> isSynthetic(mw) || isBridge(mw))
                    .forEach(mw -> mw.access = mw.access & ~(ACC_SYNTHETIC | ACC_BRIDGE));

            classNode.fields.stream()
                    .filter(this::isSynthetic)
                    .forEach(fw -> fw.access = fw.access & ~ACC_SYNTHETIC);
        });
    }

    public boolean isSynthetic(ClassNode node) {
        return (ACC_SYNTHETIC & node.access) != 0;
    }

    public boolean isSynthetic(MethodNode node) {
        return (ACC_SYNTHETIC & node.access) != 0;
    }

    public boolean isSynthetic(FieldNode node) {
        return (ACC_SYNTHETIC & node.access) != 0;
    }

    public boolean isBridge(MethodNode methodNode) {
        return (ACC_BRIDGE & methodNode.access) != 0;
    }
}
