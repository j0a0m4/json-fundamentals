package consuming;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;

public class AdvancedConsumingWithBindingApi {
    private static final File BANK_LOAN_FILE =
            new File("src/main/resources/bank_loan.json");
    
    public static void main(String[] args) throws IOException {
        final var mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        
        final var transformedLoanApplication = mapper
                .readValue(BANK_LOAN_FILE, TransformedLoanApplication.class);
        
        final var immutableLoanApplication = mapper
                .readValue(BANK_LOAN_FILE, ImmutableLoanApplication.class);
        
        System.out.println(transformedLoanApplication);
        System.out.println(immutableLoanApplication);
        
    }
}
