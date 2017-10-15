package IntegrationTest;

public class DateTime {
	
	private int year, month, day, hour, minute, second;
	private String label;
	
	public DateTime(int year, int month, int day, int hour, int minute, int second){
		
		this.year=year;
		this.month=month;
		this.day=day;
		this.hour=hour;
		this.minute=minute;
		this.second=second;
		
		label=day+"/"+month+"/"+year+" - "+hour+":"+minute+":"+second;
		
	}
	
	public String getLabel(){
		return label;
	}
	
}
