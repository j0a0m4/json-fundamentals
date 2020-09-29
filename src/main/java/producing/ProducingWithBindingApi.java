package producing;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.LoanApplication;

import java.io.IOException;

public class ProducingWithBindingApi {
    public static void main(String[] args) throws IOException {
        LoanApplication loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        
        toJsonString(loanApplication);
    }
    
    private static void toJsonString(LoanApplication loanApplication) throws IOException {
        var objectMapper = new ObjectMapper();
        var objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        
        objectWriter.writeValue(System.out, loanApplication);
    }
    
}
