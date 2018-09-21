package AMR17S2;

import java.text.DecimalFormat;
import java.util.Calendar;

public class Member {

	private String number;
	private String name;
	private String birthday;
	private String tier;
	private double mileage;
	private double points;
	private String address;
	
	public Member(String num, String na){
		number=num;
		name=na;
		birthday="";
		tier="Silver";
		mileage=0;
		points=0;
		address="";
	}
	
	public String getNumber(){
		return number;
	}
	public String getName(){
		return name;
	}
	public String getBirthday(){
		return birthday;
	}
	public int getAge(){
		int age;
		if(birthday.isEmpty()) age=0;
		else{
			String[] b=birthday.split("/");
			int[] bd=new int[b.length];
			for(int i=0;i<bd.length;i++) bd[i]=Integer.parseInt(b[i]);
			Calendar cal=Calendar.getInstance(); 
			int[] today=new int[3];
			today[2]=cal.get(Calendar.YEAR);
			today[1]=cal.get(Calendar.MONTH);
			today[0]=cal.get(Calendar.DATE);
			if(today[1]-bd[1]>0) age=today[2]-bd[2];
			else if(today[1]-bd[1]==0){
				if(today[0]-bd[0]>=0) age=today[2]-bd[2];
				else age=today[2]-bd[2]-1;
			}
			else age=today[2]-bd[2]-1;
		}
		return age;
	}
	public String getTier(){
		return tier;
	}
	public double getMileage(){
		return mileage;
	}
	public double getPoints(){
		return points;
	}
	public String getAddress(){
		return address;
	}
	private boolean validNumber(){
		return !number.isEmpty() && number.matches("[0-9]+") && number.length()==5;
	}
	private boolean validName(){
		return !name.isEmpty() && name.matches("[a-zA-Z ]+");
	}
	public boolean isValidToAdd(){
		return validNumber() && validName();
	}
	public void setNumber(String num){
		if(validNumber()) number=num;
	}
	public void setName(String na){
		if(validName()) name=na;
	}
	public void setBirthday(String birth){
		CustomDate bir=new CustomDate(birth);
		if(bir.isValid()) birthday=bir.toString();
	}
	public void setTier(String t){
		if(t.equalsIgnoreCase("platinum")) tier="Platinum";
		else if(t.equalsIgnoreCase("gold")) tier="Gold";
		else tier="Silver";
	}
	public void setMileage(double mile){
		if(mile>0) mileage=mile;
	}
	public void setPoints(double p){
		if(p>0) points=p;
	}
	public void setAddress(String[] addr){
		String a="";
		if(addr.length>0 && addr[addr.length-1].length()>=4 && addr[addr.length-1].substring((addr[addr.length-1].length()-4), (addr[addr.length-1].length())).matches("[0-9]+")){
			for(int i=0;i<addr.length;i++) a+=(addr[i].trim()+" ");
		}
		if(a.trim().split(",").length==4) address=a.trim();
	}
	public void earnPoints(double m){
		if(tier.equals("Platinum")) points+=m;
		else if(tier.equals("Gold")) points+=m*0.5;
		else points+=m*0.25;
		mileage+=m;
	}
	public void redeemPoints(double po){
		points-=po;
	}
	public String toString(){
		StringBuilder sb = new StringBuilder(); 
		DecimalFormat df=new DecimalFormat("0.00");
		sb.append("number "+getNumber()+"\n");
		sb.append("name "+getName()+"\n");
		if(!getBirthday().isEmpty()) sb.append("birthday "+getBirthday()+"\n");
		sb.append("tier "+getTier()+"\n");
		if(getMileage()!=0) sb.append("mileage "+df.format(getMileage())+"km"+"\n");
		if(getPoints()>0) sb.append("points "+df.format(getPoints())+"\n");
		if(getAddress()!="") sb.append("address "+getAddress()+"\n");
		return sb.toString();
	}
}