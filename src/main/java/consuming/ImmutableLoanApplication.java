package consuming;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.Job;
import common.LoanDetails;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class ImmutableLoanApplication {
    private final String name;
    private final String purposeOfLoan;
    private final LoanDetails loanDetails;
    private final List<Job> jobs;
    
    @JsonCreator
    public ImmutableLoanApplication(
            @JsonProperty("name") final String name,
            @JsonProperty("purposeOfLoan") final String purposeOfLoan,
            @JsonProperty("loanDetails") final LoanDetails loanDetails,
            @JsonProperty("jobs") final List<Job> jobs) {
        this.name = name;
        this.purposeOfLoan = purposeOfLoan;
        this.loanDetails = loanDetails;
        this.jobs = List.copyOf(jobs);
    }
    
    public String getName() {
        return name;
    }
    
    public String getPurposeOfLoan() {
        return purposeOfLoan;
    }
    
    public LoanDetails getLoanDetails() {
        return loanDetails;
    }
    
    public List<Job> getJobs() {
        return jobs;
    }
    
    @Override
    public String toString() {
        return "ImmutableLoanApplication{" +
               "name='" + name + '\'' +
               ", purposeOfLoan='" + purposeOfLoan + '\'' +
               ", loanDetails=\n\t" + loanDetails +
               ", jobs=\n\t" + jobs
                       .stream()
                       .map(Job::toString)
                       .collect(joining("\n\t\t",
                                        "[\n\t\t",
                                        "\n\t]")) +
               '}';
    }
}
