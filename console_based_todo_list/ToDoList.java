package todolist;

import java.util.*;
import java.sql.*;
import java.util.Scanner;
class ToDoList
{
    public static void main(String args[])
    {
        Scanner sc=new Scanner(System.in);
        System.out.print("If you are an existing user Please enter 1 otherwise enter 0\n");

        int haveAccount=sc.nextInt();
        
        if(haveAccount==1)
        {
            login();
            //taskManager();
        }
        else
        {
            signIn();
            //taskManager();
        }
    }

    static void  login()
    {
    	Scanner sc=new Scanner(System.in);
         System.out.print("Enter your phone number\n");
            String phNo=sc.nextLine();
            if(phNo.length()!=10)
            {
                System.out.println("Please enter a valid phone number\n");
                login();
            }
            for(int i=0;i<10;i++)
            {
                if(phNo.charAt(i)>='0' && phNo.charAt(i)<='9')
                {
                continue;
                }
                else
                {
                System.out.println("Please enter a valid phone number\n");
                login();
                }
            }

            System.out.print("\nEnter your Name\n");
            String name=sc.nextLine();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
            Statement stmt=con.createStatement();
            ResultSet res=stmt.executeQuery("select * from todolistlogin where phNo='"+phNo+"' and name='"+name+"'");
            if(res.next()!=false)
            {
            System.out.println("successfullt login...\n");
            int id=res.getInt(3);
            taskManager(id);
            }
            else
            {
                System.out.println("Account not exists\n");
                System.out.println("Enter details correctly\n");
                login();

            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    static void signIn()
    {
    	 Scanner sc=new Scanner(System.in);
         System.out.print("Enter your phone number\n");
            String phNo=sc.nextLine();
            if(phNo.length()!=10)
            {
                System.out.println("Please enter a valid phone number\n");
                signIn();
            }
            for(int i=0;i<10;i++)
            {
                if(phNo.charAt(i)>='0' && phNo.charAt(i)<='9')
                {
                continue;
                }
                else
                {
                System.out.println("Please enter a valid phone number\n");
                signIn();
                }
            }

            System.out.print("\nEnter your Name\n");
            String name=sc.nextLine();
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
            Statement stmt=con.createStatement();
            int count=stmt.executeUpdate("insert into todolistlogin(phNo,name) values('"+phNo+"','"+name+"')");
            //ResultSet res=stmt.executeQuery("select * from todolistlogin where phNo='"+phNo+"' and name='"+name+"'");
            if(count!=0)
            {
            System.out.println("Account created successfully ...\n");
            //ResultSet res=stmt.executeQuery("select * from todolistlogin where phNo='"+phNo+"' and name='"+name+"," );
            //ResultSet res=stmt.executeQuery("select * from todolistlogin where phNo='"+phNo+"' and name='"+name+"'");
            //int id=res.getInt(3);
            login();
            }
            else
            {
                System.out.println("Account not created successfullt\n");
                signIn();

            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }

    }

    static void taskManager(int id)
    {
    	Scanner sc=new Scanner(System.in);
    	System.out.println("WELCOME TO TASK MANAGER.................\n"); 
        int selectNum=2;
        while(selectNum!=0)
        {
            System.out.println("0-->Terminate 1-->Add Task	2.Delete Task	3.Update Task	4.View Task	 5-->login  6-->signin\n");
            System.out.println("Please Enter number...\n");
            selectNum=sc.nextInt();
            switch(selectNum)
            {
                case 1: addTask(id);
                        break;
                case 2: deleteTask(id);
                        break;
                case 3: editTask(id);
                        break;
                case 4: viewTask(id);
                        break;
                case 5: login();
                		break;
                case 6: signIn();
                		break;
            }

        }
    }

	private static void editTask(int id) 
	{
		System.out.println("Enter Your TaskId to update task");
		Scanner sc=new Scanner(System.in);
		int taskId=sc.nextInt();sc.nextLine();
		System.out.println("Enter Your updated task");
		String updatedTask=sc.nextLine();
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
			//PreparedStatement stmt=con.prepareStatement("update taskManager taskData=? where taskId=?");
			Statement stmt=con.createStatement();
			int result=stmt.executeUpdate("update taskManager set taskData='"+updatedTask+"' where taskId='"+taskId+"'");
			if(result!=0)
			{
				System.out.println("Successfully updated your task");
			}
			else
			{
				System.out.println("Entered TaskId deoes not exists");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
			taskManager(id);
		}
		
	}

	private static void viewTask(int id) 
	{
		System.out.println("\n LIST OF TASKS...");
		try
		{
			  Class.forName("com.mysql.cj.jdbc.Driver");
	          Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
	          Statement stmt=con.createStatement();
	          ResultSet res=stmt.executeQuery("select * from taskManager where id='"+id+"'");
	          System.out.println("    TaskId            TaskData");
	          System.out.println("---------------------------------------------");
	          while(res.next())
	          {
	        	  System.out.println("    "+res.getInt(3)+"          "+res.getString(2));
	          }
	          System.out.println("\n\n\n");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

	private static void deleteTask(int id)
	{
		System.out.println("Enter taskId to remove task...\n");
		Scanner sc=new Scanner(System.in);
		int taskId=sc.nextInt();
		try
		{
		       Class.forName("com.mysql.cj.jdbc.Driver");
	           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
	           Statement stmt=con.createStatement();
	           int count=stmt.executeUpdate("delete from taskManager where taskId='"+taskId+"'");
	           if(count!=0)
	        	{
	        	   System.out.println("Successfully Removed Your Task\n");
	        	}
	           else
	           {
	        	   System.out.println("Some Error ocuured\n");
	           }
		}
		catch(Exception e)
		{
			System.out.println(e);
	           
	        	   
		}
		
		
	}

	private static void addTask(int id)
	{
		System.out.println("NOTE YOUR TASK...............\n");
		Scanner sc=new Scanner(System.in);
		String taskData=sc.nextLine();
		try
		{
		       Class.forName("com.mysql.cj.jdbc.Driver");
	           Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root","Naidu@1234");
	           Statement stmt=con.createStatement();
	           int count=stmt.executeUpdate("Insert into taskManager(id,taskData) values('"+id+"','"+taskData+"')");
	           if(count!=0)
	        	{
	        	   System.out.println("Successfully Noted Your Task\n");
	        	   System.out.println("If you want add another task Please Enter 1 else 0 \n");
	        	   if(sc.nextInt()==1)
	        	   {
	        		   addTask(id);
	        	   }
	        	}
	           else
	           {
	        	   System.out.println("Some Error ocuured\n");
	           }
		}
		catch(Exception e)
		{
			System.out.println(e);
	           
	        	   
		}
		
	}
}
