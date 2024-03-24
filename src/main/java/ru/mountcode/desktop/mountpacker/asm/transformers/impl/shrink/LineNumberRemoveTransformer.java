package ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;
import java.util.stream.Stream;

public class LineNumberRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.values().forEach(classNode ->
                classNode.methods.stream()
                        .filter(method -> method.instructions.size() > 0)
                        .forEach(methodNode -> Stream.of(methodNode.instructions.toArray())
                                .filter(insn -> insn instanceof LineNumberNode)
                                .forEach(insn -> methodNode.instructions.remove(insn))));
    }
}
