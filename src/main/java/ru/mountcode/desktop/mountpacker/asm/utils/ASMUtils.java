package ru.mountcode.desktop.mountpacker.asm.utils;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FrameNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;

public class ASMUtils {
    public static boolean isInstruction(AbstractInsnNode insn) {
        return !(insn instanceof FrameNode) && !(insn instanceof LineNumberNode) && !(insn instanceof LabelNode);
    }

    public static boolean isReturn(int opcode) {
        return (opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN);
    }


    public static String getGenericMethodDesc(String desc) {
        Type returnType = Type.getReturnType(desc);
        Type[] args = Type.getArgumentTypes(desc);
        for (int i = 0; i < args.length; i++) {
            Type arg = args[i];

            if (arg.getSort() == Type.OBJECT)
                args[i] = Type.getType("Ljava/lang/Object;");
        }

        return Type.getMethodDescriptor(returnType, args);
    }

    public static int getReturnOpcode(Type type) {
        switch (type.getSort()) {
            case Type.BOOLEAN:
            case Type.CHAR:
            case Type.BYTE:
            case Type.SHORT:
            case Type.INT:
                return Opcodes.IRETURN;
            case Type.FLOAT:
                return Opcodes.FRETURN;
            case Type.LONG:
                return Opcodes.LRETURN;
            case Type.DOUBLE:
                return Opcodes.DRETURN;
            case Type.ARRAY:
            case Type.OBJECT:
                return Opcodes.ARETURN;
            case Type.VOID:
                return Opcodes.RETURN;
            default:
                throw new AssertionError("Unknown type sort: " + type.getClassName());
        }
    }

    public static int getVarOpcode(Type type, boolean store) {
        switch (type.getSort()) {
            case Type.BOOLEAN:
            case Type.CHAR:
            case Type.BYTE:
            case Type.SHORT:
            case Type.INT:
                return store ? Opcodes.ISTORE : Opcodes.ILOAD;
            case Type.FLOAT:
                return store ? Opcodes.FSTORE : Opcodes.FLOAD;
            case Type.LONG:
                return store ? Opcodes.LSTORE : Opcodes.LLOAD;
            case Type.DOUBLE:
                return store ? Opcodes.DSTORE : Opcodes.DLOAD;
            case Type.ARRAY:
            case Type.OBJECT:
                return store ? Opcodes.ASTORE : Opcodes.ALOAD;
            default:
                throw new AssertionError("Unknown type: " + type.getClassName());
        }
    }
}