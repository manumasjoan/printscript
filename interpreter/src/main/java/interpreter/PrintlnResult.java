package interpreter;

public class PrintlnResult {
  private StringBuilder content = new StringBuilder();

  public void addContent(String moreText) {
    content.append(moreText);
    content.append("\n");
  }

  public String getContent() {
    String contentString = content.toString();
    contentString = contentString.substring(0, contentString.length() - 1);
    return contentString;
  }
}
