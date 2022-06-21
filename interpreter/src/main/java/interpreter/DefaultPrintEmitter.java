package interpreter;

public class DefaultPrintEmitter implements PrintEmitter {

  @Override
  public void print(String message) {
    System.out.println(message);
  }
}
