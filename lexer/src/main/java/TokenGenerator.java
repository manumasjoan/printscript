

import org.austral.ingsis.printscript.common.Token;

public interface TokenGenerator {

    TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input);
}
