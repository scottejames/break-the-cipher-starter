import java.io.IOException;
import java.util.Arrays;

/** Demo runner — runs minWordsToDecode on one example case and prints the result. */
public class Main {
    public static void main(String[] args) throws IOException {
        TestData.TestCase c = TestData.loadCase(TestData.TEST_DATA_DIR + "/medium/01_trap.txt");

        System.out.println("Case: " + c.name);
        System.out.println("Message: " + c.message);
        System.out.println("Dictionary: " + Arrays.toString(c.dictionary));

        int result = BreakCipher.minWordsToDecode(c.message, c.dictionary);
        System.out.println("Minimum words to decode: " + result);
    }
}
