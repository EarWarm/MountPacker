package ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;
import java.util.stream.Stream;

public class NopRemoveTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.forEach((name, classNode) ->
                classNode.methods.stream()
                        .filter(method -> method.instructions.size() > 0)
                        .forEach(methodNode -> Stream.of(methodNode.instructions.toArray()).filter(insn -> insn.getOpcode() == NOP)
                                .forEach(insn -> methodNode.instructions.remove(insn)))
        );
    }
}
