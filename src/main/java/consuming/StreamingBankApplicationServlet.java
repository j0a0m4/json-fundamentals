package consuming;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import common.SimpleJettyService;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StreamingBankApplicationServlet extends HttpServlet {
    private final JsonFactory jsonFactory = new JsonFactory();
    private double totalIncome = 0;
    private double amount = 0;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        
        try (final var inputStream = req.getInputStream();
             final var outputStream = resp.getOutputStream()) {
            
            processInput(inputStream);
            processOutput(resp, outputStream,
                          this.totalIncome, this.amount);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void processOutput(HttpServletResponse resp,
                               ServletOutputStream outputStream,
                               double totalIncome, double amount)
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
    
    private void processInput(ServletInputStream inputStream) throws IOException {
        final var parser = jsonFactory.createParser(inputStream);
        
        JsonToken token;
        while ((token = parser.nextToken()) != null) {
            final var currentName = parser.getCurrentName();
            if (currentName != null && token.isNumeric()) {
                updateValues(parser, currentName);
            }
        }
    }
    
    private void updateValues(JsonParser parser, String currentName) throws IOException {
        switch (currentName) {
            case "annualIncome":
                this.totalIncome += parser.getDoubleValue();
                break;
            case "amount":
                this.amount = parser.getDoubleValue();
                break;
        }
    }
    
    private boolean isApproved(double totalIncome, double amount) {
        return amount <= 3 * totalIncome;
    }
    
    public static void main(String[] args) {
        SimpleJettyService.run(StreamingBankApplicationServlet.class);
    }
}
