import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Loads Break the Cipher test cases from ../test_data/{simple,medium,hard}/*.txt
 * (relative to the java/ directory — the scripts in java/scripts/ always run
 * from there).
 *
 * File format:
 *
 *     name=&lt;case name&gt;
 *     expected=&lt;int&gt;
 *     message=&lt;string&gt;
 *     dictionary=
 *     &lt;word 0&gt;
 *     &lt;word 1&gt;
 *     ...
 */
public class TestData {

    static final String TEST_DATA_DIR = "../test_data";

    static class TestCase {
        final String name;
        final String message;
        final String[] dictionary;
        final int expected;

        TestCase(String name, String message, String[] dictionary, int expected) {
            this.name = name;
            this.message = message;
            this.dictionary = dictionary;
            this.expected = expected;
        }
    }

    static TestCase loadCase(String path) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        String name = null;
        String message = null;
        int expected = 0;
        int dictionaryStart = -1;

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.equals("dictionary=")) {
                dictionaryStart = i + 1;
                break;
            }
            int eq = line.indexOf('=');
            String key = line.substring(0, eq);
            String value = line.substring(eq + 1);
            if (key.equals("name")) {
                name = value;
            } else if (key.equals("expected")) {
                expected = Integer.parseInt(value);
            } else if (key.equals("message")) {
                message = value;
            }
        }

        List<String> dictionaryLines = lines.subList(dictionaryStart, lines.size());
        return new TestCase(name, message, dictionaryLines.toArray(new String[0]), expected);
    }

    static List<TestCase> loadTier(String tier) throws IOException {
        File dir = new File(TEST_DATA_DIR, tier);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        List<TestCase> cases = new ArrayList<>();
        if (files != null) {
            Arrays.sort(files);
            for (File f : files) {
                cases.add(loadCase(f.getPath()));
            }
        }
        return cases;
    }
}
