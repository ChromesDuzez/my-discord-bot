import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MyListener extends ListenerAdapter 
{
	private boolean transferOwner = false;
	private boolean transferBitch = false;
	
	@Override
	public void onReady(ReadyEvent event)
	{
		System.out.println("The bot is ready.");
		System.out.println(event.getJDA().getToken());
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event)
	{
		String author = event.getAuthor().getId();
		MessageChannel channel = event.getChannel();
		Message msg = event.getMessage();
        String msgAsString = msg.getContentRaw();
        String temp;
        
		if(author.equalsIgnoreCase(Bot.getOwner()))
		{
			if(transferOwner)
			{
				for(int i = 0; i < msg.getContentRaw().length(); i++)
				{
					if( !(Character.isDigit(msgAsString.charAt(i))))
					{
						channel.sendMessage("Invalid ID! Process cancelled.").queue();
						System.out.println("Bot owner transfer error code 1.");
						transferOwner = false;
						return;
					}
				}
				
				if(transferOwner)
				{
					channel.sendMessage("Valid ID! Transfering owner.").queue();
					Bot.setOwner(msgAsString);
					System.out.println("Bot owner transfer completed by server owner.");
					transferOwner = false;
				}
			}
			
			if(transferBitch)
			{
				for(int i = 0; i < msg.getContentRaw().length(); i++)
				{
					if( !(Character.isDigit(msgAsString.charAt(i))))
					{
						channel.sendMessage("Invalid ID! Process cancelled.").queue();
						System.out.println("Bot prankee transfer error code 1.");
						transferBitch = false;
						return;
					}
				}
				
				if(transferBitch)
				{
					channel.sendMessage("Valid ID! Transfering prankee.").queue();
					Bot.setBitchboy(msgAsString);
					System.out.println("Bot prankee transfer completed by server owner.");
					transferBitch = false;
				}
			}
			
			if(msg.getContentRaw().equalsIgnoreCase("//shutdown"))
			{
				System.out.println("Bot Shutdown initiated by server owner.");
				channel.sendMessage("The Bot will now go offline.").queue();
				channel.sendMessage("Contact Lord Plat if this is incorrect.").queue();
				Bot.saveProperties("Post Save State - Auto-Save");
				Bot.api.shutdown();
				System.out.println("Done. Exited with code 0.");
			}
			
			if(msg.getContentRaw().equalsIgnoreCase("//transfer Prankee"))
			{
				System.out.println("Bot prankee transfer initiated by server owner.");
				channel.sendMessage("The Bot will now go transfer the prankee.").queue();
				transferBitch = true;
				channel.sendMessage("Please send ther ID of who you want it to transfer to.").queue();
				channel.sendMessage("For Example: 438428649881075712").queue();
			}
			
			if(msg.getContentRaw().equalsIgnoreCase("//transfer Ownership"))
			{
				System.out.println("Bot owner transfer initiated by server owner.");
				channel.sendMessage("The Bot will now go transfer the owner.").queue();
				transferOwner = true;
				channel.sendMessage("Please send ther ID of who you want it to transfer to.").queue();
				channel.sendMessage("For Example: 336231886198276096").queue();
			}
		}
		
		if(msg.getContentRaw().equalsIgnoreCase("//get owner"))
		{
			System.out.println("owner = " + Bot.getOwner());
			channel.sendMessage("The owner is: " + Bot.getOwner()).queue();
		}
		
		if(msg.getContentRaw().equalsIgnoreCase("//get prankee"))
		{
			channel.sendMessage("The prankee is: " + Bot.getBitchboy()).queue();
		}
		
		if(msg.getContentRaw().equalsIgnoreCase("//bingbongchingchong"))
		{
			System.out.println("User wanted the funny joke video. Sent it to the channel.");
			channel.sendMessage("https://www.youtube.com/watch?v=Q8QlNuTUe4M").queue();
		}
		
		if(msg.getContentRaw().equalsIgnoreCase("//help"))
		{
			channel.sendMessage("// is the command starter and the following commands are known:" 
						+ "\n" + "get owner" + "\n" + "get prankee" + "\n" + "bingbongchingchong"
						+ "\n" + "ping" + "\n" + "transfer Ownership" + "\n" + "transfer Prankee" + "\n" + "shutdown").queue();
		}
		
		if (msg.getContentRaw().equalsIgnoreCase("//ping"))
        {
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                   .queue(response /* => Message */ -> {
                       response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                   });
            System.out.println("Pong: " + (System.currentTimeMillis() - time) + " ms");
        }
		else if(event.getAuthor().isBot())
			return;
		else if(author.equalsIgnoreCase(Bot.getBitchboy()))
		{
	         channel.sendMessage(":-1:").queue();
		}
		else
			return;
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event)
	{
		String sender = event.getMember().getId();
		if(sender.equals(Bot.getBitchboy()))
			event.getMember().deafen(true);
	}
}