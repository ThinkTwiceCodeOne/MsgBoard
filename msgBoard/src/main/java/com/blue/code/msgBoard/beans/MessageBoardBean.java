package com.blue.code.msgBoard.beans;
import java.io.*;
import java.util.*;
import com.blue.code.msgBoard.dto.*;
public class MessageBoardBean
{
public ArrayList<BranchMaster> branches;
public ArrayList<SemesterMaster> semesters;
public MessageBoardBean()
{
this.branches=new ArrayList<>();
this.semesters=new ArrayList<>();
}
public void setBranches(ArrayList<BranchMaster> branches)
{
this.branches=branches;
}
public ArrayList<BranchMaster> getBranches()
{
return this.branches;
}
public void setSemesters(ArrayList<SemesterMaster> semesters)
{
this.semesters=semesters;
}
public ArrayList<SemesterMaster> getSemesters()
{
return this.semesters;
}
}
