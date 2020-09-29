package consuming;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.SimpleJettyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DomBankApplicationServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        
        try (final var inputStream = req.getInputStream();
             final var outputStream = resp.getOutputStream()) {
            
            final var loanApplication = objectMapper.readTree(inputStream);
            
            final var totalIncome = getTotalIncome(loanApplication);
            final var amount = getAmount(loanApplication);
            
            outputStream.println("Total income = " + totalIncome);
            outputStream.println("Amount = " + amount);
            
            if (isApproved(totalIncome, amount)) {
                resp.setStatus(HttpServletResponse.SC_OK);
                outputStream.println("Approved");
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                outputStream.println("Denied");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean isApproved(double totalIncome, double amount) {
        return amount <= 3 * totalIncome;
    }
    
    private double getAmount(final JsonNode loanApplication) {
        return Stream.of(loanApplication)
                     .map(loan -> loan.get("loanDetails"))
                     .map(loanDetails -> loanDetails.get("amount"))
                     .map(JsonNode::asDouble)
                     .findFirst().orElse(0.0);
    }
    
    private double getTotalIncome(final JsonNode loanApplication) {
        var jobs = loanApplication.get("jobs");
        return IntStream
                .range(0, jobs.size())
                .mapToObj(jobs::get)
                .map(job -> job.get("annualIncome"))
                .mapToDouble(JsonNode::asDouble)
                .reduce(0.0, Double::sum);
    }
    
    public static void main(String[] args) {
        SimpleJettyService.run(DomBankApplicationServlet.class);
    }
}
