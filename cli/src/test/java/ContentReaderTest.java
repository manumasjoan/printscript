import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ContentReaderTest {

    //test read method
    @Test
    public void testRead() {
        ContentReader reader = new ContentReader();
        //String url = getClass().getResource("/test.txt").getPath();
        String result = reader.readContent("test.txt");
        assertEquals("Hello World!", result);
    }
}
