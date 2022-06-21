import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class DeclarationParserV10 extends DeclarationParser {

  public DeclarationParserV10(@NotNull TokenIterator stream) {
    super(stream);
  }

  public boolean canChange() {
    consume(DefaultTokenTypes.KEYWORD, "let");
    return true;
  }
}
