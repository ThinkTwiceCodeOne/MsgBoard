package com.blue.code.msgBoard.dto;
public class SemesterMaster
{
public int code;
public String title;
public SemesterMaster()
{
this.code=0;
this.title="";
}
public void setCode(int code)
{
this.code=code;
}
public int getCode()
{
return this.code;
}
public void setTitle(String title)
{
this.title=title;
}
public String getTitle()
{
return this.title;
}
}
