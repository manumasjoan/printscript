

import org.austral.ingsis.printscript.common.Token;
import java.util.List;

public interface Lexer {
    List<Token> getTokens(String input);
}