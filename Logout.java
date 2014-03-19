package cs425project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Logout {
	public int userid;
public String status1,status2;
public int bpoints,mtpoints,mrpoints,ttpoints,trpoints,totalpoints;

	public Logout() {
		// TODO Auto-generated constructor stub
	}
	public void logout(){
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
         Statement s=c.createStatement();
       
        
        	/**
        	 * view credit points and status
        	 */
        	
            ResultSet test=s.executeQuery("select userid from MemberBook");
         	boolean find2=false;
             while(test.next()){
            	 if(userid==Integer.parseInt(test.getString("userid")))
            		 find2=true;
            	 
             }
             if(find2==false){
            	 bpoints=0;
             }
             else{
              	ResultSet rb=s.executeQuery("select userid,sum(tquantity) as tickts from MemberBook where userid='"+userid+"'");
                while(rb.next())   
            	 {bpoints+=Integer.parseInt(rb.getString("tickts"));}
             }
             
             ResultSet rmt=s.executeQuery("select userid,count(thread) as moviethread from MovieStartThread group by userid having userid='"+userid+"'");
             while(rmt.next()){
            	 mtpoints+=Integer.parseInt(rmt.getString("moviethread"));
             }
             ResultSet rmr=s.executeQuery("select userid,count(review) as moviereview from MovieReview group by userid having userid='"+userid+"'");
             while(rmr.next()){
            	 mrpoints+=Integer.parseInt(rmr.getString("moviereview"));
             }
             ResultSet rtt=s.executeQuery("select userid,count(thread) as theatrethread from TheatreStartThread group by userid having userid='"+userid+"'");
             while(rtt.next()){
            	 ttpoints+=Integer.parseInt(rtt.getString("theatrethread"));
             }  	
             ResultSet rtr=s.executeQuery("select userid,count(review) as theatrereview from TheatreReview group by userid having userid='"+userid+"'");
             while(rtr.next()){
            	 trpoints+=Integer.parseInt(rtr.getString("theatrereview"));
             }
             totalpoints=bpoints+mtpoints+mrpoints+ttpoints+trpoints;
             
             //update points
             s.executeUpdate("update Register set points='"+totalpoints+"' where userid='"+userid+"'");
             
             //show points
             ResultSet p=s.executeQuery("select points from Register where userid='"+userid+"'");
             
             while(p.next()){
            	 totalpoints=Integer.parseInt(p.getString("points"));
             }
             
             //get status
             ArrayList<String> array1=new ArrayList<String>();
             ArrayList<String> array2=new ArrayList<String>();
             array2.add("PRIMARY");
             ResultSet st=s.executeQuery("select * from Status");
             while(st.next()){
            	 array1.add(st.getString("points"));
            	 array2.add(st.getString("status"));
             }
            
             int i=0;
             while(i<array1.size()){
            	 
            	 if(totalpoints<=Integer.parseInt(array1.get(i)))
            	 {
            		 //System.out.println("creadit points:  "+totalpoints+"  status:  "+array2.get(i));
            		 status2=array2.get(i);
            		 break;
            	 }
            	 i++;
             }
        //check if status changes
             if(status1.compareTo(status2)!=0)
            	 System.out.println("Congratulations! your status has changed, your current status is: "+status2);
             
             
             
          
         s.close();  
         c.close();

         }
		 catch(Exception e){e.printStackTrace();}
	}

}
