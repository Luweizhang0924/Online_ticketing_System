package cs425project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
public class Login {
	public int userid,totalpoints;
	public String password,status;
	public int bpoints,mtpoints,mrpoints,ttpoints,trpoints;
	

	public Login() {
		
	}
	public void member(){
		/**
		 * login based on userid and password
		 */
		Scanner input=new Scanner(System.in);
	    System.out.println("please enter your userid:");
		userid=Integer.parseInt(input.nextLine());
		System.out.println("please enter your password");
		password=input.nextLine();
		 try{
			 Class.forName("com.mysql.jdbc.Driver");
         Connection c=DriverManager.getConnection("jdbc:mysql://localhost/cs425project?user=root&password=sql123");
         Statement s=c.createStatement();
         /**
          * verify userid and password
          */
        ResultSet r=s.executeQuery("select userid,password from Register");
        boolean find=false;

        while(r.next()){
        	if(userid==Integer.parseInt(r.getString("userid"))&&password.equals(r.getString("password")))
        		find=true;
        }
        /**
         * userid and password is correct
         */
        if(find==true){
        	System.out.println("you have logged successfully"+"\n");
        	System.out.println("Welcome to your homepage"+"\n");
        	/**
        	 * view personal information
        	 */
        	System.out.println("your personal information");
        	
        	ResultSet r2=s.executeQuery("select Register.userid, Register.password, Register.emailId, Register.cardnum, Card.cardtype, Card.expdate, Register.phonenum,Register.address from Register, Card where Register.cardnum=Card.cardnum and Register.userid='"+userid+"'");

        	System.out.println("userid, password, emailId, credit card number, creadit card type, credit card expiration date, phone number, address");
        	while(r2.next())
        	{System.out.println(r2.getString("userid")+"  "+r2.getString("password")+"  "+r2.getString("emailId")+"  "+r2.getString("cardnum")+"  "+r2.getString("cardtype")+"  "+r2.getString("expdate")+"  "+r2.getString("phonenum")+"  "+r2.getString("address")+"\n");}
        	 
        	
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
            		 System.out.println("creadit points:  "+totalpoints+"  status:  "+array2.get(i));
            		 status=array2.get(i);
            		 break;
            	 }
            	 i++;
             }
        
           Logout l=new Logout();
           l.userid=userid;
           l.status1=status;
             
           /**
            * display the theatres that are showing most number of movies
            */
           System.out.println("\n"+"the theatre that are showing most number of movies:");
           ResultSet m=s.executeQuery("select max(m) as max from(select tid,count(mtitle)as m from PlaySchedule group by tid)as num");
           int num=0;
           while(m.next()){
          	 num+=Integer.parseInt(m.getString("max"));
           }
           ResultSet m1=s.executeQuery("select tid from(select tid,count(mtitle)as m from PlaySchedule group by tid)as num where m='"+num+"'");
           int tid=0;
           while(m1.next()){
          	 tid=Integer.parseInt(m1.getString("tid"));
           }
           ResultSet m2=s.executeQuery("select * from Theatre where id='"+tid+"'");
           while(m2.next()){
          	 System.out.println("theatre id: "+m2.getString("id"));
          	 System.out.println("theatre location:"+m2.getString("location"));
           }
           
           
       
           
        	/**
        	 * the next step
        	 */
             boolean b=true;
           while(b){
        	 System.out.println("\n"+"if you want to book tickets, press '1'");
             System.out.println("if you want to enter to the Movie Forum, press '2' ");
             System.out.println("if you want to enter to the Theatre Forum, press '3' ");            
             System.out.println("if you want to change your password, press '4' ");
             System.out.println("if you want to change your phone number, press '5' ");
             System.out.println("if you want to logout, press '6' ");
              
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
             
             case 4: System.out.println("now enter your new password");
            	     Scanner scan4=new Scanner(System.in);
            	     String newpassword=scan4.nextLine();
            	     s.executeUpdate("update Register set password='"+newpassword+"' where userid='"+userid+"'");
            	     System.out.println("you have changed you password successfully!"+"\n");
            	     break;
             case 5: System.out.println("now enter your new phone number");
            	     Scanner scan5=new Scanner(System.in);
                     String newphone=scan5.nextLine();
                     s.executeUpdate("update Register set phonenum='"+newphone+"' where userid='"+userid+"'");
            	     break;
             case 6: l.logout();
                     b=false;
            	 break;
          
             
             default: System.out.println("Error! please input your choice again"); 
             }
	
        }
           }
        /**
         * userid and password cannot match
         */
        else{System.out.println("your userid and password cannot match, please try again"+"\n");}
        
         s.close();  
         c.close();

         }
		 catch(Exception e){e.printStackTrace();}
		
	}
	

}
