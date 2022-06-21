import org.austral.ingsis.printscript.parser.TokenIterator;
import org.jetbrains.annotations.NotNull;

public class ParserBuilder {

  public DefaultParser buildParser_V10(@NotNull TokenIterator stream) {
    return new DefaultParser(stream, new ContentParserV10(stream));
  }

  public DefaultParser buildParser_V11(@NotNull TokenIterator stream) {
    return new DefaultParser(stream, new ContentParserV11(stream));
  }
}
