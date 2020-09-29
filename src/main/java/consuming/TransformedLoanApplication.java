package consuming;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.Job;
import common.LoanDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class TransformedLoanApplication {
    @JsonProperty("name")
    private String applicantName;
    private String purposeOfLoan;
    private LoanDetails loanDetails;
    private Map<String, Job> jobs;
    
    public String getApplicantName() {
        return applicantName;
    }
    
    public TransformedLoanApplication setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
        return this;
    }
    
    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }
    
    public TransformedLoanApplication setPurposeOfLoan(final String purposeOfLoan) {
        this.purposeOfLoan = purposeOfLoan;
        return this;
    }
    
    public LoanDetails getLoanDetails() {
        return loanDetails;
    }
    
    public TransformedLoanApplication setLoanDetails(final LoanDetails loanDetails) {
        this.loanDetails = loanDetails;
        return this;
    }
    
    @JsonIgnore
    public Map<String, Job> getJobs() {
        return jobs;
    }
    
    @JsonIgnore
    public TransformedLoanApplication setJobs(final Map<String, Job> jobs) {
        this.jobs = jobs;
        return this;
    }
    
    @JsonProperty("jobs")
    public List<Job> getJobsJson() {
        return new ArrayList<>(jobs.values());
    }
    
    @JsonProperty("jobs")
    public TransformedLoanApplication setJobsJson(final List<Job> jobs) {
        this.jobs = jobs
                .stream()
                .collect(toMap(Job::getTitle, job -> job));
        return this;
    }
    
    @Override
    public String toString() {
        return "LoanApplication{" +
               "applicantName='" + applicantName + '\'' +
               ", purposeOfLoan='" + purposeOfLoan + '\'' +
               ", loanDetails=\n\t" + loanDetails +
               ", jobs=\n\t" + jobs +
               '}';
    }
}
