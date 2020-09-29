package producing;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import common.Job;
import common.LoanApplication;
import common.LoanDetails;

import java.io.IOException;
import java.util.List;

public class ProducingWithGenerator {
    public static void main(String[] args) throws IOException {
        var loanApplication = ExampleLoan.LOAN_APPLICATION;
        System.out.println(loanApplication);
        System.out.println();
        toJsonString(loanApplication);
    }
    
    private static void toJsonString(LoanApplication loanApplication) throws IOException {
        JsonFactory jsonFactory = new JsonFactory();
        JsonGenerator generator = jsonFactory
                .createGenerator(System.out)
                .setPrettyPrinter(new DefaultPrettyPrinter());
        
        generator.writeStartObject();
        generator.writeStringField("name", loanApplication.getName());
        generator.writeStringField("purposeOfLoan", loanApplication.getPurposeOfLoan());
        toJsonString(generator, loanApplication.getLoanDetails());
        toJsonString(generator, loanApplication.getJobs());
        generator.writeEndObject();
        
        generator.flush();
    }
    
    private static void toJsonString(JsonGenerator generator, LoanDetails loanDetails) throws IOException {
        generator.writeFieldName("loanDetails");
        generator.writeStartObject();
        generator.writeNumberField("amount", loanDetails.getAmount());
        generator.writeStringField("startDate", loanDetails.getStartDate().toString());
        generator.writeStringField("endDate", loanDetails.getEndDate().toString());
        generator.writeEndObject();
    }
    
    private static void toJsonString(JsonGenerator generator, List<Job> jobs) throws IOException {
        generator.writeFieldName("jobs");
        generator.writeStartArray();
        for (final Job job : jobs) {
            generator.writeStartObject();
            generator.writeStringField("title", job.getTitle());
            generator.writeNumberField("annualIncome", job.getAnnualIncome());
            generator.writeNumberField("yearsActive", job.getYearsActive());
            generator.writeEndObject();
        }
        generator.writeEndArray();
    }
    
}
