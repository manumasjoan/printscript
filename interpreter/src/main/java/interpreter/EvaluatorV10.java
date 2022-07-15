package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EvaluatorV10 extends Evaluator {

  public EvaluatorV10(PrintEmitter printEmitter, InputProvider inputProvider) {
    super(printEmitter, inputProvider);
    variablesWithValue = new HashMap<>();
    variableWithTypes = new HashMap<>();
    output = "";
  }

  public EvaluatorV10(Map<String, String> variablesWithValue) {
    super(variablesWithValue);
    this.variablesWithValue = variablesWithValue;
    variableWithTypes = new HashMap<>();
  }

  public EvaluatorV10() {
    super(new DefaultPrintEmitter(), new DefaultInputProvider());
    variablesWithValue = new HashMap<>();
    variableWithTypes = new HashMap<>();
    output = "";
  }

  public void assignVariable(String name) {
    variablesWithValue.put(name, output);
  }

  public boolean validateType(String type) {
    if (Objects.equals(type, "string")) {
      return isString(output);
    } else if (Objects.equals(type, "number")) {
      return isNumber(output);
    }
    return false;
  }
}
