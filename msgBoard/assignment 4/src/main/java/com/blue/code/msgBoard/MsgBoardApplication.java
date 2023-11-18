package com.blue.code.msgBoard;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.google.gson.*;
import com.blue.code.msgBoard.beans.*;
import com.blue.code.msgBoard.dao.*;
import com.blue.code.msgBoard.dto.*;
import java.io.*;
import java.util.*;
import java.sql.*;
@SpringBootApplication
public class MsgBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsgBoardApplication.class, args);
	}
@Bean
public DatabaseBean getDatabaseBean()
{
System.out.println("getDatabaseBean got called");
DatabaseBean databaseBean=null;
try
{
File file=new File("conf"+File.separator+"db.json");
if(file.exists())
{
Gson gson=new Gson();
databaseBean=gson.fromJson(new FileReader(file.getAbsolutePath()),DatabaseBean.class);
}
else
{
databaseBean=new DatabaseBean();
} 
}catch(Exception exception)
{
System.out.println(exception);
}
return databaseBean;
}
@Bean
public MessageBoardBean getMessageBoardBean(DatabaseBean databaseBean)
{
System.out.println("MessageBoardBean got called");
MessageBoardBean messageBoardBean=null;
try
{
DAOConnection.driver=databaseBean.getDriver();
DAOConnection.connectionString=databaseBean.getConnectionString();
DAOConnection.username=databaseBean.getUsername();
DAOConnection.password=databaseBean.getPassword();
messageBoardBean=new MessageBoardBean();
BranchDAO branchDAO=new BranchDAO();
SemesterDAO semesterDAO=new SemesterDAO();
ArrayList<BranchMaster> branches=branchDAO.getBranches();
ArrayList<SemesterMaster> semesters=semesterDAO.getSemesters();
messageBoardBean.setBranches(branches);
messageBoardBean.setSemesters(semesters);
}catch(Exception exception)
{
System.out.println(exception);
}
return messageBoardBean;
}
}
