package consuming;

import common.Job;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class BasicLoanApplication {
    private String name;
    private String purposeOfLoan;
    private String comments;
    private BasicLoanDetails loanInfo;
    private BasicLoanDetails loanDetails;
    private List<Job> jobs;
    
    public String getName() {
        return name;
    }
    
    public BasicLoanApplication setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }
    
    public BasicLoanApplication setPurposeOfLoan(String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
        return this;
    }
    
    public BasicLoanDetails getLoanDetails() {
        return loanDetails;
    }
    
    public BasicLoanApplication setLoanDetails(BasicLoanDetails loanDetails) {
        this.loanDetails = loanDetails;
        return this;
    }
    
    public List<Job> getJobs() {
        return jobs;
    }
    
    public BasicLoanApplication setJobs(List<Job> jobs) {
        this.jobs = jobs;
        return this;
    }
    
    @Override
    public String toString() {
        return "LoanApplication{" +
               "name='" + name + '\'' +
               ", purposeOfLoan='" + purposeOfLoan + '\'' +
               ", loanDetails=\n\t" + loanDetails +
               ", jobs=\n\t" + jobs.stream().map(Job::toString).collect(joining("\n\t\t", "[\n\t\t", "\n\t]")) +
               '}';
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(final String comments) {
        this.comments = comments;
    }
    
    public BasicLoanDetails getLoanInfo() {
        return loanInfo;
    }
    
    public void setLoanInfo(final BasicLoanDetails loanInfo) {
        this.loanInfo = loanInfo;
    }
}
