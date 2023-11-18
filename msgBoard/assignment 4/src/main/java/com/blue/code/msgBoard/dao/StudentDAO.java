package com.blue.code.msgBoard.dao;
import java.sql.*;
import com.blue.code.msgBoard.dto.*;
import com.blue.code.msgBoard.utils.*;
import com.blue.code.msgBoard.beans.*;
import java.util.*;
import java.io.*;
public class StudentDAO
{
public void add(StudentDTO studentDTO) throws DAOException
{
try
{
String secretKey=EncryptionUtility.getKey();
String passwordKey=EncryptionUtility.encrypt(studentDTO.getPassword(),secretKey);
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("insert into student (roll_number,first_name,last_name,email_id,password,password_key) values(?,?,?,?,?,?)");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.setString(2,studentDTO.getFirstName());
preparedStatement.setString(3,studentDTO.getLastName());
preparedStatement.setString(4,studentDTO.getEmailID());
preparedStatement.setString(5,studentDTO.getPassword());
preparedStatement.setString(6,passwordKey);
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into student_branch_mapping(roll_number,branch_code)values(?,?)");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.setInt(2,studentDTO.getBranchCode());
System.out.println(studentDTO.getBranchCode());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into student_semester_mapping(roll_number,semester_code)values(?,?)");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.setInt(2,studentDTO.getSemesterCode());
System.out.println(studentDTO.getSemesterCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println(exception);
throw new DAOException(exception.getMessage());
}
}
public void update(StudentDTO studentDTO) throws DAOException
{
try
{
String secretKey=EncryptionUtility.getKey();
String passwordKey=EncryptionUtility.encrypt(studentDTO.getPassword(),secretKey);
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("update student_branch_mapping set branch_code=? where roll_number=?");
preparedStatement.setInt(1,studentDTO.getBranchCode());
preparedStatement.setString(2,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update student_semester_mapping set semester_code=? where roll_number=?");
preparedStatement.setInt(1,studentDTO.getSemesterCode());
preparedStatement.setString(2,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update student set first_name=?,last_name=?,email_id=?,password=?,password_key=? where roll_number=?");
preparedStatement.setString(1,studentDTO.getFirstName());
preparedStatement.setString(2,studentDTO.getLastName());
preparedStatement.setString(3,studentDTO.getEmailID());
preparedStatement.setString(4,studentDTO.getPassword());
preparedStatement.setString(5,passwordKey);
preparedStatement.setString(6,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public void delete(StudentDTO studentDTO) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("delete from student_branch_mapping where roll_number=?");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from student_semester_mapping where roll_number=?");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from student where roll_number=?");
preparedStatement.setString(1,studentDTO.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
}
public ArrayList<StudentViewBean> getStudents() throws DAOException
{
ArrayList<StudentViewBean> result;
try
{
result=new ArrayList<>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String sqlStatement="SELECT student.roll_number, student.first_name, student.last_name, student.email_id,branch.code AS branch_code, branch.title AS branch_name,semester.code AS semester_code, semester.title AS semester_name FROM student JOIN student_branch_mapping ON student.roll_number = student_branch_mapping.roll_number JOIN branch ON student_branch_mapping.branch_code = branch.code JOIN student_semester_mapping ON student.roll_number = student_semester_mapping.roll_number JOIN semester ON student_semester_mapping.semester_code = semester.code";
preparedStatement=connection.prepareStatement(sqlStatement);
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
StudentViewBean studentViewBean=new StudentViewBean();
studentViewBean.setRollNumber(resultSet.getString("roll_number"));
studentViewBean.setFirstName(resultSet.getString("first_name"));
studentViewBean.setLastName(resultSet.getString("last_name"));
studentViewBean.setEmailID(resultSet.getString("email_id"));
BranchBean branchBean=new BranchBean();
branchBean.setTitle(resultSet.getString("branch_name"));
branchBean.setCode(resultSet.getInt("branch_code"));
studentViewBean.setBranchBean(branchBean);
SemesterBean semesterBean=new SemesterBean();
semesterBean.setTitle(resultSet.getString("semester_name"));
semesterBean.setCode(resultSet.getInt("semester_code"));
studentViewBean.setSemesterBean(semesterBean);
result.add(studentViewBean);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return result;
}
public ArrayList<StudentViewBean> getStudentsByRollNumber(StudentDTO studentDTO) throws DAOException
{
ArrayList<StudentViewBean> result;
try
{
result=new ArrayList<>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String sqlStatement="SELECT student.roll_number, student.first_name, student.last_name, student.email_id,branch.code AS branch_code, branch.title AS branch_name,semester.code AS semester_code, semester.title AS semester_name FROM student JOIN student_branch_mapping ON student.roll_number = student_branch_mapping.roll_number JOIN branch ON student_branch_mapping.branch_code = branch.code JOIN student_semester_mapping ON student.roll_number = student_semester_mapping.roll_number JOIN semester ON student_semester_mapping.semester_code = semester.code where student.roll_number=?";
preparedStatement=connection.prepareStatement(sqlStatement);
preparedStatement.setString(1,studentDTO.getRollNumber());
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
StudentViewBean studentViewBean=new StudentViewBean();
studentViewBean.setRollNumber(resultSet.getString("roll_number"));
studentViewBean.setFirstName(resultSet.getString("first_name"));
studentViewBean.setLastName(resultSet.getString("last_name"));
studentViewBean.setEmailID(resultSet.getString("email_id"));
BranchBean branchBean=new BranchBean();
branchBean.setTitle(resultSet.getString("branch_name"));
branchBean.setCode(resultSet.getInt("branch_code"));
studentViewBean.setBranchBean(branchBean);
SemesterBean semesterBean=new SemesterBean();
semesterBean.setTitle(resultSet.getString("semester_name"));
semesterBean.setCode(resultSet.getInt("semester_code"));
studentViewBean.setSemesterBean(semesterBean);
result.add(studentViewBean);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return result;
}
public ArrayList<StudentViewBean> getStudentsByBranchCode(StudentDTO studentDTO) throws DAOException
{
ArrayList<StudentViewBean> result;
try
{
result=new ArrayList<>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String sqlStatement="SELECT student.roll_number, student.first_name, student.last_name, student.email_id,branch.code AS branch_code, branch.title AS branch_name,semester.code AS semester_code, semester.title AS semester_name FROM student JOIN student_branch_mapping ON student.roll_number = student_branch_mapping.roll_number JOIN branch ON student_branch_mapping.branch_code = branch.code JOIN student_semester_mapping ON student.roll_number = student_semester_mapping.roll_number JOIN semester ON student_semester_mapping.semester_code = semester.code where branch.code=?";
preparedStatement=connection.prepareStatement(sqlStatement);
preparedStatement.setInt(1,studentDTO.getBranchCode());
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
StudentViewBean studentViewBean=new StudentViewBean();
studentViewBean.setRollNumber(resultSet.getString("roll_number"));
studentViewBean.setFirstName(resultSet.getString("first_name"));
studentViewBean.setLastName(resultSet.getString("last_name"));
studentViewBean.setEmailID(resultSet.getString("email_id"));
BranchBean branchBean=new BranchBean();
branchBean.setTitle(resultSet.getString("branch_name"));
branchBean.setCode(resultSet.getInt("branch_code"));
studentViewBean.setBranchBean(branchBean);
SemesterBean semesterBean=new SemesterBean();
semesterBean.setTitle(resultSet.getString("semester_name"));
semesterBean.setCode(resultSet.getInt("semester_code"));
studentViewBean.setSemesterBean(semesterBean);
result.add(studentViewBean);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return result;
}
public ArrayList<StudentViewBean> getStudentsBySemesterCode(StudentDTO studentDTO) throws DAOException
{
ArrayList<StudentViewBean> result;
try
{
result=new ArrayList<>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String sqlStatement="SELECT student.roll_number, student.first_name, student.last_name, student.email_id,branch.code AS branch_code, branch.title AS branch_name,semester.code AS semester_code, semester.title AS semester_name FROM student JOIN student_branch_mapping ON student.roll_number = student_branch_mapping.roll_number JOIN branch ON student_branch_mapping.branch_code = branch.code JOIN student_semester_mapping ON student.roll_number = student_semester_mapping.roll_number JOIN semester ON student_semester_mapping.semester_code = semester.code where semester.code=?";
preparedStatement=connection.prepareStatement(sqlStatement);
preparedStatement.setInt(1,studentDTO.getSemesterCode());
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
StudentViewBean studentViewBean=new StudentViewBean();
studentViewBean.setRollNumber(resultSet.getString("roll_number"));
studentViewBean.setFirstName(resultSet.getString("first_name"));
studentViewBean.setLastName(resultSet.getString("last_name"));
studentViewBean.setEmailID(resultSet.getString("email_id"));
BranchBean branchBean=new BranchBean();
branchBean.setTitle(resultSet.getString("branch_name"));
branchBean.setCode(resultSet.getInt("branch_code"));
studentViewBean.setBranchBean(branchBean);
SemesterBean semesterBean=new SemesterBean();
semesterBean.setTitle(resultSet.getString("semester_name"));
semesterBean.setCode(resultSet.getInt("semester_code"));
studentViewBean.setSemesterBean(semesterBean);
result.add(studentViewBean);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return result;
}
public ArrayList<StudentViewBean> getStudentsByBranchAndSemester(StudentDTO studentDTO) throws DAOException
{
ArrayList<StudentViewBean> result;
try
{
result=new ArrayList<>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
String sqlStatement="SELECT student.roll_number, student.first_name, student.last_name, student.email_id,branch.code AS branch_code, branch.title AS branch_name,semester.code AS semester_code, semester.title AS semester_name FROM student JOIN student_branch_mapping ON student.roll_number = student_branch_mapping.roll_number JOIN branch ON student_branch_mapping.branch_code = branch.code JOIN student_semester_mapping ON student.roll_number = student_semester_mapping.roll_number JOIN semester ON student_semester_mapping.semester_code = semester.code where branch.code=? and semester.code=?";
preparedStatement=connection.prepareStatement(sqlStatement);
preparedStatement.setInt(1,studentDTO.getBranchCode());
preparedStatement.setInt(2,studentDTO.getSemesterCode());
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
StudentViewBean studentViewBean=new StudentViewBean();
studentViewBean.setRollNumber(resultSet.getString("roll_number"));
studentViewBean.setFirstName(resultSet.getString("first_name"));
studentViewBean.setLastName(resultSet.getString("last_name"));
studentViewBean.setEmailID(resultSet.getString("email_id"));
BranchBean branchBean=new BranchBean();
branchBean.setTitle(resultSet.getString("branch_name"));
branchBean.setCode(resultSet.getInt("branch_code"));
studentViewBean.setBranchBean(branchBean);
SemesterBean semesterBean=new SemesterBean();
semesterBean.setTitle(resultSet.getString("semester_name"));
semesterBean.setCode(resultSet.getInt("semester_code"));
studentViewBean.setSemesterBean(semesterBean);
result.add(studentViewBean);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return result;
}
}
