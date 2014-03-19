package cs425project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class Owner {

	public Owner() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * view guests information, update movie and theatre schedule
	 */
	public void own(){
		System.out.println("Welcome! Owner");
		boolean b=true;
		while(b){
		System.out.println("1 ----- view guests information"+"\n"+"2 ----- view registered members information"+"\n"+"3 ----- update movie and theatre schedule"+
		"\n"+"4 ----- change discount policy"+"\n"+"5 ----- change member status policy"+"\n"+"6 ----- insert employee information"+
				"\n"+"7 ----- view employees who are on duty on monday on a specific theatre"+"\n"+
		"8 ----- check whether security is scheduled to work tomorrow"+"\n"+"-1 ----- quit");
		System.out.println("now enter your choice:");
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		switch(choice){
		case 1: showguestinfo();
		break;
		case 2: showmemberinfo();
		break;
		case 3: updateschedule();
		break;
		case 4:
		break;
		case 5:
		break;
		case 6: insertstaff();
	    break;
		case 7: dutymonday();
		break;
		case 8: nosecurity();
		break;
		case -1: break;
		default: System.out.println("Error! please enter your choice again");
		}
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
	
	public void updateschedule(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
      Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
      Statement s=c.createStatement();
     /**
      * show current movie and theatre information and schedule
      */
      ResultSet r=s.executeQuery("select theatre.id,screenid,capacity,location from Theatre,Screen where Theatre.id=Screen.tid");
      System.out.println("here is theatre information:");
      System.out.println("theatreid, location, screenroom number, capacity");
      while(r.next()){
    	  System.out.println(r.getString("theatre.id")+"     "+r.getString("screenid")+"   "+r.getString("capacity")+"  "+r.getString("location"));
      }
      System.out.println("here is movie information:");
      System.out.println("movie title, director, type");
      ResultSet r1=s.executeQuery("select * from Movie");
      while(r1.next()){
    	  System.out.println(r1.getString("title")+"  "+r1.getString("director")+"  "+r1.getString("type"));
      }
      
      
      s.close();  
      c.close();
      
      
      /**
       * update movie or theatre information
       */
      System.out.println("1 ----- insert new movie");
      System.out.println("2 ----- update movie show time");
      System.out.println("3 ----- delete outdated movie");
      System.out.println("now enter your choice");
      Scanner scan=new Scanner(System.in);
      int choice=scan.nextInt();
      switch(choice){
      case 1: insertM();
      break;
      case 2: updateM();
      break;
      case 3: deleteM();
      break;
      default: System.out.println("Error! please enter your choice again");
     
       }
   

      }
		 catch(Exception e){e.printStackTrace();}
	}
	
	public void insertM(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
     Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
     Statement s=c.createStatement();
     Scanner im=new Scanner(System.in);
		System.out.println("enter movie title");
		String title=im.nextLine();
		System.out.println("enter movie director");
		String director=im.nextLine();
		System.out.println("enter movie type");
		String type=im.nextLine();
		String insert="insert into Movie values('"+title+"','"+director+"','"+type+"')";
		s.executeUpdate(insert);
   
     
     s.close();  
     c.close();
     
     }
		 catch(Exception e){e.printStackTrace();}
		
	
	}
	public void updateM(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
    Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
    Statement s=c.createStatement();
    Scanner um=new Scanner(System.in);
    System.out.println("enter theatre id");
    int tid=um.nextInt();
    System.out.println("enter Screenroom number");
    int sid=um.nextInt();
		System.out.println("enter movie title");
		String time=um.nextLine();
		String update="update PlaySchedule set time='"+time+"' where tidd='"+tid+"' and sid='"+sid+"'";
		s.executeUpdate(update);
  
    
    s.close();  
    c.close();
    
    }
		 catch(Exception e){e.printStackTrace();}
	}
	public void deleteM(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
    Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
    Statement s=c.createStatement();
    Scanner dm=new Scanner(System.in);
    System.out.println("enter the movie title");
    String mtitle=dm.nextLine();
    
    ResultSet r=s.executeQuery("select mtitle from MovieStar");
    boolean find=false;
    while(r.next()){
    	if(mtitle.equalsIgnoreCase(r.getString("mtitle")))
    		find=true;
    }
    if(find==true){
    	s.executeUpdate("delete from MovieStar where mtitle='"+mtitle+"'");
    	s.executeUpdate("delete from Movie where mtitle='"+mtitle+"'");
    }
    else{
    	s.executeUpdate("delete from Movie where mtitle='"+mtitle+"'");

    }
  
    s.close();  
    c.close();
    
    }
		 catch(Exception e){e.printStackTrace();}
	}
	
	public void insertstaff(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
      Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
      Statement s=c.createStatement();
      Scanner input=new Scanner(System.in);
      System.out.println("enter employee id:");
      int employeeid=Integer.parseInt(input.nextLine());
      System.out.println("enter job type:");
      String jobtype=input.nextLine();
      System.out.println("enter first name:");
      String fname=input.nextLine();
      System.out.println("enter last name:");
      String lname=input.nextLine();
      System.out.println("enter address:");
      String address=input.nextLine();
      System.out.println("enter phone number:");
      String phone=input.nextLine();
      System.out.println("enter the working theatre id:");
      int tid=Integer.parseInt(input.nextLine());
      System.out.println("enter ssn:");
      int ssn=Integer.parseInt(input.nextLine());
      System.out.println("enter time slot id:");
      int timeslotid=Integer.parseInt(input.nextLine());
      
      String insert="insert into Staff values('"+employeeid+"','"+jobtype+"','"+fname+"','"+lname+"','"+address+"','"+phone+"','"+tid+"','"+ssn+"')";
      String insert2="insert into StaffSchedule values('"+employeeid+"','"+jobtype+"','"+tid+"','"+timeslotid+"')";
      
      s.executeUpdate(insert);
      s.executeUpdate(insert2);
      System.out.println("you have inserted employee information successfully!"+"\n");
   
    
      s.close();  
      c.close();
      

      }
		 catch(Exception e){e.printStackTrace();}
	}
	public void dutymonday(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
      Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
      Statement s=c.createStatement();
      
      
      /**
       * display list of employees who are on duty on Monday on a specific theatre
       * 
       */
      Scanner input=new Scanner(System.in);
      System.out.println("enter the specific theatre id");
      int ttid=Integer.parseInt(input.nextLine());
      ResultSet r=s.executeQuery("select distinct Staff.employeeid,Staff.fname,Staff.lname, Staff.jobtype, Time.starttime,Time.endtime from Staff,StaffSchedule natural join Time where Staff.tid='"+ttid+"' and Staff.employeeid in(select eid from StaffSchedule where timeslotid=1)");
     System.out.println("here is employee id,name,jobtype,time schedule who are on duty on Monday on a specific theatre");
      while(r.next()){
    	  System.out.println(r.getString("Staff.employeeid")+"  "+r.getString("Staff.fname")+" "+r.getString("Staff.lname")+"  "+r.getString("Staff.jobtype")+"  "+r.getString("Time.starttime")+"  "+r.getString("Time.endtime")+"\n");
      }
   
      /**
       * send an alert to the owner and manager if no employee with the job of security
       *  is scheduled to work tomorrow.
       */
      
    
      s.close();  
      c.close();
      

      }
		 catch(Exception e){e.printStackTrace();}
	}
	
	public void nosecurity(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
     Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
     Statement s=c.createStatement();
   
  
     /**
      * send an alert to the owner and manager if no employee with the job of security
      *  is scheduled to work tomorrow.
      */
     System.out.println("1 ----- Monday"+"\n"+"2 ----- Tuesday"+"\n"+"3 ----- Wendesday"+"\n"+"4 ----- Thursday"
      +"\n"+"5 ----- Friday"+"\n"+"6 ----- Saturday"+"\n"+"7 ----- Sunday");
     Scanner input=new Scanner(System.in);
     System.out.println("enter tmorrow's timeslot id");
     int timeid=input.nextInt();
     System.out.println("enter theatre id");
     int tid=input.nextInt();
     System.out.println("please enter timeslot id corresponding to tmorrow");
     ResultSet r=s.executeQuery("select jobtype from StaffSchedule where timeslotid='"+timeid+"' and tid='"+tid+"'" );
     ArrayList<String> array=new ArrayList<String>();
     while(r.next()){
    	 array.add(r.getString("jobtype"));
     }
     if(!array.contains("Security"))
     { System.out.println("Alert! No employee with the job of security is sheduled to work tomorrow");}
     else{
    	 System.out.println("don't worry, there is security is scheduled to work tomorrow");
     }
     
     
 
   
     s.close();  
     c.close();
     

     }
		 catch(Exception e){e.printStackTrace();}
	}

}
