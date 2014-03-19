package cs425project;
import java.util.Scanner;

public class OnlineTicket {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Welcome to Online Ticketing and Theatre Management System"+"\n");
				
		boolean b=true;
		while(b){
		System.out.println("\n\n"+"please choose your identity:");	
		System.out.println("1 ----- guest"+"\n"+"2 ----- member"+"\n"+"3 ----- owner"+"\n"+"4 ----- administrator"+"\n"+"5 ----- manager"+"\n"+"5 ----- logout");
		System.out.println("now enter your identity or enter -1 to quit:");	
		Scanner input=new Scanner(System.in);
		int choice=input.nextInt();
		
		switch(choice)
		{
		case 1:System.out.println("Do you want to register? Y/N");
		       Scanner r=new Scanner(System.in);
		       String s=r.nextLine();
		       if(s.equalsIgnoreCase("Y"))
		    	   new GuestRegister().register();
		       else if(s.equalsIgnoreCase("N"))
		    	   new Guest().guest();
		break;
		case 2: new Login().member();
		break;
		case 3: new Owner().own();
		break;
		case 4: new Administrator().administrate();
		break;
		case 5: new Logout().logout();
		case -1: b=false;
		break;
		default:System.out.println("Error, please input your identity again");
		}
		}
		
	}

}
