public class InterpreterBuilder {

  public static Interpreter buildInterpreter_V10() {
    return new DefaultInterpreter(new NodeGroupVisitorV10(new EvaluatorV10()));
  }

  public static Interpreter buildInterpreter_V11() {
    return new DefaultInterpreter(new NodeGroupVisitorV11(new EvaluatorV11()));
  }

  public static Interpreter buildInterpreter_V10(
      PrintEmitter printEmitter, InputProvider inputProvider) {
    return new DefaultInterpreter(
        new NodeGroupVisitorV10(new EvaluatorV10(printEmitter, inputProvider)));
  }

  public static Interpreter buildInterpreter_V11(
      PrintEmitter printEmitter, InputProvider inputProvider) {
    return new DefaultInterpreter(
        new NodeGroupVisitorV11(new EvaluatorV11(printEmitter, inputProvider)));
  }
}
