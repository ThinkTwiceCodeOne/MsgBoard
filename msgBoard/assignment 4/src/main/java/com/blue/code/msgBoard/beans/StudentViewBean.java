package com.blue.code.msgBoard.beans;
public class StudentViewBean
{
String rollNumber;
String firstName;
String lastName;
String emailID;
SemesterBean semesterBean;
BranchBean branchBean;
public StudentViewBean()
{
this.rollNumber=null;
this.firstName=null;
this.lastName=null;
this.emailID=null;
this.semesterBean=null;
this.branchBean=null;
}
public void setRollNumber(String rollNumber)
{
this.rollNumber=rollNumber;
}
public String getRollNumber()
{
return this.rollNumber;
}
public void setFirstName(String firstName)
{
this.firstName=firstName;
}
public String getFirstName()
{
return this.firstName;
}
public void setLastName(String lastName)
{
this.lastName=lastName;
}
public String getLastName()
{
return this.lastName;
}
public void setEmailID(String emailID)
{
this.emailID=emailID;
}
public String getEmailID()
{
return this.emailID;
}
public void setSemesterBean(SemesterBean semesterBean)
{
this.semesterBean=semesterBean;
}
public SemesterBean getSemesterBean()
{
return this.semesterBean;
}
public void setBranchBean(BranchBean branchBean)
{
this.branchBean=branchBean;
}
public BranchBean getBranchBean()
{
return this.branchBean;
}
}
