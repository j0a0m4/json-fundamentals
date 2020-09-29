package consuming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;

public class ConsumingWithDomApi {
    private static final File BANK_LOAN_FILE =
            new File("src/main/resources/bank_loan.json");
    
    public static void main(String[] args) throws IOException {
        final var jsonNode = new ObjectMapper()
                .readTree(BANK_LOAN_FILE);
        
        validateDates(jsonNode);
    }
    
    private static void validateDates(JsonNode jsonNode) {
        final var fields = jsonNode.fields();
        while (fields.hasNext()) {
            final var field = fields.next();
            final var fieldName = field.getKey();
            final var childNode = field.getValue();
            
            if (childNode.isTextual() && fieldName.endsWith("Date")) {
                System.out.println("Fount date field: " + fieldName);
                try {
                    DateTimeFormatter.ISO_LOCAL_DATE
                            .parse(childNode.asText());
                } catch (DateTimeException e) {
                    e.printStackTrace();
                }
            } else {
                validateDates(childNode);
            }
        }
    }
}