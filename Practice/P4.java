import java.io.File;
import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.io.*;
import java.util.*;
public class P4 {
	public static void main(String []args){
		executeScript();
	}
	    public static void executeScript() {
		  try {
		    ProcessBuilder pb = new ProcessBuilder("Script1.sh");
		    Process p = pb.start();     // Start the process.
		    p.waitFor();                // Wait for the process to finish.
		    System.out.println("Script1 executed successfully");
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		}
}