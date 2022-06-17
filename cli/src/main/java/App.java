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

      execute(content, mode);
    }
  }

  private static void execute(String content, String mode) throws Exception {
    switch (mode) {
      case "validate" -> validate(content);
      case "execute" -> execute(content);
      case "exit" -> System.exit(0);
      default -> System.out.println("Error: Invalid mode");
    }
  }

  private static void execute(String content) throws Exception {
    PrintlnResult printlnResult = compile(content);
    System.out.println(printlnResult.getContent());
  }

  private static PrintlnResult compile(String content) throws Exception {
    LexerBuilder lexerBuilder = new LexerBuilder();
    Lexer lexer = lexerBuilder.buildLexer1();
    List<Token> tokens = lexer.getTokens(content);

    DefaultParser parser = new DefaultParser(TokenIterator.Companion.create(content, tokens));
    NodeGroupResult node = parser.createNode();

    DefaultInterpreter interpreter = new DefaultInterpreter(new NodeGroupVisitor());
    return interpreter.interpret(node);
  }

  private static String askForInput(String message) {
    System.out.println(message);
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }

  private static void validate(String content) {
    try {
      compile(content);
      System.out.println("Valid");
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
