package cs425project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class MovieForum {

	public int userid;
	public int moviethreadpoints,moviereviewpoints;

	
	public MovieForum() {
		// TODO Auto-generated constructor stub
		moviethreadpoints=0;
		moviereviewpoints=0;
	}
	public void movieforum(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
        Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
        Statement s=c.createStatement();
        
        /**
         * display 10 most recent threads
         */
        System.out.println("\n"+"here are 10 most recent threads of the movies:"+"\n");
        ResultSet r=s.executeQuery("select * from MovieStartThread");
        ArrayList<String> s1=new ArrayList<String>();
        ArrayList<String> s2=new ArrayList<String>();
        ArrayList<String> s3=new ArrayList<String>();
        ArrayList<String> s4=new ArrayList<String>();
        
        while(r.next())
        {
        	s1.add(r.getString("userid"));
         s2.add(r.getString("mtitle"));
        	s3.add(r.getString("thread"));
        	s4.add(r.getString("time"));
        }
    
        for(int i=s1.size()-1;i>s1.size()-11;i--){
     	   System.out.println("movie starter id:  "+s1.get(i));
         System.out.println("movie title:  "+s2.get(i));
        	System.out.println("thread:  "+s3.get(i));
        	System.out.println("published: "+s4.get(i)+"\n");
        }
        
        /**
         * display the least popular discussion thread in terms of visits and comments
         */
         System.out.println("the least popular discussion thread in MovieForum is:");  
       	ResultSet com=s.executeQuery("select min(r) as min from (select startuserid,startmtitle,count(review)as r from MovieReview group by startuserid,startmtitle )as num");
     	int num=0;
     	while(com.next()){
     		num+=Integer.parseInt(com.getString("min"));
     	}
        ResultSet com2=s.executeQuery("select startuserid,startmtitle from (select startuserid,startmtitle,count(review)as r from MovieReview group by startuserid,startmtitle )as num where r='"+num+"'"); 
        int uid=0;
        String title="";
        while(com2.next()){
        	uid=Integer.parseInt(com2.getString("startuserid"));
        	title=(com2.getString("startmtitle"));
        }
        ResultSet com3=s.executeQuery("select * from MovieStartThread where userid='"+uid+"' and mtitle='"+title+"'");
       
        while(com3.next()){
        	System.out.println("userid: "+com3.getString("userid"));
        	System.out.println("movie title: "+com3.getString("mtitle"));
        	System.out.println("thread: "+com3.getString("thread"));
        }

        
        
        
        
        
        
        /**
        * start thread of movie experience && review the existing thread
        */
        System.out.println("if you want to start new thread, press '1'");
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
	
	/**
	 * start thread
	 */
	public void startThread(){
		try{
			 Class.forName("com.mysql.jdbc.Driver");
       Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
       Statement s=c.createStatement();
       /**
        * view all movie information
        */
       
       System.out.println("here are all movie information:"+"\n");
       ResultSet rmovie=s.executeQuery("select * from Movie");
       while(rmovie.next())
       {
    	   System.out.println("movie title:  "+rmovie.getString("title"));
    	   System.out.println("direcotr:  "+rmovie.getString("director"));
    	   System.out.println("type:  "+rmovie.getString("type")+"\n");
       }
      
       
       /**
        * start new thread on movies
        */

          Scanner scan1=new Scanner(System.in);
     
          System.out.println("please input the movie name that you want to start a new thread:");
          String mtitle=scan1.nextLine();
          System.out.println("please inpput the new thread:");
          String thread=scan1.nextLine();
          
          Date now=new Date();
      	 DateFormat d1 = DateFormat.getDateTimeInstance();
      	 String time=d1.format(now);
        
      	 
       	  String insert="insert into MovieStartThread values('"+time+"','"+userid+"','"+mtitle+"','"+thread+"');";
       	  
       	  s.executeUpdate(insert);
       
       	 System.out.println("Congratulations! you have started a new thread successfully!");

          
        
          
          
       
       s.close();  
       c.close();
       }
		 catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * review existing thread
	 */
	
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
       System.out.println("please input the movie title");
       String mtitle=scan.nextLine();
       ResultSet rr=s.executeQuery("select thread from MovieStartThread where userid='"+sid+"' and mtitle='"+mtitle+"'");
       String thread="";
       while(rr.next()){
       	thread=rr.getString("thread");
       }

       ResultSet r=s.executeQuery("select * from MovieReview where startuserid='"+sid+"'and startmtitle='"+mtitle+"'");

       ArrayList<String> s1=new ArrayList<String>();
       ArrayList<String> s2=new ArrayList<String>();
       ArrayList<String> s3=new ArrayList<String>();
       ArrayList<String> s4=new ArrayList<String>();
       ArrayList<String> s5=new ArrayList<String>();
       
       while(r.next())
       {
       	s1.add(r.getString("startuserid"));
           s2.add(r.getString("startmtitle"));
       	s3.add(r.getString("userid"));        	
       	s4.add(r.getString("review"));
       	s5.add("time");
       }
       if(s1.size()>10)
       {
           System.out.println("\n"+"here are 10 most recent reviews of the thread:"+thread+"\n");
           
       for(int i=s1.size()-1;i>s1.size()-11;i--){
       	
    	   System.out.println("thread starter id:  "+s1.get(i));
        System.out.println("movie title:  "+s2.get(i));
       	System.out.println("reviewer userid:  "+s3.get(i));
       	System.out.println("review: "+s4.get(i));
       	System.out.println("published: "+s5.get(i)+"\n");
       }
       }
       else{
       	System.out.println("\n"+"here are most recent reviews of the thread:"+thread+"\n");
       	for(int i=s1.size()-1;i>=0;i--){
       		System.out.println("thread starter id:  "+s1.get(i));
               System.out.println("movie title:  "+s2.get(i));
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
       
    	  String insert1="insert into MovieReview values('"+time+"','"+userid+"','"+sid+"','"+mtitle+"','"+review+"')";
    	  s.executeUpdate(insert1);
    	  
    	System.out.println("Congratulations! you have reviewed this thread successfully"+"\n");
    	
    	
    	/**
    	 * select registered guest who has contributed most comments
    	 */
    	ResultSet com=s.executeQuery("select max(r) as max from (select userid,count(review)as r from MovieReview group by userid )as num");
    	int num=0;
    	while(com.next()){
    		num+=Integer.parseInt(com.getString("max"));
    	}
       ResultSet com2=s.executeQuery("select userid from (select userid,count(review)as r from MovieReview group by userid )as num where r='"+num+"'"); 
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
