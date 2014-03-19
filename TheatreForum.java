package cs425project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.*;
public class TheatreForum {
public int userid;
public int theatrethreadpoints,theatrereviewpoints;
	public TheatreForum() {
		// TODO Auto-generated constructor stub
	}
	public void theatreforum(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
       Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
       Statement s=c.createStatement();
       
       /**
        * display 10 most recent threads
        */
       System.out.println("\n"+"here are 10 most recent threads of the theatres:"+"\n");
       ResultSet r=s.executeQuery("select * from TheatreStartThread");
       ArrayList<String> s1=new ArrayList<String>();
       ArrayList<String> s2=new ArrayList<String>();
       ArrayList<String> s3=new ArrayList<String>();
       ArrayList<String> s4=new ArrayList<String>();
       
       while(r.next())
       {
       	s1.add(r.getString("userid"));
        s2.add(r.getString("tid"));
       	s3.add(r.getString("thread"));
       	s4.add(r.getString("time"));
       }
   
       for(int i=s1.size()-1;i>s1.size()-11;i--){
    	   System.out.println("thread starter id:  "+s1.get(i));
        System.out.println("theatre id:  "+s2.get(i));
       	System.out.println("thread:  "+s3.get(i));
       	System.out.println("published: "+s4.get(i)+"\n");
       }
       
    /**
     * display the least popular discussion thread in terms of visits and comments
     */
     System.out.println("the least popular discussion thread in TheatreForum is:");  
   	ResultSet com=s.executeQuery("select min(r) as min from (select startuserid,starttid,count(review)as r from TheatreReview group by startuserid,starttid )as num");
 	int num=0;
 	while(com.next()){
 		num+=Integer.parseInt(com.getString("min"));
 	}
    ResultSet com2=s.executeQuery("select startuserid,starttid from (select startuserid,starttid,count(review)as r from TheatreReview group by startuserid,starttid )as num where r='"+num+"'"); 
    int uid=0;
    int tid=0;
    while(com2.next()){
    	uid=Integer.parseInt(com2.getString("startuserid"));
    	tid=Integer.parseInt(com2.getString("starttid"));
    }
    ResultSet com3=s.executeQuery("select * from TheatreStartThread where userid='"+uid+"' and tid='"+tid+"'");
   
    while(com3.next()){
    	System.out.println("userid: "+com3.getString("userid"));
    	System.out.println("theatre id: "+com3.getString("tid"));
    	System.out.println("thread: "+com3.getString("thread"));
    }

       
       
       
       
     
       /**
       * start thread of movie experience && review the existing thread
       */
       System.out.println("\n"+"if you want to start new thread, press '1'");
       System.out.println("if you want to review the existing thread, press '2'");
       System.out.println("if you want to go back to menu, press '3'");
       Scanner scan=new Scanner(System.in);
       int choice=scan.nextInt();
       switch(choice){
       case 1: startThread();
       break;
       case 2: review();
       break;
       case 3: break;
       }

     
       s.close();  
       c.close();
       

       }
		 catch(Exception e){e.printStackTrace();}
	}
	
	public void startThread() {
		try{
			 Class.forName("com.mysql.jdbc.Driver");
      Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
      Statement s=c.createStatement();
      /**
       * view all theatre information
       */
      System.out.println("here are all theatre information:"+"\n");
      ResultSet rtheatre=s.executeQuery("select Theatre.id,Screen.screenid,Screen.capacity,Theatre.location from Theatre,Screen where Theatre.id=Screen.tid");
      while(rtheatre.next())
      {
   	   System.out.println("theatre id:  "+rtheatre.getString("Theatre.id"));
   	   System.out.println("Screenroom number:  "+rtheatre.getString("Screen.screenid"));
   	   System.out.println("capacity of Screenroom:  "+rtheatre.getString("Screen.capacity"));
   	   System.out.println("theatre location:  "+rtheatre.getString("Theatre.location")+"\n");
      }
      /**
       * display theatres that are showing most numbers of movies
       */
      
      
      /**
       * start new thread on movies
       */

         Scanner scan1=new Scanner(System.in);
    
         System.out.println("please input the theatre id that you want to start a new thread:");
         int tid=Integer.parseInt(scan1.nextLine());
         System.out.println("please inpput the new thread:");
         String thread=scan1.nextLine();
       
         Date now=new Date();
     	 DateFormat d1 = DateFormat.getDateTimeInstance();
     	 String time=d1.format(now);
     	 
      	  String insert="insert into TheatreStartThread values('"+time+"','"+userid+"','"+tid+"','"+thread+"');";
      	  
      	  s.executeUpdate(insert);
      
      	 System.out.println("Congratulations! you have started a new thread successfully!");
          
         
        	      
      s.close();  
      c.close();
      }
		 catch(Exception e){e.printStackTrace();}
		
	}
	
    public void review() {
    	try{
			 Class.forName("com.mysql.jdbc.Driver");
     Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
     Statement s=c.createStatement();
     /**
      * display 10 most recent review of a specific thread
      */

        Scanner scan=new Scanner(System.in);
        System.out.println("please input the thread starter's userid");
        int sid=Integer.parseInt(scan.nextLine());
        System.out.println("please input the theatre id");
        int stid=Integer.parseInt(scan.nextLine());
        ResultSet rr=s.executeQuery("select thread from TheatreStartThread where userid='"+sid+"' and tid='"+stid+"'");
        String thread="";
        while(rr.next()){
        	thread=rr.getString("thread");
        }

        ResultSet r=s.executeQuery("select * from TheatreReview where startuserid='"+sid+"'and starttid='"+stid+"'");
 
        ArrayList<String> s1=new ArrayList<String>();
        ArrayList<String> s2=new ArrayList<String>();
        ArrayList<String> s3=new ArrayList<String>();
        ArrayList<String> s4=new ArrayList<String>();
        ArrayList<String> s5=new ArrayList<String>();
        
        while(r.next())
        {
        	s1.add(r.getString("startuserid"));
            s2.add(r.getString("starttid"));
        	s3.add(r.getString("userid"));        	
        	s4.add(r.getString("review"));
        	s5.add("time");
        }
        if(s1.size()>10)
        {
            System.out.println("\n"+"here are 10 most recent reviews of the thread:"+thread+"\n");
            
        for(int i=s1.size()-1;i>s1.size()-11;i--){
        	
     	   System.out.println("thread starter id:  "+s1.get(i));
         System.out.println("theatre id:  "+s2.get(i));
        	System.out.println("reviewer userid:  "+s3.get(i));
        	System.out.println("review: "+s4.get(i));
        	System.out.println("published: "+s5.get(i)+"\n");
        }
        }
        else{
        	System.out.println("\n"+"here are most recent reviews of the thread:"+thread+"\n");
        	for(int i=s1.size()-1;i>=0;i--){
        		System.out.println("thread starter id:  "+s1.get(i));
                System.out.println("theatre id:  "+s2.get(i));
                System.out.println("reviewer userid:  "+s3.get(i));
                System.out.println("review: "+s4.get(i));
                System.out.println("published: "+s5.get(i)+"\n");

        	}
        }
        
        
        System.out.println("please input your review:");    
        String review=scan.nextLine();
      
        Date now=new Date();
    	 DateFormat d1 = DateFormat.getDateTimeInstance();
    	 String time=d1.format(now);
        
     	  String insert1="insert into TheatreReview values('"+time+"','"+userid+"','"+sid+"','"+stid+"','"+review+"')";
     	  s.executeUpdate(insert1);
     	  
     	System.out.println("Congratulations! you have reviewed this thread successfully"+"\n");
     	
     	/**
     	 * select registered guest who has contributed most comments
     	 */
     	ResultSet com=s.executeQuery("select max(r) as max from (select userid,count(review)as r from TheatreReview group by userid )as num");
     	int num=0;
     	while(com.next()){
     		num+=Integer.parseInt(com.getString("max"));
     	}
        ResultSet com2=s.executeQuery("select userid from (select userid,count(review)as r from TheatreReview group by userid )as num where r='"+num+"'"); 
        int uid=0;
        while(com2.next()){
        	uid=Integer.parseInt(com2.getString("userid"));
        }
        ResultSet com3=s.executeQuery("select userid,fname,lname from Register where userid='"+uid+"'");
        System.out.println("the registered member who has contributed most comments in TheatreForum is:");
        while(com3.next()){
        	System.out.println("userid: "+com3.getString("userid"));
        	System.out.println("first name: "+com3.getString("fname"));
        	System.out.println("last name: "+com3.getString("lname"));
        }

     
     s.close();  
     c.close();
     }
		 catch(Exception e){e.printStackTrace();}
		
		
	}
	

}
