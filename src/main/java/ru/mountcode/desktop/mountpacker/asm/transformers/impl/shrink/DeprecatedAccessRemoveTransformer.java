package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;

public class DeprecatedAccessRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.forEach((name, classNode) -> {
            if (isDeprecated(classNode)) {
                classNode.access = classNode.access & ~ACC_DEPRECATED;
            }

            classNode.methods.stream()
                    .filter(this::isDeprecated)
                    .forEach(mw -> mw.access = mw.access & ~ACC_DEPRECATED);

            classNode.fields.stream()
                    .filter(this::isDeprecated)
                    .forEach(fw -> fw.access = fw.access & ~ACC_DEPRECATED);
        });
    }

    private boolean isDeprecated(ClassNode node) {
        return (ACC_DEPRECATED & node.access) != 0;
    }

    private boolean isDeprecated(MethodNode node) {
        return (ACC_DEPRECATED & node.access) != 0;
    }

    private boolean isDeprecated(FieldNode node) {
        return (ACC_DEPRECATED & node.access) != 0;
    }
}
