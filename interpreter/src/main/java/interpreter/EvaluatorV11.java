package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import parser.ast.node.ReadInput;

public class EvaluatorV11 extends Evaluator {

  private Map<String, Boolean> variableWithCanChange;

  public EvaluatorV11(PrintEmitter printEmitter, InputProvider inputProvider) {
    super(printEmitter, inputProvider);
    this.variableWithCanChange = new HashMap<>();
  }

  public EvaluatorV11() {
    super(new DefaultPrintEmitter(), new DefaultInputProvider());
    this.variableWithCanChange = new HashMap<>();
  }

  @Override
  public void declareCanChange(String name, boolean canChange) {
    variableWithCanChange.put(name, canChange);
  }

  public void assignVariable(String name) throws Exception {
    if (variablesWithValue.containsKey(name) && variablesWithValue.get(name) == null) {
      variablesWithValue.put(name, output);
      return;
    }

    //todo: aca esta el problema
    System.out.println("variables with value: "+ variablesWithValue);
    System.out.println("variables with type: "+ variableWithTypes);
    System.out.println("variables with can change: "+ variableWithCanChange);

    System.out.println("name: "+ name);
    System.out.println("puede cambiar?: "+ variableWithCanChange.get(name));

    if (variablesWithValue.containsKey(name) && variableWithCanChange.get(name)) {
      variablesWithValue.put(name, output);
    } else {
      throw new Exception("variable can't be changed");
    }
  }

  @Override
  public void visitReadInput(ReadInput readInput) {
    String inputText = readInput.getInput();
    printEmitter.print(inputText);
    output = "\"" + inputProvider.input(inputText) + "\"";
  }

  public boolean validateType(String type) {
    if (Objects.equals(type, "String")) {
      return isString(output);
    } else if (Objects.equals(type, "number")) {
      return isNumber(output);
    } else if (Objects.equals(type, "boolean")) {
      return output.equals("true") || output.equals("false");
    }
    return false;
  }
}
