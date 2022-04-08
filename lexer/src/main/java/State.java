import lombok.Getter;

@Getter
public class State {
  private int index;
  private int line;
  private int column;

  public State() {
    this.line = 0;
    this.column = 0;
    this.index = 0;
  }

  public void updateState(int index, int line, int column) {
    this.index = index;
    this.line = line;
    this.column = column;
  }
}
