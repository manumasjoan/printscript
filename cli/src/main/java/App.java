import ast.node.NodeGroupResult;
import java.util.List;
import java.util.Scanner;
import org.austral.ingsis.printscript.common.Token;
import org.austral.ingsis.printscript.parser.TokenIterator;

public class App {

  public static void main(String[] args) throws Exception {

    String fileName = "";
    String content = "";
    String mode = "";

    fileName = askForInput("Enter file name: ");

    try {
      ContentReader contentReader = new ContentReader();
      content = contentReader.readContent(fileName);

    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
      fileName = askForInput("Enter file name: ");
    }

    while (true) {

      mode = askForInput("Enter mode: ");

      run(content, mode);
    }
  }

  private static void run(String content, String mode) throws Exception {
    String version = "";
    version = askForInput("Enter version: ");

    switch (mode) {
      case "validate" -> validate(content, version);
      case "execute" -> execute(content, version);
      case "exit" -> System.exit(0);
      default -> System.out.println("Error: Invalid mode");
    }
  }

  private static void execute(String content, String version) throws Exception {
    PrintlnResult printlnResult = compile(content, version);
    System.out.println(printlnResult.getContent());
  }

  private static PrintlnResult compile(String content, String version) throws Exception {

    LexerBuilder lexerBuilder = new LexerBuilder();
    ParserBuilder parserBuilder = new ParserBuilder();
    InterpreterBuilder interpreterBuilder = new InterpreterBuilder();

    switch (version) {
      case "1.0" -> {
        Lexer lexer = lexerBuilder.buildLexer_V10();
        List<Token> tokens = lexer.getTokens(content);
        DefaultParser parser =
            parserBuilder.buildParser_V10(TokenIterator.Companion.create(content, tokens));
        NodeGroupResult node = parser.createNode();
        Interpreter interpreter = interpreterBuilder.buildInterpreter_V10();
        return interpreter.interpret(node);
      }
      case "1.1" -> {
        Lexer lexer = lexerBuilder.buildLexer_V11();
        List<Token> tokens = lexer.getTokens(content);
        DefaultParser parser =
            parserBuilder.buildParser_V11(TokenIterator.Companion.create(content, tokens));
        NodeGroupResult node = parser.createNode();
        Interpreter interpreter = interpreterBuilder.buildInterpreter_V11();
        return interpreter.interpret(node);
      }
      default -> {
        System.out.println("Invalid version");
        return null;
      }
    }
  }

  private static String askForInput(String message) {
    System.out.println(message);
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private static void validate(String content, String version) {
    try {
      compile(content, version);
      System.out.println("Valid");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
