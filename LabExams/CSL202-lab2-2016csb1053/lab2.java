import java.util.*;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


//Main Class 
public class lab2{										
	public static void main(String [] args){			
		
		
		 //Path of the Zip file from Command line arguments
		 String zipFilePath = args[1];
		 //Directory of unzip Files
		 String destDir = "Dictionary";
		 //Calling a functions 5to unzip the directory
		  unzip(zipFilePath, destDir);
		  

		// The name of the file to open. File to be passed from arguments
        String fileName = args[0];		

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);
				int count = 0;						//For counting how many words are wrong
            while((line = bufferedReader.readLine()) != null) {
                String[] words = line.split("\\W");				//Keeps the words in a line the the array 
                int len = words.length;
                for(int i=0;i<len;i++){
                	//System.out.println(words[i]);
                	int flag = 0;      				//Just to check whether the words present or not
                	//System.out.println("HI I am reading the file.");
                	File folder = new File("Dictionary/dictionaries");
					File[] listOfFiles = folder.listFiles();
					//System.out.println(listOfFiles.length);
					for (int i1 = 0; i1 < listOfFiles.length; i1++) {
						if(flag == 1)
						{
							break;
						}
					  File file = listOfFiles[i1];
					  if (file.isFile()) {
						//I am checking
						 // The name of the file to open.
        //String fileName1 = file;

        // This will reference one line at a time
        //String line1 = null;
			Scanner myScanner = null;				//Using Scanner Class to Read the words From file
				try
				{
					myScanner = new Scanner(file);
					while(myScanner.hasNextLine()){
    					if(flag==1){
    						break;
    					}
    					String contents=myScanner.nextLine();
    					//...
						if(words[i].equalsIgnoreCase(contents)){
						//System.out.println("The given word is " + words[i]);
						flag = 1;
						break;
					}
					}
					//String contents = myScanner.nextLine();
					
					
					 
				}
				finally
				{
					if(myScanner != null)
					{
						myScanner.close(); 
					}
				}
        
					  } 
					}
					if(flag==0){
						count++;
						System.out.println("The wrong word is " + words[i]);
						//System.out.println(i+1);
					}
                }
                //System.out.println(line);
            }   

            // Always close files.
            bufferedReader.close();   
              System.out.println("The total number of wrong words are ");
        System.out.println(count);
      
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
      	}
	
	private static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                //System.out.println("Unzipping to "+newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
