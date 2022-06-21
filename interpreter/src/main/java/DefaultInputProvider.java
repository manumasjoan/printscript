import java.util.Scanner;

public class DefaultInputProvider implements InputProvider {

  @Override
  public String input(String name) {
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
}
