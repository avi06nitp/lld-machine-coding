package factory;

import enums.EncodingType;
import strategy.Base62Strategy;
import strategy.CodeGenerationStrategy;
import strategy.HashStrategy;

/**
 * Factory for creating the appropriate CodeGenerationStrategy.
 * Decouples strategy instantiation from service logic.
 */
public class CodeGeneratorFactory {

    public static CodeGenerationStrategy create(EncodingType encodingType) {
        return switch (encodingType) {
            case BASE62 -> new Base62Strategy();
            case HASH -> new HashStrategy();
        };
    }
}
