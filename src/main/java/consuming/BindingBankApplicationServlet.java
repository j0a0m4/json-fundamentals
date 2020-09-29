package consuming;

import com.fasterxml.jackson.databind.ObjectMapper;
import common.Job;
import common.SimpleJettyService;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BindingBankApplicationServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        
        try (final var inputStream = req.getInputStream();
             final var outputStream = resp.getOutputStream()) {
            
            final var loanApplication = objectMapper
                    .readValue(inputStream, BasicLoanApplication.class);
            
            final var totalIncome = getTotalIncome(loanApplication);
            final var amount = getAmount(loanApplication);
            
            processResponse(totalIncome, amount, resp, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void processResponse(double totalIncome, double amount,
                                 HttpServletResponse resp,
                                 ServletOutputStream outputStream)
            throws IOException {
        
        final var approved = isApproved(totalIncome, amount);
        final var status = processResStatus(approved);
        final var message = processResMessage(approved);
        
        outputStream.println("Total income = " + totalIncome);
        outputStream.println("Amount = " + amount);
        
        resp.setStatus(status);
        outputStream.println(message);
    }
    
    private String processResMessage(boolean approved) {
        return approved
               ? "Approved!"
               : "Denied";
    }
    
    private int processResStatus(boolean approved) {
        return approved
               ? HttpServletResponse.SC_OK
               : HttpServletResponse.SC_FORBIDDEN;
    }
    
    private boolean isApproved(double totalIncome, double amount) {
        return amount <= 3 * totalIncome;
    }
    
    private double getAmount(final BasicLoanApplication basicLoanApplication) {
        return basicLoanApplication
                .getLoanDetails()
                .getAmount();
    }
    
    private double getTotalIncome(final BasicLoanApplication basicLoanApplication) {
        return basicLoanApplication
                .getJobs()
                .stream()
                .parallel()
                .mapToDouble(Job::getAnnualIncome)
                .sum();
    }
    
    public static void main(String[] args) {
        SimpleJettyService.run(DomBankApplicationServlet.class);
    }
}

