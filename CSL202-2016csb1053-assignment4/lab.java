import java.io.File;
import java.io.FileInputStream;
import java.lang.*;
import java.util.regex.*;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import static java.util.concurrent.TimeUnit.*;
import java.util.concurrent.*;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.concurrent.*;
//Class for storing process details


public class lab {
	//For storing data in map.
	static Map<String,List<String>> map = new HashMap<String,List<String>>();
	static ArrayList<String> mail=new ArrayList<String>();		//For storing mail
	static int cnt = 1;
	//For storing violators
	static Map<String,List<String>> violator = new HashMap<String,List<String>>();
	static Map<String,Integer> dataViolator = new HashMap<String,Integer>();
	static Map<String,Integer> sentMail = new HashMap<String,Integer>();
	private static String USER_NAME = "abckbc321";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "abckbc@123"; // GMail password
    //private static String RECIPIENT = "";
	//static ArrayList<float> variable = new ArrayList<flaot>();


	private static final ScheduledExecutorService scheduler =
     	Executors.newScheduledThreadPool(1);
	public static void main (String []args) {

		File file = new File("configure.txt");
		int no_lines = 0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader("configure.txt"));
			while (reader.readLine() != null) no_lines++;
			reader.close();
		}
		catch(IOException e){
	
		}
		String[] lines = new String[no_lines];

    	try { 		
        	FileReader reader1 = new FileReader(file);
	        BufferedReader buffReader = new BufferedReader(reader1);
	        int x = 0;
	        String s;
	        while((s = buffReader.readLine()) != null){
	            lines[x] = s;
	            x++;
	        }
	    }
	    catch(IOException e){
	        //handle exception
	        System.out.println("In Catch block.");
	    }

	    String quota_window_minutes="a";
	    String sustained_max_cpu_usage_duration_limit="a";
	    String sustained_max_cpu_usage_limit="a";
	    String sustained_max_memory_usage_duration_limit="a";
	    String sustained_max_memory_usage_imit="a";
	    
	    int count = 0;
		for (String line : lines) {
			//Using string tokeniser to store all the data
		    StringTokenizer tokens = new StringTokenizer(line," ,");
		    while (tokens.hasMoreTokens()) {
		        String token = tokens.nextToken();
		        if(token.equals("=")){
		        	//System.out.println(token);
		        	if(count==0){
		        		quota_window_minutes = tokens.nextToken();
		        		count++;
		        	}
		        	else if(count==1){
		        		count++;
		        		sustained_max_cpu_usage_duration_limit = tokens.nextToken();
		        	}
		        	else if(count==2){
		        		count++;
		        		sustained_max_cpu_usage_limit = tokens.nextToken();
		        	}
		        	else if(count==3){
		        		count++;
		        		sustained_max_memory_usage_duration_limit = tokens.nextToken();
		        	}
		        	else if(count==4){
		        		count++;
		        		sustained_max_memory_usage_imit = tokens.nextToken();
		        	}
		        	else{
		        		while(tokens.hasMoreTokens())
		        		//System.out.println(tokens.nextToken());
		        		mail.add(tokens.nextToken());
		        	}	
		        }
		    }
		}
		
		//Parsing the various results into integer and float.
		int win_min = Integer.parseInt(quota_window_minutes); 
		int cpu_time = Integer.parseInt(sustained_max_cpu_usage_duration_limit);
		float cpu_per = Float.parseFloat(sustained_max_cpu_usage_limit);
		int mem_time = Integer.parseInt(sustained_max_memory_usage_duration_limit);
		float mem_per = Float.parseFloat(sustained_max_memory_usage_imit);
		
		//For mail sending;
		String from = USER_NAME;
        String pass = PASSWORD;
        int l = mail.size();
        String[] to = new String[l]; //{ RECIPIENT }; // list of recipient email addresses
        int i=0;
        for(String m : mail){
            to[i] = m;
            i++;
        }
        
// 		while(true){
// //			int cnt = 0;
// 			runTop(win_min,cpu_time,cpu_per,mem_time,mem_per,from,pass,to);
// 			cnt++;
// 			//System.out.println(map.size());        		
// 		}

		Runnable runnable = new Runnable() {
	      public void run() {
	        // task to run goes here
	        	runTop(win_min,cpu_time,cpu_per,mem_time,mem_per,from,pass,to);
				//cnt++;
				      }
	  };
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
		 
	}
	private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
                System.out.println("The value of toAddress is " + toAddress[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }
			System.out.println("The subject is " + subject);
            message.setSubject(subject);
            message.setText(body);
            System.out.println("Body of mail is " + body);
            Transport transport = session.getTransport("smtp");
             System.out.println("After transport" + " " + from + " " + pass );
            transport.connect(host, from, pass);
            System.out.println("After Connect");
            transport.sendMessage(message, message.getAllRecipients());
            System.out.println("After sendMessage");
            transport.close();
            System.out.println("After Close");
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
	public static void runTop(int win_min, int cpu_time, float cpu_per, int mem_time, float mem_per, String from, String pass, String []to){
		//System.out.println("RunTop is running " + cnt + " times.");
		//count++;
		final Runnable beeper = new Runnable() {
       public void run() {
       	try{
		Process jcmd = Runtime.getRuntime().exec("top -n 1 -b");
		InputStream cmdstr = jcmd.getInputStream();
		BufferedReader b1 = new BufferedReader(new InputStreamReader(cmdstr));
		String line;
		line = b1.readLine();
		int flag = 0;
		while(line!=null){
			int c = 0;
			StringTokenizer tokens = new StringTokenizer(line," ");
			String p_id = null;
			List<String> p = new ArrayList<String>();
			 
			while (tokens.hasMoreTokens()) {
		        String token = tokens.nextToken();
		        if(flag==1){
		        	if(c==0){
		        		// System.out.println("The pid is " + token);
		        		String pid = token;
		        		p_id = pid; 
		        		p.add(pid);
		        	}
		        	if(c==1){
		        		// System.out.println("The user is " + token);
		        		String user = token;
		        		p.add(user);
		        	}
		        	if(c==8){
		        		// System.out.println("The cpu is " + token);
		        		String cpu = token;
		        		p.add(cpu);
		        	}
		        	if(c==9){
		        		// System.out.println("The mem is " + token);
		        		String mem = token;
		        		p.add(mem);
		        	}
		        	if(c==10){
		        		// System.out.println("The time is " + token);
		        		String time = token;
		        		p.add(time);
		        	}
		        	if(c==11){
		        		// System.out.println("The command is " + token);
		        		String command = token;
		        		p.add(command);
		        	}	
		        }
		        if(token.equals("COMMAND")){
		        	flag = 1;
		        	//System.out.println(tokens.hasMoreTokens());
		        }
		        c++;
		    }
			line = b1.readLine();
			if(p.size()==0){
				//System.out.println("THe length of list is 0");
			}
			else{
				map.put(p_id,p);
			}
		}
		//System.out.println("The size of map is " + map.size());
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			//System.out.println("Hi");
			List<String> values = entry.getValue();
			String cpuper = values.get(2);
			String memper = values.get(3);
			// System.out.println("the cpu per is " + cpuper);
			float cpu = Float.parseFloat(cpuper);
			float mem = Float.parseFloat(memper);
			// System.out.println("mem is " + mem);
			if(cpu>cpu_per){
				String key = entry.getKey();
				//List<String> st = get(key);

				//Checking in violators where the key is present or not.
				if(violator.containsKey(key)){
					//Have to do
					if(dataViolator.containsKey(key)){
						int val = dataViolator.get(key);
						val++;
						if(val>cpu_time){
							// System.out.println("Process is violating in cpu.");
							if(sentMail.containsKey(key)){
								//Ignore
								System.out.println("Mail has been sent once.");
							}
							else{
								//have to send an email.
								System.out.println("Mail is about to send.");
								String subject = "Regarding the process which is consuming more memory or cpu";
        						String body = "The following program with pid " + values.get(0) + " and user is " + values.get(1) + " with cpu limit is " + values.get(2) + " with mem limit "+ values.get(3) + " with command name "+ values.get(5);
        						System.out.println("The body of the mail is " + body);
								sendFromGMail(from, pass, to, subject, body);
								sentMail.put(key,val);	
							}
						}
						else{
							dataViolator.put(key,val);
						}
					}
					else{
						int v1 = 1;
						dataViolator.put(key,v1);	//Adding in dataviolators
					}
				}
				else{
					violator.put(key,values);	//Adding to violators
					map.remove(key,values);	//Removing from map
				}
			}
			if(mem > mem_per){
				String key = entry.getKey();
				System.out.println("The process is violating in memory.");
				//List<String> st = get(key);

				//Checking in violators where the key is present or not.
				if(violator.containsKey(key)){
					//Have to do
					if(dataViolator.containsKey(key)){
						int val = dataViolator.get(key);
						System.out.println("The value is "+ val);
						val++;
						if(val>mem_time){
							if(sentMail.containsKey(key)){
								//Ignore
							}
							else{
								//have to send an email.
								System.out.println("Mail is about to send.");
								String subject = "Regarding the process which is consuming more memory or cpu";
        						String body = "The following program with pid " + values.get(0) + " and user is " + values.get(1) + " with cpu limit is " + values.get(2) + " with mem limit "+ values.get(3) + " with command name "+ values.get(5);
        						System.out.println("The body of the mail is " + body);
								sendFromGMail(from, pass, to, subject, body);
								sentMail.put(key,val);	
							}
						}
						else{
							dataViolator.put(key,val);
						}
					}
					else{
						int v1 = 1;
						dataViolator.put(key,v1);	//Adding in dataviolators
					}
				}
				else{
					violator.put(key,values);		//Adding to violators
					map.remove(key,values);			//Removing from map
				}
			}
		}

		}
		catch(IOException e)
		{
			

     	}
	}
};
    final ScheduledFuture<?> beeperHandle =
     scheduler.scheduleAtFixedRate(beeper, 1, 1, SECONDS);
     scheduler.schedule(new Runnable() {
       public void run() { 
       	map.clear();					//Clearing all the items after n minutes
       	violator.clear();
       	dataViolator.clear();
       	sentMail.clear();
       	beeperHandle.cancel(true); }
     }, win_min * 60, SECONDS);
	}
}
