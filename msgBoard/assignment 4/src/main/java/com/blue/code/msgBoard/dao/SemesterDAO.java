package com.blue.code.msgBoard.dao;
import java.sql.*;
import java.util.*;
import com.blue.code.msgBoard.beans.*;
import com.blue.code.msgBoard.dto.*;
public class SemesterDAO
{
public int addSemester(SemesterBean semesterBean) throws DAOException
{
int generatedCode=0;
try
{
System.out.println("inside add semester "+semesterBean.getTitle());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("insert into semester(title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,semesterBean.getTitle());
int affectedRows=preparedStatement.executeUpdate();
if(affectedRows>0)
{
ResultSet generatedKeys=preparedStatement.getGeneratedKeys();
if(generatedKeys.next())
{
generatedCode=generatedKeys.getInt(1);
System.out.println(generatedCode);
}
}
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("inside catch block of add method");
throw new DAOException(exception.getMessage());
}
return generatedCode;
}
public void updateSemester(SemesterBean semesterBean) throws DAOException
{
try
{
System.out.println("inside update semester "+semesterBean.getTitle()+" "+semesterBean.getCode());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("update semester set title=? where code=?");
preparedStatement.setString(1,semesterBean.getTitle());
preparedStatement.setInt(2,semesterBean.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("inside catch block of add method");
throw new DAOException(exception.getMessage());
}
}
public void deleteSemester(SemesterBean semesterBean) throws DAOException
{
try
{
System.out.println("inside delete semester "+semesterBean.getTitle()+" "+semesterBean.getCode());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("delete from semester where code=?");
preparedStatement.setInt(1,semesterBean.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("inside catch block of add method");
throw new DAOException(exception.getMessage());
}
}
public ArrayList<SemesterMaster> getSemesters() throws DAOException
{
ArrayList<SemesterMaster> semesters;
try
{
semesters=new ArrayList<SemesterMaster>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from semester");
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
SemesterMaster semesterMaster=new SemesterMaster();
semesterMaster.setCode(resultSet.getInt("code"));
semesterMaster.setTitle(resultSet.getString("title"));
semesters.add(semesterMaster);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return semesters;
} 
}
