import org.austral.ingsis.printscript.common.LexicalRange;
import org.austral.ingsis.printscript.common.Token;
import org.austral.ingsis.printscript.common.TokenType;
import org.jetbrains.annotations.NotNull;

public abstract class TokenGenerator {

  abstract TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input);

  @NotNull
  public Token createToken(
      TokenType tokenType, LexicalRangeState lexicalRangeState, int inputLength) {
    int index = lexicalRangeState.getIndex();
    return new Token(
        tokenType,
        index,
        index + inputLength,
        new LexicalRange(
            lexicalRangeState.getColumn(),
            lexicalRangeState.getLine(),
            lexicalRangeState.getColumn() + inputLength,
            lexicalRangeState.getLine()));
  }

  public LexicalRangeState updateState(LexicalRangeState lexicalRangeState, int inputLength) {
    return lexicalRangeState.updateState(
        lexicalRangeState.getIndex() + inputLength,
        lexicalRangeState.getLine(),
        lexicalRangeState.getColumn() + inputLength);
  }

  @NotNull
  public String getNextWord(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();

    if (firstCharIsLetter(lexicalRangeState, input)) {

      string.append(input.charAt(lexicalRangeState.getIndex()));

      int i = lexicalRangeState.getIndex();

      while (i + 1 < input.length() && Character.isLetterOrDigit(input.charAt(i + 1))) {
        char nextChar = input.charAt(i + 1);
        string.append(nextChar);
        i++;
      }
    }

    return string.toString();
  }

  @NotNull
  public String getNumber(LexicalRangeState lexicalRangeState, String input) {
    StringBuilder string = new StringBuilder();

    if (firstCharIsDigit(lexicalRangeState, input)) {

      string.append(input.charAt(lexicalRangeState.getIndex()));

      int i = lexicalRangeState.getIndex();

      while (i + 1 < input.length()
          && (Character.isDigit(input.charAt(i + 1)) || input.charAt(i + 1) == '.')) {
        char nextChar = input.charAt(i + 1);
        string.append(nextChar);
        i++;
      }
    }

    return string.toString();
  }

  public boolean firstCharIsLetter(LexicalRangeState lexicalRangeState, String input) {
    return Character.isLetter(input.charAt(lexicalRangeState.getIndex()));
  }

  public boolean firstCharIsDigit(LexicalRangeState lexicalRangeState, String input) {
    return Character.isDigit(input.charAt(lexicalRangeState.getIndex()));
  }
}
