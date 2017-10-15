package Logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log{
	
	private static FileHandler loggingFile;
    private static SimpleFormatter formatterTxt;
    private Logger logger;
    private String module;
    
    public Log(){
    	this.module="all";
    }
    
    public Log(String module){
    	this.module=module;
    }
    
    public void initialize() throws IOException{
    	
    	try{
    		creationInstruction();
    	}
    	catch(IOException e){
    		System.out.println("ok");
    		throw new IOException("Impossible to create the Logging File");
    	}
    	
    }
    
	public void creationInstruction() throws IOException{
		
		// The following line enables to get information about the current date and time.
		Calendar now = Calendar.getInstance();
		final int day = now.get(Calendar.DATE);
		final int month = now.get(Calendar.MONTH)+1;
		final int hour = now.get(Calendar.HOUR_OF_DAY);
		
		// The following lines initialize the Logger and indicate the fact that all types of logging message will be taken into account.
		logger = Logger.getLogger(module);
		logger.setLevel(Level.ALL);
		
		if(module=="all"){
			// Creation of the file that will report all logging messages : general logging file
			loggingFile = new FileHandler("Logging/General/Logging_"+day+"_"+month+"_"+hour+".txt",true);
		}
		else{
			// Creation of the file corresponding to the current module
			loggingFile = new FileHandler("Logging/"+module+"/Logging_"+day+"_"+month+"_"+hour+".txt",true);
		}
		
		// Create a formatter (simple) to display messages : simpleFormatter can be replaced by other if other options required.
        formatterTxt = new SimpleFormatter();
        loggingFile.setFormatter(formatterTxt);
        logger.addHandler(loggingFile);
		
	}
	
	public void write(String message){
		logger.info(message);
	}
	
	public void write(String message,String category){
		
		switch(category){
		
			case "info": logger.info(message);
				break;
		
			case "warning": logger.warning(message);
				break;
			
			case "finest": logger.finest(message);
				break;
		
			case "severe": logger.severe(message);
				break;
			default : logger.info(message);
				break;
		}
			
		
	}
	
	public void write(String message,String category,String classe){
		
		switch(category){
		
			case "info": logger.info("<"+classe+"> "+message);
				break;
		
			case "warning": logger.warning("<"+classe+"> "+message);
				break;
			
			case "finest": logger.finest("<"+classe+"> "+message);
				break;
		
			case "severe": logger.severe("<"+classe+"> "+message);
				break;
			default : logger.info("<"+classe+"> "+message);
				break;
		}
			
		
	}
	
}
