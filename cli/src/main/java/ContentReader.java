import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ContentReader {

    public String readContent(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            String url = getClass().getResource(fileName).getPath();
            BufferedReader br = new BufferedReader(new FileReader(url));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
