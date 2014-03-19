package cs425project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Guest {
	private int emailId;
	private String fname,lname,cardnum,phonenum,location;

	public Guest() {
		// TODO Auto-generated constructor stub
	}
	public void guest(){
		System.out.println("\n"+"Notice: You can visit our web site, reads comments on thread just created, but you cannot enter a comment. Thank You");
	

		try{
				 Class.forName("com.mysql.jdbc.Driver");
	         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
	         Statement s=c.createStatement();
	         
	        
	         /**
	          * movie experience
	          */
	         System.out.println("The Movie Thread and Review");
	         /**
	          * show recent thread
	          */
	         System.out.println("\n"+"here are rencent threads of the movies:"+"\n");
	         ResultSet rmt=s.executeQuery("select thread,userid,mtitle from MovieStartThread");
	         ArrayList<String> list=new ArrayList<String>();
	         ArrayList<String> list2=new ArrayList<String>();
	         ArrayList<String> list3=new ArrayList<String>();
	         while(rmt.next())
	         {
	         	list.add(rmt.getString("thread"));
	         	list2.add(rmt.getString("userid"));
	         	list3.add(rmt.getString("mtitle"));
	         }
	         System.out.println("thread starter id: "+list2.get(list2.size()-1));
	         System.out.println("movie title: "+list3.get(list3.size()-1));
	         System.out.println("thread: "+list.get(list.size()-1));
	         
	         
	         /**
	          * show review of thread
	          */
	         System.out.println("\n"+"here are the reviews of threads:"+"\n");
	         ResultSet rmr=s.executeQuery("select userid,review from MovieReview where startuserid='"+list2.get(list2.size()-1)+"'and startmtitle='"+list3.get(list3.size()-1)+"'");
	         while(rmr.next())
	         {
	         	
	         	System.out.println("reviewer id:  "+rmr.getString("userid"));
	         	System.out.println("review of the thread:  "+rmr.getString("review")+"\n");
	         }
	         
	         /**
	          * theatre experience
	          */
	         System.out.println("Theatre Thread and Review");
	         /**
	          * show thread
	          */
	         System.out.println("\n"+"here are threads of the theatres:"+"\n");
	         ResultSet rtt=s.executeQuery("select thread,userid, tid from TheatreStartThread");
	         ArrayList<String> l=new ArrayList<String>();
	         ArrayList<String> l2=new ArrayList<String>();
	         ArrayList<String> l3=new ArrayList<String>();
	         while(rtt.next())
	         {
	         	l.add(rtt.getString("thread"));
	         	l2.add(rtt.getString("userid"));
	         	l3.add(rtt.getString("tid"));
	         }
	         System.out.println("thread starter id: "+l2.get(l2.size()-1));
	         System.out.println("theatre id: "+l3.get(l3.size()-1));
	         System.out.println("thread: "+l.get(l.size()-1));
	         
	         /**
	          * show review of thread
	          */
	         System.out.println("\n"+"here are the reviews of threads:"+"\n");
	         ResultSet rtr=s.executeQuery("select userid,review from TheatreReview where startuserid='"+l2.get(l2.size()-1)+"'and starttid='"+l3.get(l3.size()-1)+"'");
	         while(rtr.next())
	         {
	         
	         	System.out.println("reviewer id:  "+rtr.getString("userid"));
	         	System.out.println("review of the thread:  "+rtr.getString("review")+"\n");
	         }
	         
	         
	         /**
	          * book tickets
	          */ 
	     	Scanner input=new Scanner(System.in);
			System.out.println("please provide your personal information:");
			System.out.println("first name:");
			fname=input.nextLine();
			System.out.println("last name:");
			lname=input.nextLine();
			System.out.println("credit card number:");
			cardnum=input.nextLine();
			System.out.println("phone number:");
			phonenum=input.nextLine();
			System.out.println("emailId:");
			emailId=Integer.parseInt(input.nextLine());
			System.out.println("the location you want to watch the movie");
			location=input.nextLine();
	         ResultSet r=s.executeQuery("select emailId from Guest");
	         boolean find=false;
	         while(r.next())
	         {if(emailId==Integer.parseInt(r.getString("emailId")))
	               { find=true;}
	         }	         
	         
	      	 if(find==false){      
	      		 System.out.println("your emailId can be used");
	     	 String insert="insert into Guest values ('"+emailId+"','"+fname+"','"+lname+"','"+cardnum+"','"+phonenum+"','"+location+"')";
	     	 s.executeUpdate(insert);
	     	 ResultSet r1=s.executeQuery("select * from Guest where emailId='"+emailId+"'");
	     	 System.out.println("your personal information:");
	     	 while(r1.next())
	     	 {System.out.println("name: "+r1.getString("fname")+"  "+r1.getString("lname")+"\ncard number: "+r1.getString("cardnum")+"\nphone number: "+r1.getString("phonenum")+"\nemailId: "+r1.getString("emailId")+"\nthe location you want to see the movie: "+r1.getString("location"));}
	     	         
	     	         
	     	 System.out.println("the theatre id, the screenroom number, screenroom capacity, the time and movie name is:");
	     	 ResultSet r2=s.executeQuery("select PlaySchedule.tid,PlaySchedule.sid,Screen.capacity,PlaySchedule.time,PlaySchedule.mtitle from PlaySchedule,Screen where Screen.screenid=PlaySchedule.sid and Screen.tid=PlaySchedule.tid and PlaySchedule.tid=(select id from Theatre where location='"+location+"')");
                     
	     	 while(r2.next())
	     	 {
	     	   System.out.println(r2.getString("PlaySchedule.tid")+"  "+r2.getString("PlaySchedule.sid")+"  "+r2.getString("Screen.capacity")+"  "+r2.getString("PlaySchedule.time")+"  "+r2.getString("PlaySchedule.mtitle"));
	     	  }
	     	 System.out.println("please choose the movie schedule you want to watch");
	     	 Scanner input1=new Scanner(System.in);
	     	 System.out.println("choose the theatre id:");
	     	 int tempid=Integer.parseInt(input1.nextLine());
	     	 System.out.println("choose the screenroom number");
	     	 int tempsid=Integer.parseInt(input1.nextLine());
	     	 System.out.println("choose the movie title:");
	     	 String temptitle=input1.nextLine();
	     	 String insert1="insert into GuestBook values ('"+emailId+"','"+tempid+"','"+tempsid+"','"+temptitle+"')";
	     	            
	     	 s.executeUpdate(insert1);
	     	 System.out.println("you have booked tickets successfully!"+"\n");	    
	     	 
	     	
	      	 }       
	      	 else{
	      		 System.out.println("sorry, your emailId cannot be used, please try another emailId"+"\n");
	      	 }       
	        	 
	        		 
           
	         s.close();  
	         c.close();
	         

	         }
			 catch(Exception e){e.printStackTrace();}
			
		}
	

}
