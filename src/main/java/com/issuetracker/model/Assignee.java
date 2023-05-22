package com.issuetracker.model;

import java.time.LocalDate;

// Do Not Change Any Signature
public class Assignee
{
    
    // Your Code Goes Here
    
    private String assigneeId;
    private String assigneeName;
    private String assigneeEmail;
    private Unit workingUnit;
    private LocalDate employeeSince;
    private Integer numberOfIssuesActive;
    
    public Assignee(String assigneeId, String assigneeName,
		    String assigneeEmail, Unit workingUnit,
		    LocalDate employeeSince, Integer numberOfIssuesActive)
    {
	super();
	this.assigneeId = assigneeId;
	this.assigneeName = assigneeName;
	this.assigneeEmail = assigneeEmail;
	this.workingUnit = workingUnit;
	this.employeeSince = employeeSince;
	this.numberOfIssuesActive = numberOfIssuesActive;
    }
    
    public String getAssigneeId()
    {
        return assigneeId;
    }
    public void setAssigneeId(String assigneeId)
    {
        this.assigneeId = assigneeId;
    }
    public String getAssigneeName()
    {
        return assigneeName;
    }
    public void setAssigneeName(String assigneeName)
    {
        this.assigneeName = assigneeName;
    }
    public String getAssigneeEmail()
    {
        return assigneeEmail;
    }
    public void setAssigneeEmail(String assigneeEmail)
    {
        this.assigneeEmail = assigneeEmail;
    }
    public Unit getWorkingUnit()
    {
        return workingUnit;
    }
    public void setWorkingUnit(Unit workingUnit)
    {
        this.workingUnit = workingUnit;
    }
    public LocalDate getEmployeeSince()
    {
        return employeeSince;
    }
    public void setEmployeeSince(LocalDate employeeSince)
    {
        this.employeeSince = employeeSince;
    }
    public Integer getNumberOfIssuesActive()
    {
        return numberOfIssuesActive;
    }
    public void setNumberOfIssuesActive(Integer numberOfIssuesActive)
    {
        this.numberOfIssuesActive = numberOfIssuesActive;
    }
    
}