package cs425project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class Administrator {

	public Administrator() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * view guests and members information
	 */
	public void administrate(){
		System.out.println("Welcome! Administrator");
		
		
		System.out.println("press 1 to view guest information");
		System.out.println("press 2 to view Register Member information");
		System.out.println("now enter your choice:");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		switch(choice){
		case 1: showguestinfo();
		break;
		case 2: showguestinfo();
		break;
		}
		
	}
	public void showguestinfo(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
       Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
       Statement s=c.createStatement();
       System.out.println("here is the guests personal information");
       ResultSet r=s.executeQuery("select * from Guest");
       System.out.println("emailId, first name, last name, card number, phone number, theatre location chosed to watch movie, the theatre id, the screenroom number, the movie title");
       
       while(r.next()){
       	System.out.println(r.getString("Guest.emailId")+"   "+r.getString("Guest.fname")+" "+r.getString("Guest.lname")+"   "+r.getString("Guest.cardnum")+"   "+r.getString("Guest.phonenum")+"   "+r.getString("Guest.location"));
       }
      		 
       System.out.println("\n"+"here is guests booking tickets information");
       System.out.println("guetst's emailId, theatre id, screenroom number, movie title");
       ResultSet r1=s.executeQuery("select * from GuestBook");
       while(r1.next()){
       	System.out.println(r1.getString("emailId")+"     "+r1.getString("tid")+"     "+r1.getString("sid")+"     "+r1.getString("mtitle"));
       }
     
       s.close();  
       c.close();
       

       }
		 catch(Exception e){e.printStackTrace();}
	}
	
	private void showmemberinfo() {
		try{
			 Class.forName("com.mysql.jdbc.Driver");
      Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
      Statement s=c.createStatement();
      System.out.println("here is registered members' personal information");
      ResultSet r=s.executeQuery("select userid,password,fname,lname,emailId,Register.cardnum,cardtype,expdate,phonenum,address from Register,Card where Register.cardnum=Card.cardnum");
      System.out.println("userid, password, first name, last name, emailId, credit card number, card type, expiration type, phone nubmer, address,");
      while(r.next()){
   	   System.out.println(r.getString("userid")+"   "+r.getString("fname")+"   "+r.getString("lname")+"   "+r.getString("emailId")+"   "+r.getString("Register.cardnum")+"   "+r.getString("cardtype")+"   "+r.getString("expdate")+"   "+r.getString("phonenum")+"   "+r.getString("address"));
      }
      System.out.println("\n"+"here is registered member's booking tickets information");
      ResultSet r1=s.executeQuery("select * from MemberBook");
      System.out.println("userid, theatre id, screenroom number, movie title, date, tquantity");
      while(r1.next()){
   	   System.out.println(r1.getString("userid")+"   "+r1.getString("tid")+"   "+r1.getString("sid")+"   "+r1.getString("mtitle")+"   "+r1.getString("mtitle")+"   "+r1.getString("date")+"   "+r1.getString("tquantity"));
      }
 
    
      s.close();  
      c.close();
      

      }
		 catch(Exception e){e.printStackTrace();}
	}

}
