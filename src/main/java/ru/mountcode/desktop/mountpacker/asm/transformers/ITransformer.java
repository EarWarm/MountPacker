package ru.mountcode.desktop.mountpacker.asm.transformers;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

public interface ITransformer extends Opcodes {

    void transform(Map<String, ClassNode> classes);
}
