import org.austral.ingsis.printscript.common.Token;

public interface InputTokenGenerator {

  Token read(State state, String input);
}
