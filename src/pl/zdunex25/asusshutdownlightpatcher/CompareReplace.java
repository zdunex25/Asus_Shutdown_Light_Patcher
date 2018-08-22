package pl.zdunex25.asusshutdownlightpatcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CompareReplace {

	private Matcher matcher;
	private Pattern methodPattern = Pattern.compile(METHOD_PATTERN, Pattern.DOTALL);
	private static String METHOD_PATTERN = "textholder";
	private static String DIR = "nameholder";
	private static final String TASKLIST = "tasklist";
	private static final String KILL = "taskkill /F /IM ";

	public static void main(String args[]) throws Exception {
		//if (args.length == 0) {
        //    System.out.println("\nNo arguments, type --help to get more info!");
		//} else {
			//if (args[0].contains("help") || args[0].contains("/?")) {
				System.out.println("\nAsus Shutdown Light Patcher v1.0 by ZduneX25, designed for AURA 1.06.29\nIf for whatever reason after shutdown lights are on just open Aura software\ngo to SHUTDOWN tab, do nothing just close software.\n");
			//} else {
				//String a = readFile("assets/shutdown_code.txt", StandardCharsets.US_ASCII);
				
				String FILE_DIR = "C:\\Program Files (x86)\\LightingService";
	    		String FILE_TEXT_EXT = "LastProfile.xml";
	    		String METHOD_NAME = "<state>Non-S0</state>";
	    		String METHOD_TO_USE = "            <mode key=\"101\">\r\n" + 
	    				"                <led key=\"0\">\r\n" + 
	    				"                    <color>0</color>\r\n" + 
	    				"                    <hue>0.000000</hue>\r\n" + 
	    				"                    <saturation>0.000000</saturation>\r\n" + 
	    				"                    <lightness>0.000000</lightness>\r\n" + 
	    				"                    <speed>0</speed>\r\n" + 
	    				"                    <direction>0</direction>\r\n" + 
	    				"                    <hashue2>0</hashue2>\r\n" + 
	    				"                    <hue2>0.000000</hue2>\r\n" + 
	    				"                </led>\r\n" + 
	    				"                <led key=\"1\">\r\n" + 
	    				"                    <color>0</color>\r\n" + 
	    				"                    <hue>0.000000</hue>\r\n" + 
	    				"                    <saturation>0.000000</saturation>\r\n" + 
	    				"                    <lightness>0.000000</lightness>\r\n" + 
	    				"                    <speed>0</speed>\r\n" + 
	    				"                    <direction>0</direction>\r\n" + 
	    				"                    <hashue2>0</hashue2>\r\n" + 
	    				"                    <hue2>0.000000</hue2>\r\n" + 
	    				"                </led>\r\n" + 
	    				"                <led key=\"2\">\r\n" + 
	    				"                    <color>0</color>\r\n" + 
	    				"                    <hue>0.000000</hue>\r\n" + 
	    				"                    <saturation>0.000000</saturation>\r\n" + 
	    				"                    <lightness>0.000000</lightness>\r\n" + 
	    				"                    <speed>0</speed>\r\n" + 
	    				"                    <direction>0</direction>\r\n" + 
	    				"                    <hashue2>0</hashue2>\r\n" + 
	    				"                    <hue2>0.000000</hue2>\r\n" + 
	    				"                </led>\r\n" + 
	    				"                <led key=\"3\">\r\n" + 
	    				"                    <color>0</color>\r\n" + 
	    				"                    <hue>0.000000</hue>\r\n" + 
	    				"                    <saturation>0.000000</saturation>\r\n" + 
	    				"                    <lightness>0.000000</lightness>\r\n" + 
	    				"                    <speed>0</speed>\r\n" + 
	    				"                    <direction>0</direction>\r\n" + 
	    				"                    <hashue2>0</hashue2>\r\n" + 
	    				"                    <hue2>0.000000</hue2>\r\n" + 
	    				"                </led>\r\n" + 
	    				"                <led key=\"4\">\r\n" + 
	    				"                    <color>0</color>\r\n" + 
	    				"                    <hue>0.000000</hue>\r\n" + 
	    				"                    <saturation>0.000000</saturation>\r\n" + 
	    				"                    <lightness>0.000000</lightness>\r\n" + 
	    				"                    <speed>0</speed>\r\n" + 
	    				"                    <direction>0</direction>\r\n" + 
	    				"                    <hashue2>0</hashue2>\r\n" + 
	    				"                    <hue2>0.000000</hue2>\r\n" + 
	    				"                </led>\r\n" + 
	    				"            </mode>";
	    		DIR = new File(FILE_DIR).getName();
	    		//if (OsUtils.isWindows()) {
		        //	System.out.println("\nYou are using Windows in order to continue you need to use Unix OS such as Ubuntu!");
		        //} else {
	    			System.out.println("AutoPatcher: [WORKING...]");
		        	new CompareReplace().listf(FILE_DIR, FILE_TEXT_EXT, METHOD_NAME, METHOD_TO_USE);
		        //}
			//}
    	//}
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	@SuppressWarnings("resource")
	public List<File> listf(String directoryName, String fileName, String methodName, String newMethod) throws Exception {
        File directory = new File(directoryName);
        List<File> resultList = new ArrayList<File>();

        File[] fList = directory.listFiles();
        if (fList == null) {
        	System.out.println("AutoPatcher: [PATH TO APP FOLDER IS MISSING, WRONG AURA VERSION]");
            return null;
        }
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
            	String filename = file.getName();
                if(filename.equalsIgnoreCase(fileName) && findinFile(file.getAbsolutePath(), methodName)) {
                	//fileee = file.getAbsolutePath();
                    METHOD_PATTERN = "(?s)" + Pattern.quote(methodName) + "\\s(.*?)</scene>";
                    
                    String content = null;
					try {
						content = new Scanner(new File(file.getAbsolutePath())).useDelimiter("\\Z").next();
						//String content2 = new Scanner(new File(newMethod)).useDelimiter("\\Z").next();
						String content2 = newMethod;
						content = content.replaceAll(METHOD_PATTERN, Matcher.quoteReplacement(methodName+"\n"+content2+"\n        </scene>"));
						//System.out.println(content);
						PrintWriter printWriter = new PrintWriter (file.getAbsolutePath());
	        		    printWriter.println (content);
	        		    printWriter.close ();
	        		    System.out.println("AutoPatcher: [LIGHTS FIXED]");
	        		    Runtime.getRuntime().exec(new String[] { "C:\\Program Files (x86)\\ASUS\\AURA\\Aura.exe"});
	        		    try {
	        		        Thread.sleep(5000);
	        		        String processName = "Aura.exe";
		        		    if (isProcessRunning(processName)) {
		        		    	killProcess(processName);
		        		    	System.out.println("AutoPatcher: [AURA SOFTWARE RESTARTED]");
		        		    	System.out.println("AutoPatcher: [DONE!!!]\n");
		        		    	
		        		    	askUser();
		        		    }
	        		    } catch(InterruptedException ex) {
	        		        Thread.currentThread().interrupt();
	        		    }

					} catch (FileNotFoundException e1) {
						System.out.println("AutoPatcher: [FAILED]\n");
						e1.printStackTrace();
					}
                } else {
                	//do nothing
                }
            } else if (file.isDirectory()) {
                resultList.addAll(listf(file.getAbsolutePath(), fileName, methodName, newMethod));
            }
        }
        return resultList;
    }
	
	@SuppressWarnings({ "resource", "unused" })
	public static void askUser() {
		Scanner input = new Scanner(System.in);
        System.out.println("Would you like to power off your PC now?");
        String a = input.next();

        if(a.equalsIgnoreCase("yes")) {
            System.out.println("PC will shutdown in 10 seconds!");
            Runtime runtime = Runtime.getRuntime();
            try {
				Process proc = runtime.exec("shutdown -s -t 10");
			} catch (IOException e) {
				e.printStackTrace();
			}
            System.exit(0);
        }

        else if(a.equalsIgnoreCase("no"))
            System.out.println("Ok, do it yourself later.");

        else
           System.out.println("You have to type \"yes\" or \"no\" with no capital letters!");
	}
	
	@SuppressWarnings("unused")
	private List<String> getAddress(String data){
		
		List<String> result = new ArrayList<String>();
		matcher = methodPattern.matcher(data);

		while (matcher.find()) {
			result.add(matcher.group(1));
			
			System.out.println(matcher.group(1));
		}
		
		return result;
	}
	
	@SuppressWarnings("resource")
	public static boolean findinFile(String file, String pattern) {
		File infile = new File(file);

    	try {
    	    Scanner scanner = new Scanner(infile);

    	    //now read the file line by line...
    	    //int lineNum = 0;
    	    while (scanner.hasNextLine()) {
    	        String line = scanner.nextLine();
    	        //lineNum++;
    	        if(line.contains(pattern)) { 
    	            //System.out.println("ho hum, i found it on line " +lineNum);
    	            return true;
    	        }
    	    }
    	} catch(FileNotFoundException e) { 
    	    //handle this
    	}
    	return false;
	}
	
	public static boolean isProcessRunning(String serviceName) throws Exception {
		 Process p = Runtime.getRuntime().exec(TASKLIST);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(
		 p.getInputStream()));
		 String line;
		 while ((line = reader.readLine()) != null) {
			 //System.out.println(line);
			 if (line.contains(serviceName)) {
				 return true;
			 }
		 }
		 return false;
		}

		public static void killProcess(String serviceName) throws Exception {
		  Runtime.getRuntime().exec(KILL + serviceName);
		 }
}
