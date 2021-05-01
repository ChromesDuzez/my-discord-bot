import java.util.Scanner;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Bot extends ListenerAdapter
{
	private static String BOT_TOKEN = " ";
	public static JDA api;
	private static String _owner = " ";
	private static String _bitchboy = " ";
	
	public static void main(String[] args) throws Exception
	{
		Scanner in = new Scanner(System.in);
		
		if(BOT_TOKEN.length() < 6)
		{
			System.out.println("The current Bot Token is invalid.");
			System.out.print("Please enter in your Bot Token for discord: ");
			BOT_TOKEN = in.nextLine();
		}
		
		while(_owner.length() < 18)
		{
			System.out.println("The current owner ID is invalid.");
			System.out.println("i.e. 336231886198276096");
			System.out.print("Please enter in the ID for the user you want to have the rank owner in discord: ");
			_owner = in.nextLine();
			
			if(_owner.length() < 18)
			{
				System.out.println("The owner ID you put in is invalid.");
			}
		}
		
		api = JDABuilder.createDefault(BOT_TOKEN).build();
		api.addEventListener(new MyListener());
	}
	
	
	public static void setOwner(String owner)
	{
		_owner = owner;
	}
	
	public static void setBitchboy(String bitchboy)
	{
		_bitchboy = bitchboy;
	}
	
	public static String getOwner()
	{
		return _owner;
	}
	
	public static String getBitchboy()
	{
		return _bitchboy;
	}
}
