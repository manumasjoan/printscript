package parser;

public interface Parser<Node> {
  Node createNode() throws Exception;
}
