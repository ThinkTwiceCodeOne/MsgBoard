package com.blue.code.msgBoard.dao;
import java.sql.*;
import java.util.*;
import com.blue.code.msgBoard.beans.*;
import com.blue.code.msgBoard.dto.*;
public class BranchDAO
{
public int addBranch(BranchBean branchBean) throws DAOException
{
int generatedCode=0;
try
{
System.out.println("inside add branch "+branchBean.getTitle());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("insert into branch(title) values(?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,branchBean.getTitle());
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
public void updateBranch(BranchBean branchBean) throws DAOException
{
try
{
System.out.println("inside update branch "+branchBean.getTitle()+" "+branchBean.getCode());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("update branch set title=? where code=?");
preparedStatement.setString(1,branchBean.getTitle());
preparedStatement.setInt(2,branchBean.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("inside catch block of add method");
throw new DAOException(exception.getMessage());
}
}
public void deleteBranch(BranchBean branchBean) throws DAOException
{
try
{
System.out.println("inside delete branch "+branchBean.getTitle()+" "+branchBean.getCode());
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("delete from student_branch_mapping where branch_code=?");
preparedStatement.setInt(1,branchBean.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from branch where code=?");
preparedStatement.setInt(1,branchBean.getCode());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
System.out.println("inside catch block of add method");
System.out.println(exception);
throw new DAOException(exception.getMessage());
}
}
public ArrayList<BranchMaster> getBranches() throws DAOException
{
ArrayList<BranchMaster> branches;
try
{
branches=new ArrayList<BranchMaster>();
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from branch");
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
BranchMaster branchMaster=new BranchMaster();
branchMaster.setCode(resultSet.getInt("code"));
branchMaster.setTitle(resultSet.getString("title"));
branches.add(branchMaster);
}
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception exception)
{
throw new DAOException(exception.getMessage());
}
return branches;
} 
}
