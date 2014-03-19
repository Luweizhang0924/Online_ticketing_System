package cs425project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Scanner;

public class MemberBook {
	public int userid;
	public int bookpoints;

	public MemberBook() {
		bookpoints=0;
	}
	public void memberbook(){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
         Statement s=c.createStatement();
         
       
         /**
          * show theatre and movie schedule
          */
     	System.out.println("Theatre and Movie information:");
     	System.out.println("theatre id, Screenroom number, time, movie title,theatre's location:");
     	ResultSet r1=s.executeQuery("select PlaySchedule.tid,PlaySchedule.sid, PlaySchedule.time,PlaySchedule.mtitle,Theatre.location from PlaySchedule,Theatre where PlaySchedule.tid=Theatre.id");        	
     	while(r1.next())
     	{System.out.println(r1.getString("PlaySchedule.tid")+"  "+r1.getString("PlaySchedule.sid")+"  "+r1.getString("PlaySchedule.time")+"  "+r1.getString("PlaySchedule.mtitle")+"  "+r1.getString("Theatre.location")+"\n");}
     /**
      * book tickets
      */
    	 System.out.println("please choose the movie schedule you want to watch");
     	 Scanner input1=new Scanner(System.in);
     	 System.out.println("choose the theatre id:");
     	 int tempid=Integer.parseInt(input1.nextLine());
     	 System.out.println("choose the screenroom number");
     	 int tempsid=Integer.parseInt(input1.nextLine());
     	 System.out.println("choose the movie title:");
     	 String temptitle=input1.nextLine();
     	 System.out.println("input the quantity of tickets you want book");
     	 int tquantity=Integer.parseInt(input1.nextLine());
     	 
     	 Date now=new Date();
     	 DateFormat d1 = DateFormat.getDateTimeInstance();
     	 String time=d1.format(now);
     	 
     	 String insert2="insert into MemberBook values ('"+userid+"','"+tempid+"','"+tempsid+"','"+temptitle+"','"+time+"','"+tquantity+"')";
     	            
     	 s.executeUpdate(insert2);
     	 System.out.println("you have booked tickets successfully!"+"\n");	   
         
     	
		
         s.close();  
         c.close();
         
         }
		 catch(Exception e){e.printStackTrace();}
		
	}
}

