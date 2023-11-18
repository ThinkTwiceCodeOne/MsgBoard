package com.blue.code.msgBoard.services;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.blue.code.msgBoard.beans.*;
import com.blue.code.msgBoard.dao.*;
import com.blue.code.msgBoard.dto.*;
import com.blue.code.msgBoard.utils.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;
@Controller
public class Administration
{
@Autowired
private DatabaseBean databaseBean;
@Autowired
private MessageBoardBean messageBoardBean;
@GetMapping("/admin")
public String adminIndex()
{
if(databaseBean.getDriver()==null) return "Installer";
return "AdminIndex";
}
@PostMapping("/install")
public String installMessageBoard(InstallationBean installationBean)
{
try
{
DatabaseUtility.createTables(installationBean.getDriver(),installationBean.getConnectionString(),installationBean.getUsername(),installationBean.getPassword());
DAOConnection.driver=installationBean.getDriver();
DAOConnection.connectionString=installationBean.getConnectionString();
DAOConnection.username=installationBean.getUsername();
DAOConnection.password=installationBean.getPassword();
Connection connection=DAOConnection.getConnection();
Administrator administrator=new Administrator();
administrator.setUsername(installationBean.getAdministratorUsername());
administrator.setPassword(installationBean.getAdministratorPassword());
String secretKey=EncryptionUtility.getKey();
String passwordKey=EncryptionUtility.encrypt(installationBean.getAdministratorPassword(),secretKey);
System.out.println(passwordKey);
administrator.setPasswordKey(passwordKey);
AdministratorDAO administratorDAO=new AdministratorDAO();
administratorDAO.add(administrator);
return "InstallationSuccessful";
}catch(Exception exception)
{
System.out.println(exception);
return "InstallationFailed";
}
}
@ResponseBody
@PostMapping("/addBranch")
public ActionResponse addBranch(BranchBean branchBean)
{
System.out.println("inside add branch");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<BranchMaster> branches=messageBoardBean.getBranches();
System.out.println(branchBean.getTitle());
for(int i=0;i<branches.size();i++)
{
System.out.println(branches.get(i).getTitle());
if(branchBean.getTitle().equalsIgnoreCase(branches.get(i).getTitle()))
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Branch already exist");
actionResponse.setResult(Integer.valueOf(0));
flag=1;
break;
}
}
if(flag==1) return actionResponse;
else
{
actionResponse=new ActionResponse();
BranchDAO branchDAO=new BranchDAO();
int code=branchDAO.addBranch(branchBean);
BranchMaster branchMaster=new BranchMaster();
branchMaster.setCode(code);
branchMaster.setTitle(branchBean.getTitle());
branches.add(branchMaster);
messageBoardBean.setBranches(branches);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(Integer.valueOf(code));
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(Integer.valueOf(0));
}
return actionResponse;
}
@ResponseBody
@PostMapping("/updateBranch")
public ActionResponse updateBranch(BranchBean branchBean)
{
System.out.println("inside update branch");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<BranchMaster> branches=messageBoardBean.getBranches();
System.out.println(branchBean.getTitle());
for(int i=0;i<branches.size();i++)
{
System.out.println(branches.get(i).getTitle());
if(branchBean.getTitle().equalsIgnoreCase(branches.get(i).getTitle()))
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Branch already exist");
actionResponse.setResult("");
flag=1;
break;
}
}
if(flag==1) return actionResponse;
else
{
actionResponse=new ActionResponse();
BranchDAO branchDAO=new BranchDAO();
branchDAO.updateBranch(branchBean);
for(int i=0;i<branches.size();i++)
{
if(branchBean.getCode()==branches.get(i).getCode())
{
branches.get(i).setTitle(branchBean.getTitle());
break;
}
}
messageBoardBean.setBranches(branches);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("");
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@PostMapping("/deleteBranch")
public ActionResponse deleteBranch(BranchBean branchBean)
{
System.out.println("inside delete branch");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<BranchMaster> branches=messageBoardBean.getBranches();
System.out.println(branchBean.getTitle());
for(int i=0;i<branches.size();i++)
{
System.out.println(branches.get(i).getTitle());
if(branchBean.getTitle().equalsIgnoreCase(branches.get(i).getTitle()))
{
flag=1;
}
}
if(flag==0)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Branch does not exist");
actionResponse.setResult("");
}
else
{
actionResponse=new ActionResponse();
BranchDAO branchDAO=new BranchDAO();
branchDAO.deleteBranch(branchBean);
for(int i=0;i<branches.size();i++)
{
if(branchBean.getCode()==branches.get(i).getCode())
{
branches.remove(i);
break;
}
}
messageBoardBean.setBranches(branches);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("");
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getBranch")
public ActionResponse getBranch()
{
System.out.println("inside get branch");
ActionResponse actionResponse=null;
try
{
actionResponse=new ActionResponse();
BranchDAO branchDAO=new BranchDAO();
ArrayList<BranchMaster> branches=branchDAO.getBranches();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(branches);
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@PostMapping("/addSemester")
public ActionResponse addSemester(SemesterBean semesterBean)
{
System.out.println("inside add Semester");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<SemesterMaster> semesters=messageBoardBean.getSemesters();
System.out.println(semesterBean.getTitle());
for(int i=0;i<semesters.size();i++)
{
System.out.println(semesters.get(i).getTitle());
if(semesterBean.getTitle().equalsIgnoreCase(semesters.get(i).getTitle()))
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Semester already exist");
actionResponse.setResult(Integer.valueOf(0));
flag=1;
break;
}
}
if(flag==1) return actionResponse;
else
{
actionResponse=new ActionResponse();
SemesterDAO semesterDAO=new SemesterDAO();
int code=semesterDAO.addSemester(semesterBean);
SemesterMaster semesterMaster=new SemesterMaster();
semesterMaster.setCode(code);
semesterMaster.setTitle(semesterBean.getTitle());
semesters.add(semesterMaster);
messageBoardBean.setSemesters(semesters);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(Integer.valueOf(code));
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(Integer.valueOf(0));
}
return actionResponse;
}
@ResponseBody
@PostMapping("/updateSemester")
public ActionResponse updateSemester(SemesterBean semesterBean)
{
System.out.println("inside update semester");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<SemesterMaster> semesters=messageBoardBean.getSemesters();
System.out.println(semesterBean.getTitle());
for(int i=0;i<semesters.size();i++)
{
System.out.println(semesters.get(i).getTitle());
if(semesterBean.getTitle().equalsIgnoreCase(semesters.get(i).getTitle()))
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Semester already exist");
actionResponse.setResult("");
flag=1;
break;
}
}
if(flag==1) return actionResponse;
else
{
actionResponse=new ActionResponse();
SemesterDAO semesterDAO=new SemesterDAO();
semesterDAO.updateSemester(semesterBean);
for(int i=0;i<semesters.size();i++)
{
if(semesterBean.getCode()==semesters.get(i).getCode())
{
semesters.get(i).setTitle(semesterBean.getTitle());
break;
}
}
messageBoardBean.setSemesters(semesters);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("");
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@PostMapping("/deleteSemester")
public ActionResponse deleteSemester(SemesterBean semesterBean)
{
System.out.println("inside delete semester");
ActionResponse actionResponse=null;
try
{
int flag=0;
ArrayList<SemesterMaster> semesters=messageBoardBean.getSemesters();
System.out.println(semesterBean.getTitle());
for(int i=0;i<semesters.size();i++)
{
System.out.println(semesters.get(i).getTitle());
if(semesterBean.getTitle().equalsIgnoreCase(semesters.get(i).getTitle()))
{
flag=1;
}
}
if(flag==0)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException("Semester does not exist");
actionResponse.setResult("");
}
else
{
actionResponse=new ActionResponse();
SemesterDAO semesterDAO=new SemesterDAO();
semesterDAO.deleteSemester(semesterBean);
for(int i=0;i<semesters.size();i++)
{
if(semesterBean.getCode()==semesters.get(i).getCode())
{
semesters.remove(i);
break;
}
}
messageBoardBean.setSemesters(semesters);
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("");
}
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getSemester")
public ActionResponse getSemester()
{
System.out.println("inside get semester");
ActionResponse actionResponse=null;
try
{
actionResponse=new ActionResponse();
SemesterDAO semesterDAO=new SemesterDAO();
ArrayList<SemesterMaster> semesters=semesterDAO.getSemesters();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(semesters);
}catch(Exception exception)
{
System.out.println(exception);
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult("");
}
return actionResponse;
}
@ResponseBody
@PostMapping("/addStudent")
public ActionResponse addStudent(StudentBean studentBean)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setRollNumber(studentBean.getRollNumber());
studentDTO.setFirstName(studentBean.getFirstName());
studentDTO.setLastName(studentBean.getLastName());
studentDTO.setEmailID(studentBean.getEmailID());
studentDTO.setPassword(studentBean.getPassword());
studentDTO.setSemesterCode(studentBean.getSemesterCode());
studentDTO.setBranchCode(studentBean.getBranchCode());
studentDAO.add(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("Student added");
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@PostMapping("/updateStudent")
public ActionResponse updateStudent(StudentBean studentBean)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setRollNumber(studentBean.getRollNumber());
studentDTO.setFirstName(studentBean.getFirstName());
studentDTO.setLastName(studentBean.getLastName());
studentDTO.setEmailID(studentBean.getEmailID());
studentDTO.setPassword(studentBean.getPassword());
studentDTO.setSemesterCode(studentBean.getSemesterCode());
studentDTO.setBranchCode(studentBean.getBranchCode());
studentDAO.update(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult("Student updated");
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@PostMapping("/deleteStudent")
public ActionResponse deleteStudent(StudentBean studentBean)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setRollNumber(studentBean.getRollNumber());
studentDTO.setFirstName(studentBean.getFirstName());
studentDTO.setLastName(studentBean.getLastName());
studentDTO.setEmailID(studentBean.getEmailID());
studentDTO.setPassword(studentBean.getPassword());
studentDTO.setSemesterCode(studentBean.getSemesterCode());
studentDTO.setBranchCode(studentBean.getBranchCode());
studentDAO.delete(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");   
actionResponse.setResult("Student deleted");
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getStudents")
public ActionResponse getStudents()
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
ArrayList<StudentViewBean> result=studentDAO.getStudents();
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(result);
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getStudentsByRollNumber")
public ActionResponse getStudentsByRollNumber(String rollNumber)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setRollNumber(rollNumber);
ArrayList<StudentViewBean> result=studentDAO.getStudentsByRollNumber(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(result);
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getStudentsByBranchCode")
public ActionResponse getStudentsByBranchCode(int branchCode)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setBranchCode(branchCode);
ArrayList<StudentViewBean> result=studentDAO.getStudentsByBranchCode(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(result);
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getStudentsBySemesterCode")
public ActionResponse getStudentsBySemesterCode(int semesterCode)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setSemesterCode(semesterCode);
ArrayList<StudentViewBean> result=studentDAO.getStudentsBySemesterCode(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(result);
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/getStudentsByBranchAndSemester")
public ActionResponse getStudentsByBranchAndSemester(int semesterCode,int branchCode)
{
ActionResponse actionResponse=null;
try
{
StudentDAO studentDAO=new StudentDAO();
StudentDTO studentDTO=new StudentDTO();
studentDTO.setSemesterCode(semesterCode);
studentDTO.setBranchCode(branchCode);
ArrayList<StudentViewBean> result=studentDAO.getStudentsByBranchAndSemester(studentDTO);
actionResponse=new ActionResponse();
actionResponse.setSuccess(true);
actionResponse.setException("");
actionResponse.setResult(result);
}catch(Exception exception)
{
actionResponse=new ActionResponse();
actionResponse.setSuccess(false);
actionResponse.setException(exception.getMessage());
actionResponse.setResult(null);
}
return actionResponse;
}
@ResponseBody
@GetMapping("/testA")
public String methodA(@RequestParam("aaa") String g,HttpServletRequest rq)
{
HttpSession session=rq.getSession();
session.setAttribute("aaaa",g);
return g;
}
@ResponseBody
@GetMapping("/testB")
public String methodB(HttpSession ss)
{
String k=(String)ss.getAttribute("aaaa");
return k+k+k;
}
@ResponseBody
@GetMapping("/testC")
public void methodC(HttpSession ss)
{
ss.invalidate();
}
@PostMapping("/login")
public String login(AdministratorBean administratorBean,HttpServletRequest rq)
{
String secretKey=EncryptionUtility.getKey();
String passwordKey=EncryptionUtility.encrypt(administratorBean.getPassword(),secretKey);
System.out.println(passwordKey);
HttpSession session=rq.getSession();
System.out.println(administratorBean.getUsername());
System.out.println(administratorBean.getPassword());
AdministratorDAO administratorDAO=new AdministratorDAO();
Administrator administrator=new Administrator();
administrator.setUsername(administratorBean.getUsername());
administrator.setPassword(administratorBean.getPassword());
administrator.setPasswordKey(passwordKey);
boolean result=administratorDAO.login(administrator);
if(result==true) 
{
session.setAttribute("username",administratorBean.getUsername());
return "AdminHome";
}
return "AdminIndex";
}
@GetMapping("/logout")
public String logout(HttpSession ss)
{
ss.invalidate();
return "AdminIndex";
}
@GetMapping("/getTablesPage")
public String getTablesPage()
{
return "tables";
}
}
