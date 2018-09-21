package AMR17S2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDate {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	private String dateinString;
	private Date date;

	public CustomDate(String cd){
		dateinString=cd;
		String[] temp;
		int[] a = new int[3];
		if(dateinString.matches("\\d+\\D\\d+\\D\\d+")){
			temp=dateinString.split("\\D");
			if(temp.length==3){
				for(int i=0;i<temp.length;i++) a[i]=Integer.parseInt(temp[i]);
				int remainder1=a[2] % 4;
				int remainder2=a[2] % 100;
				int remainder3=a[2] % 400;
				if(((a[1]==1||a[1]==3||a[1]==5||a[1]==7||a[1]==8||a[1]==10||a[1]==12) && a[0]<=31 && a[0]>0)||((a[1]==4||a[1]==6||a[1]==9||a[1]==11) && a[0]<=30 && a[0]>0)||(((remainder1==0 && remainder2!=0) || (remainder3==0)) && a[1]==2 && a[0]<=29 && a[0]>0)||(remainder1!=0 && a[1]==2 && a[0]<=28 && a[0]>0)){
					for(int i=0;i<temp.length-1;i++) {
						if(temp[i].length()<2) temp[i]="0"+temp[i];
					}
					dateinString=temp[0]+"/"+temp[1]+"/"+temp[2];
				} else dateinString="";
			}
		}
		try{
			date=dateFormat.parse(dateinString);
		}
		catch(Exception e){
			date=null;
		}
	}	
	public boolean isValid(){
		Calendar cal=Calendar.getInstance();
		if(date!=null && date.before(cal.getTime())) return true;
		else return false;
	}	
	public String toString(){
		return dateinString;
	}
}