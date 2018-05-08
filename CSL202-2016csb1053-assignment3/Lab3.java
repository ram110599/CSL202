import java.io.File;
import java.io.FileInputStream;
import java.lang.*;
import java.util.regex.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
import java.util.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedInputStream;

public class Lab3 {
	public static void main(String[] args) throws ArrayIndexOutOfBoundsException {
		
		try {
			/* Taking url from command line. */
			String url = args[0];
			/* Calling the function to download file. */	 
            downloadUsingNIO(url, "DownloadedJAR");
            
        } catch (ArrayIndexOutOfBoundsException a){		//For Handlining array index out of bound
			System.out.println("Please give the URL through the command line.");
		}

         catch (IOException e) { 						//For Handling input output exception
            e.printStackTrace();
        }
        /* Unzipping JAR files in a folder. */
            String zipFilePath = "DownloadedJAR";
			/* Destination Of unzipped folder in the current */        
        	String destDir = "JAR";
        	
        	try {
        		
        		/* Calling Function to extract the JAR File*/
        		unjar(zipFilePath, destDir);
        		//For storing the JVM instructions 
        		HashMap< String,Integer> jvm = new HashMap< String,Integer>();

        		PrintStream pri = new PrintStream(new File("output.txt"));	//For printing in output.txt file
        		System.setOut(pri);			
        		Process p1 = Runtime.getRuntime().exec("mkdir ./class");
        		Process p2 = Runtime.getRuntime().exec("find ./JAR -type f -name *.class -exec install -D {} ./class ;");	
        		//Process p3 = Runtime.getRuntime().exec("javap -verbose ./class");
        		Process p3 = Runtime.getRuntime().exec("find ./class/ -type f -name *.class");
        		//Storing the name of .class file by input stream
        		InputStream ab = p3.getInputStream();
        		//Variable 1 to store the result
        		int i1 = 0;
        		StringBuffer s = new StringBuffer();
        		while((i1=ab.read())!= -1){
        			s.append((char)i1);			//Appending all the file name
        		}
        		//System.out.println(s.toString());
				ab.close();
				//Storing each name separately in string array
				String name[] = s.toString().split("\n");
				//Command to excutemy buffereader doesnot work well in java why
				String command = "javap -verbose ";

				int c=0;  //VAriable to store counting result
				int c1 = 0;
				int pool_size[] = new int[name.length];
				//pattern to be matched for counting pool size
				Pattern pool_pattern = Pattern.compile("#[0-9][0-9]*[ \t]=");

				//PATTERN FOR JVM
                Pattern jvmins = Pattern.compile("[0-9][0-9]*: [a-zA-Z].*");

				int flag1 = 0;
				int jvm_inst = 0;		//Variable for counting JVM instruction.
				while(c<name.length){
					String com = command + name[c];
					Process jcmd = Runtime.getRuntime().exec(com);
					InputStream cmdstr = jcmd.getInputStream();
					BufferedReader b1 = new BufferedReader(new InputStreamReader(cmdstr));
					String line;
					line = b1.readLine();
				
					while(line!=null){
						//Finding JVM Instruction 
						Matcher m31=jvmins.matcher(line);
                        while(m31.find())			//Finding the required pattern
                        {
                        String [] abc32 = line.split(":",2);  String [] abc321 = abc32[1].split(" ");
                        
                        if(jvm.containsKey(abc321[1]))
                          {
                            jvm.put(abc321[1], jvm.get(abc321[1]) + 1);
                          }
                        else
                            {jvm.put(abc321[1],1);
                            	jvm_inst++;	//Increamenting the variable.
                            }
                        //System.out.println(abc321[1]);
                        }

						Matcher m = pool_pattern.matcher(line);
					//	System.out.println(line);
						while(m.find()){
							flag1++;
						}
						line = b1.readLine();
					}
					pool_size[c1] = flag1;
					//System.out.println(flag1);
					flag1 = 0; //Intiliasing the flag1 = 0;
					System.out.println("The pool_size in the " + name[c] + " file is:");
					System.out.println(pool_size[c1]);
					c++;
					c1++;
				}
				//For Calculating average, min, max , standard Deviation;
				int min = pool_size[0];
				int max = pool_size[0];
				double average = 0;
				int sum = 0;
				int n = pool_size.length;
				for(int j1=0;j1<n;j1++){
					if(pool_size[j1]<min){
						min = pool_size[j1];			//For calculating minimum
					}
					if(pool_size[j1]>max){
						max = pool_size[j1];			//For calculating maximum
					}
					sum += pool_size[j1];				//For summing
					//System.out.println(pool_size[j1]);
				}
				double sum1 = sum;
				average = sum1/n;
				double dev = 0;		//For calculating standard deviation
				for(int j1=0;j1<n;j1++){
					double d1 = pool_size[j1];
					double s1 = d1-average;
					dev+=s1*s1;
				}
				dev = dev/n;
				double stdev = Math.sqrt(dev);	//For storing standard deviation
				System.out.println();
				//System.out.println();
				System.out.println("The maximum value of pool_size in all the .class file is " + max);
				System.out.println("The minimum value is pool_size in all the .class file is " + min);
				System.out.println("The average value pool_size is in all the .class file is " + average);
				System.out.println("The standard deviation pool_size in all the .class file is " + stdev);
				System.out.println();

				//FINDING TOP 50 JVM
            	for(int i_1=0;i_1<50;i_1++)
	            {Integer mx=new Integer(0);
	            String k=null;
	                for(Map.Entry m:jvm.entrySet()){
	                    if(mx.compareTo((Integer) m.getValue())<0)
	                    {mx=(Integer) m.getValue();
	                    k=(String) m.getKey();}
	                   
	                
	               }
                System.out.println(k+" "+mx);
                jvm.put( k,0);
            }

				/* For calculating the number of methods in a class file*/
				String command1 = "javap -p ";

				int cm=0;  //VAriable to store counting result
				int cm1 = 0;
				int met_size[] = new int[name.length];
				//pattern to be matched for counting method size
				Pattern met_pattern = Pattern.compile("\\)");
				int flag2 = 0;
				while(cm<name.length){
					String com1 = command1 + name[cm];
					Process jcmd1 = Runtime.getRuntime().exec(com1);
					InputStream cmdstr1 = jcmd1.getInputStream();
					BufferedReader b2 = new BufferedReader(new InputStreamReader(cmdstr1));
					String line1;
					line1 = b2.readLine();

					while(line1!=null){
						Matcher m1 = met_pattern.matcher(line1);
					//	System.out.println(line);
						while(m1.find()){
							flag2++;
						}
						line1 = b2.readLine();
					}
					met_size[cm1] = flag2;
					//System.out.println(flag2);
					flag2 = 0; //Intiliasing the flag1 = 0;
					System.out.println();
					System.out.println("The number of methods in  " + name[cm] + " file are:");
					System.out.println(met_size[cm1]);
					cm++;
					cm1++;
				}
				double avg = 0;
				int n2 = met_size.length;
				for(int i31=0;i31<n2;i31++){
					double avg1 = met_size[i31];
					avg += avg1;
				}
				avg = avg/n2;
				System.out.println();
				System.out.println("The average number of methods in all the .class file is " + avg);

        	} catch (IOException e){
        		e.printStackTrace();
        	}
        	
        	
	}
	/* Code for downloading files Using NIO API */
	private static void downloadUsingNIO(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(file);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }
    /* Code for extracting JAR files */
    private static void unjar(String jarFilePath, String destdir) throws IOException {
    	java.util.jar.JarFile jarfile = new java.util.jar.JarFile(new java.io.File(jarFilePath)); //jar file path(here sqljdbc4.jar)
    	java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
        while(enu.hasMoreElements())
        {
            //String destdir = "JAR";     //JAR is my destination directory
            java.util.jar.JarEntry je = enu.nextElement();

            //System.out.println(je.getName());

            java.io.File fl = new java.io.File(destdir, je.getName());
            if(!fl.exists())
            {
                fl.getParentFile().mkdirs();
                fl = new java.io.File(destdir, je.getName());
            }
            if(je.isDirectory())
            {
                continue;
            }
            java.io.InputStream is = jarfile.getInputStream(je);
            java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
            while(is.available()>0)
            {
                fo.write(is.read());
            }
            fo.close();
            is.close();
        }
    }
    
}