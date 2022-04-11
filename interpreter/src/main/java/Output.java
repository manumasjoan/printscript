public class Output {
    StringBuilder text = new StringBuilder();
    public void addText(String moreText){
        text.append(moreText);
    }
    public String getText(){
        return text.toString();
    }
}
