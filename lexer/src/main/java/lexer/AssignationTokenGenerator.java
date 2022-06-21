package lexer;

import java.util.List;
import org.austral.ingsis.printscript.common.Token;

public class AssignationTokenGenerator extends TokenGenerator {

  private List<String> assignations;

  public AssignationTokenGenerator(List<String> assignations) {
    this.assignations = assignations;
  }

  @Override
  public TokenGeneratorResult read(LexicalRangeState lexicalRangeState, String input) {
    if (!isAssignation(lexicalRangeState, input))
      return new TokenGeneratorResult(lexicalRangeState);

    Token token = createToken(DefaultTokenTypes.ASSIGN, lexicalRangeState, 1);
    LexicalRangeState newState = updateState(lexicalRangeState, 1);

    return new TokenGeneratorResult(token, newState);
  }

  private boolean isAssignation(LexicalRangeState lexicalRangeState, String input) {
    String followingChar = String.valueOf(input.charAt(lexicalRangeState.getIndex()));
    return assignations.contains(followingChar);
  }
}
