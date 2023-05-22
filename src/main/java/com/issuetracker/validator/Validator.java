package com.issuetracker.validator;

import java.time.LocalDate;

import com.issuetracker.exception.IssueTrackerException;
import com.issuetracker.model.Issue;
import com.issuetracker.model.IssueStatus;

//Do Not Change Any Signature
public class Validator
{
 public void validate(Issue issue) throws IssueTrackerException
 {
	// Your Code Goes Here
     if(!isValidIssueId(issue.getIssueId())) {
	 throw new IssueTrackerException("The issue ID is of invalid format!");
     }
     else if(!isValidIssueDescription(issue.getIssueDescription())) {
	 throw new IssueTrackerException("The issue description is of unacceptable format!");
     }
     else if(!isValidReportedOn(issue.getReportedOn())) {
	 throw new IssueTrackerException("The reported date is incorrect!");
     }
     else if(!isValidStatus(issue.getStatus())) {
	 throw new IssueTrackerException("The status of the issue is inappropriate!");
     }
 }

 public Boolean isValidIssueId(String issueId)
 {
	// Your Code Goes Here
     String regex = "((MTI-I-([0-9]){3})-(LS|MS|HS))";
      if(issueId.matches(regex)) {
	  return true;
      }
      return false;
}

 public Boolean isValidIssueDescription(String issueDescription)
 {
	// Your Code Goes Here
     String regex = "[A-Z]((\\s)?[a-z]+)*";
     if(issueDescription.matches(regex))
	return true;
     return false;
 }

 public Boolean isValidReportedOn(LocalDate reportedOn)
 {
	// Your Code Goes Here
     LocalDate currentDate = LocalDate.now();
       if(currentDate.isAfter(reportedOn))
	return true;
       return false;
 }

 public Boolean isValidStatus(IssueStatus status)
 {
	// Your Code Goes Here
        if(status == IssueStatus.OPEN || status == IssueStatus.IN_PROGRESS)
            return true;
	return false;
 }
}