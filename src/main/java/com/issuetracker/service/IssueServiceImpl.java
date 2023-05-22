package com.issuetracker.service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.time.LocalDate;
import java.util.LinkedList;
import com.issuetracker.dao.IssueDAO;
import com.issuetracker.dao.IssueDAOImpl;
import com.issuetracker.exception.IssueTrackerException;
import com.issuetracker.model.Assignee;
import com.issuetracker.model.Issue;
import com.issuetracker.model.IssueReport;
import com.issuetracker.model.IssueStatus;
import com.issuetracker.validator.Validator;

public class IssueServiceImpl implements IssueService
{
    private AssigneeService assigneeService = new AssigneeServiceImpl();

    private IssueDAO issueDAO = new IssueDAOImpl();
    
    private Validator validator = new Validator();
    /**
     * @params
     *         issue - The new issue to be reported
     * 
     * @operation Reports a new issue by
     *            * validating its details
     *            * fetch an assignee
     *            * persists the issue in the issueList
     * 
     * @returns
     *          String - The issue id
     */
    @Override
    public String reportAnIssue(Issue issue) throws IssueTrackerException
    {
	// Your Code Goes Here
        validator.validate(issue);
        List<Assignee> assignee = assigneeService.fetchAssignee(issue.getIssueUnit());
        if(!assignee.isEmpty()) {
           //assignee.get(0).;
            issue.setAssigneeEmail(assignee.get(0).getAssigneeEmail());
            assigneeService.updateActiveIssueCount(assignee.get(0).getAssigneeEmail(), 'I');
            
        }
        String issueId = issueDAO.reportAnIssue(issue);
        if(issueId == "" ||issueId == null) {
            throw new IssueTrackerException("An issue with the same ID already exists!");
        }
	return issueId;
    }

    /**
     * @params
     *         issueId - The issue id
     *         status - The new status
     * 
     * @operation Updates the status of the given issue with the given status
     * 
     * @returns
     *          Boolean - Result of the status update
     */
    @Override
    public Boolean updateStatus(String issueId,
				IssueStatus status) throws IssueTrackerException
    {
	// Your Code Goes Here
        Issue issue = issueDAO.getIssueById(issueId);
            if(issue == null) {
        	throw new IssueTrackerException("An issue with the given ID is not found!");
            }
            else if(issue.getStatus()== status) {
        	throw new IssueTrackerException("There is no change in the issue status!");
            }
            else if( status == IssueStatus.RECALLED && issue.getStatus() != IssueStatus.OPEN) {
        	throw new IssueTrackerException("The current issue status is incompatible for recall!");
            }
            else {
        	issueDAO.updateStatus(issue, status);
        	if(status != IssueStatus.OPEN || status!= IssueStatus.IN_PROGRESS) {
        	    assigneeService.updateActiveIssueCount(issue.getAssigneeEmail(), 'D');  
        	}
            }
	return true;
    }

    /**
     * @params
     *         filterCriteria - A map where its
     *         key denotes an attribute of the issue object
     *         value contains the filter value
     * 
     * @operation Generates a report of issues based on the filter criteria
     * 
     * @returns
     *          List<IssueReport> - The list of filtered issue objects
     */
    @Override
    public List<IssueReport> showIssues(Map<Character, Object> filterCriteria) throws IssueTrackerException
    {
	// Your Code Goes Here
	List<Issue> issues = issueDAO.getIssueList();
	List<IssueReport> report = new LinkedList<>();
	Set<Entry<Character, Object>> filterSet = filterCriteria.entrySet();
	
	for(Entry<Character, Object> obj : filterSet ) {
	if(obj.getKey()=='A') {
	    Object email = obj.getValue();
	    for(Issue i : issues) {
		if(String.valueOf(i.getAssigneeEmail()).equals(email)) {
		    report.add(new IssueReport(i.getIssueId(),i.getIssueDescription(),i.getAssigneeEmail(),i.getStatus()));
		}
	    }
	}
	else {
	    String status = (String) obj.getValue();
	    System.out.println(status);
	    for(Issue i : issues) {
		
		if(String.valueOf(i.getStatus()) == (status)) {
		    //System.out.println(i.getStatus());
		    report.add(new IssueReport(i.getIssueId(),i.getIssueDescription(),i.getAssigneeEmail(),i.getStatus()));
	        }
	    }
	}
	}
	if(report.isEmpty())
	    throw new IssueTrackerException("No issues are found for the requested criteria!");
	return report;
    }

    /**
     * @operation Deletes the issue object which are resolved or closed,
     *            at least 14 days ago
     * 
     * @returns
     *          List<Issue> - The list of issues which had been deleted
     */
    @Override
    public List<Issue> deleteIssues() throws IssueTrackerException
    {
	// Your Code Goes Here
        List<Issue> issues = issueDAO.getIssueList();
        List<Issue> oldIssues = new LinkedList<>();
        for(Issue i : issues) {
            if((i.getStatus() == IssueStatus.RESOLVED || i.getStatus()==IssueStatus.CLOSED)  && ( i.getUpdatedOn()!=null&&(i.getUpdatedOn().isBefore(LocalDate.now().minusDays(13)))) ){
        	
        	oldIssues.add(i);
            }
        }
        if(oldIssues.isEmpty())
            throw new IssueTrackerException("No issues are old enough to be cleared!");
        issues.removeAll(oldIssues);
	return oldIssues;
    }
}