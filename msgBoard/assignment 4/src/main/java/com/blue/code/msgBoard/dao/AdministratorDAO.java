package com.blue.code.msgBoard.dao;
import java.sql.*;
import com.blue.code.msgBoard.dto.*;
import com.blue.code.msgBoard.utils.*;
public class AdministratorDAO
{
public void add(Administrator administrator) throws DAOException
{
try
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("insert into administrator values(?,?,?)");
preparedStatement.setString(1,administrator.getUsername());
preparedStatement.setString(2,administrator.getPassword());
preparedStatement.setString(3,administrator.getPasswordKey());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(SQLException sqlException)
{
throw new DAOException("Unable to add administrator");
}
}
public boolean login(Administrator administrator)
{
try
{
System.out.println(administrator.getPasswordKey());
int flag=0;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from administrator");
ResultSet resultSet=preparedStatement.executeQuery();
while(resultSet.next())
{
if(administrator.getUsername().equals(resultSet.getString("username")) && administrator.getPassword().equals(resultSet.getString("password")))
{
flag=1;
break;
}
}
if(flag==1) return true;
return false;
}catch(Exception exception)
{
System.out.println(exception);
}
return false;
}
}
