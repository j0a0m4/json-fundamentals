package consuming;

import com.fasterxml.jackson.core.JsonFactory;

import java.io.File;
import java.io.IOException;

public class ConsumingWithStreamingApi {
    private static final File BANK_LOAN_FILE =
            new File("src/main/resources/bank_loan.json");
    
    public static void main(String[] args) throws IOException {
        final var factory = new JsonFactory();
        
        try (final var parser = factory.createParser(BANK_LOAN_FILE)) {
            
            while (parser.nextToken() != null) {
                final var currentName = parser.getCurrentName();
                if (currentName != null) {
                    final var text = parser.getText();
                    System.out.printf("%s: %s%n", currentName, text);
                }
            }
            
        }
    }
}
