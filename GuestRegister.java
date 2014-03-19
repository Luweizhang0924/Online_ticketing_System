package cs425project;

import java.sql.*;
import java.util.*;
public class GuestRegister {
	public int userid,
	            emailId,
	            points;
	public String password,
	              fname,
	              lname,
	               cardnum,
	               cardtype,
	               expdate,
	               phonenum,
	               address;

	public GuestRegister() {
		// TODO Auto-generated constructor stub
		points=0;
	}
	public void register(){
		System.out.println("Welcome to become a member of our system");
		Scanner input=new Scanner(System.in);
		System.out.println("please provide your personal information:");
		System.out.println("UserId:");
		userid=Integer.parseInt(input.nextLine());
		System.out.println("Password:");
		password=input.nextLine();
		System.out.println("first name:");
		fname=input.nextLine();
		System.out.println("last name");
		lname=input.nextLine();
		System.out.println("EmailId:");
		emailId=Integer.parseInt(input.nextLine());
		System.out.println("credit card number:");
		cardnum=input.nextLine();
		System.out.println("credit card type:");
		cardtype=input.nextLine();
		System.out.println("expiration date:");
		expdate=input.nextLine();
		System.out.println("phone number:");
		phonenum=input.next();
		System.out.println("address:");
		address=input.next();
		
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
         Statement s=c.createStatement();
         /**
          * register
          */
         ResultSet r=s.executeQuery("select userid,emailId from Register");
         boolean find=false;
         while(r.next())
         {
        	 if(userid==Integer.parseInt(r.getString("userid")))
        	 {find=true;}
         }
         
         if(find==true)
         {
        	 System.out.println("your userid or emailId has been used, please try again"+"\n");
         }
         else{
         String insert="insert into Card values('"+cardnum+"','"+cardtype+"','"+expdate+"')";
         String insert1="insert into Register values ('"+userid+"','"+password+"','"+fname+"','"+lname+"','"+emailId+"','"+cardnum+"','"+phonenum+"','"+address+"','"+points+"')";     
         s.executeUpdate(insert);
         s.executeUpdate(insert1);
         
         
         System.out.println("you have register successfully!"+"\n");
         System.out.println("if you want to book tickets, press '1'");
         System.out.println("if you want to enter to the Movie Forum, press '2'");
         System.out.println("if you want to enter to the Theatre Forum, press '3'");
         System.out.println("if you want to logout, press '4' ");
         System.out.println("now input your choice: ");
         Scanner scan=new Scanner(System.in);
         int choice=scan.nextInt();
         switch(choice){
         case 1: MemberBook book=new MemberBook();
                   book.userid=userid;
                   book.memberbook();
                   break;
         case 2: MovieForum mforum=new MovieForum();
                mforum.userid=userid;
                mforum.movieforum();
                   break;
         case 3: TheatreForum tforum=new TheatreForum();
                 tforum.userid=userid;
                 tforum.theatreforum();
         break;
         case 4: new Logout().logout();
         break;
         default: System.out.println("Error! please input your choice again"); 
         }

     
     	
         s.close();  
         c.close();
         }
         }
		 catch(Exception e){e.printStackTrace();}
		
	}

}
