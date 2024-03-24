package ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.ITransformer;
import ru.mountcode.desktop.mountpacker.asm.utils.ASMUtils;

import java.util.Map;
import java.util.stream.Stream;

public class GotoReturnInlineTransformer implements ITransformer {
    @Override
    public void transform(Map<String, ClassNode> classes) {
        classes.forEach((name, classNode) ->
                classNode.methods.stream().filter(methodNode -> methodNode.instructions.size() > 0).forEach(methodNode -> {
                    Stream.of(methodNode.instructions.toArray()).filter(insn -> insn.getOpcode() == GOTO)
                            .forEach(insn -> {
                                JumpInsnNode gotoJump = (JumpInsnNode) insn;
                                AbstractInsnNode insnAfterTarget = gotoJump.label.getNext();

                                if (insnAfterTarget != null && ASMUtils.isReturn(insnAfterTarget.getOpcode())) {
                                    methodNode.instructions.set(insn, new InsnNode(insnAfterTarget.getOpcode()));
                                }
                            });
                }));
    }
}
