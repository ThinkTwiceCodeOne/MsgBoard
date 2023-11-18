package com.blue.code.msgBoard.dto;
public class ActionResponse
{
public boolean success;
public String exception;
public Object result;
public ActionResponse()
{
this.success=false;
this.exception=null;
this.result=null;
}
public void setSuccess(boolean success)
{
this.success=success;
}
public boolean getSuccess()
{
return this.success;
}
public void setException(String exception)
{
this.exception=exception;
}
public String getException()
{
return this.exception;
}
public void setResult(Object result)
{
this.result=result;
}
public Object getResult()
{
return this.result;
}
}
