package com.blue.code.msgBoard.beans;
public class StudentBean
{
public String rollNumber;
public String firstName;
public String lastName;
public String emailID;
public String password;
public int semesterCode;
public int branchCode;
public StudentBean()
{
this.rollNumber=null;
this.firstName=null;
this.lastName=null;
this.emailID=null;
this.password=null;
this.semesterCode=0;
this.branchCode=0;
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
public void setPassword(String password)
{
this.password=password;
}
public String getPassword()
{
return this.password;
}
public void setSemesterCode(int semesterCode)
{
this.semesterCode=semesterCode;
}
public int getSemesterCode()
{
return this.semesterCode;
}
public void setBranchCode(int branchCode)
{
this.branchCode=branchCode;
}
public int getBranchCode()
{
return this.branchCode;
}
}
