import java.lang.reflect.Member;
import java.util.Scanner;
import java.util.Properties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Bot extends ListenerAdapter
{
	private static Properties p;
	public static JDA api;
	private static String _bitchboy = " "; 
	
	public static void main(String[] args) throws Exception
	{
		FileReader reader;
		try {
			reader = new FileReader("config.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			String currentPath = new File(".").getCanonicalPath();
			FileOutputStream output = new FileOutputStream(currentPath + "/config.properties");
			reader = new FileReader("config.properties");
			//e.printStackTrace();
		}  
	      
	    p = new Properties();  
	    p.load(reader);  
	    
		Scanner in = new Scanner(System.in);
		
		System.out.println("BOT_TOKEN = " + p.getProperty("BOT_TOKEN"));  
	    System.out.println("owner = " + p.getProperty("owner"));
		String BOT_TOKEN = p.getProperty("BOT_TOKEN");
		String _owner = p.getProperty("owner");
		
		while(BOT_TOKEN == null || BOT_TOKEN.length() < 6)
		{
			System.out.println("The current Bot Token is invalid.");
			System.out.print("Please enter in your Bot Token for discord: ");
			BOT_TOKEN = in.nextLine();
			
			if(BOT_TOKEN.equalsIgnoreCase("stop"))
			{
				break;
			}
			else
			{
				p.setProperty("BOT_TOKEN", BOT_TOKEN);
			}
		}
		
		while(_owner == null || _owner.length() < 6)
		{
			System.out.println("The current owner is invalid.");
			System.out.print("Please enter in your owner ID for discord (this will be saved): ");
			_owner = in.nextLine();
			
			if(_owner.equalsIgnoreCase("stop"))
			{
				break;
			}
			else
			{
				p.setProperty("owner", _owner);
			}
		}
		
		p.store(new FileWriter("config.properties"), "Current Save State - Auto-Save");  
		
		if( (! (BOT_TOKEN.length() < 6) ) || (! (_owner.length() < 6)) ) 
		{
			api = JDABuilder.createDefault(BOT_TOKEN).build();
			api.addEventListener(new MyListener());
		}
	}
	
	
	public static void setOwner(String owner)
	{
		
		if( (owner.equalsIgnoreCase("stop")) || (owner.length() < 6) )
		{
			System.out.println("User tried to transfer arbitrary owner to an invalid target.");
		}
		else
		{
			p.setProperty("owner", owner);
		}
		System.out.println("owner = " + p.getProperty("owner"));
	}
	
	public static void setBitchboy(String prankee)
	{
		p.setProperty("prankee", prankee);
	}
	
	public static String getOwner()
	{
		return p.getProperty("owner");
	}
	
	public static String getBitchboy()
	{
		return p.getProperty("prankee");
	}
	
	public static void saveProperties(String message)
	{
		message = message != null ? message : "Random Save State - Auto-Save";
		try {
			p.store(new FileWriter("config.properties"), message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
