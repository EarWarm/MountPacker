package ru.mountcode.desktop.mountpacker.asm.transformers;

import org.objectweb.asm.tree.ClassNode;
import ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization.GotoGotoInlineTransformer;
import ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization.GotoReturnInlineTransformer;
import ru.mountcode.desktop.mountpacker.asm.transformers.impl.optimization.NopRemoveTransformer;
import ru.mountcode.desktop.mountpacker.asm.transformers.impl.shrink.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TransformersExecutor {

    private static final Set<ITransformer> shrinkTransformers = new HashSet<>();
    private static final Set<ITransformer> optimizationTransformers = new HashSet<>();

    static {
        optimizationTransformers.add(new NopRemoveTransformer());
        optimizationTransformers.add(new GotoGotoInlineTransformer());
        optimizationTransformers.add(new GotoReturnInlineTransformer());

        shrinkTransformers.add(new DeprecatedAccessRemoveTransformer());
        shrinkTransformers.add(new InnerClassRemoveTransformer());
        shrinkTransformers.add(new LineNumberRemoveTransformer());
        shrinkTransformers.add(new LocalVariableRemoveTransformer());
        shrinkTransformers.add(new OuterRemoveTransformer());
        shrinkTransformers.add(new SignatureRemoveTransformer());
        shrinkTransformers.add(new SourceDebugRemoveTransformer());
        shrinkTransformers.add(new SourceFileRemoveTransformer());
        shrinkTransformers.add(new SyntheticAccessRemoveTransformer());
    }

    public static void executeOptimization(HashMap<String, ClassNode> classes) {
        optimizationTransformers.forEach(transformer -> transformer.transform(classes));
    }

    public static void executeShrink(HashMap<String, ClassNode> classes) {
        shrinkTransformers.forEach(transformer -> transformer.transform(classes));
    }
}
