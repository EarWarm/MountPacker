package ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.JumpInsnNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;

import java.util.Map;
import java.util.stream.Stream;

public class GotoGotoInlineTransformer implements ITransformer {

    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.forEach((name, classNode) ->
                classNode.methods.stream().filter(method -> method.instructions.size() > 0).forEach(methodNode -> {
                    Stream.of(methodNode.instructions.toArray()).filter(insn -> insn.getOpcode() == GOTO)
                            .forEach(insn -> {
                                JumpInsnNode gotoJump = (JumpInsnNode) insn;
                                AbstractInsnNode insnAfterTarget = gotoJump.label.getNext();

                                if (insnAfterTarget != null && insnAfterTarget.getOpcode() == GOTO) {
                                    JumpInsnNode secGoto = (JumpInsnNode) insnAfterTarget;
                                    gotoJump.label = secGoto.label;
                                }
                            });
                }));
    }
}
