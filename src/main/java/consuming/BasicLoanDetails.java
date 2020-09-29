package consuming;

public class BasicLoanDetails {
    private double amount;
    private String startDate;
    private String endDate;
    
    public double getAmount() {
        return amount;
    }
    
    public BasicLoanDetails setAmount(double amount) {
        this.amount = amount;
        return this;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public BasicLoanDetails setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public BasicLoanDetails setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
    
    @Override
    public String toString() {
        return "LoanDetails{" +
               "amount=" + amount +
               ", startDate='" + startDate + '\'' +
               ", endDate='" + endDate + '\'' +
               '}';
    }
}
