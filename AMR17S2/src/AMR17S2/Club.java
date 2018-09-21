package AMR17S2;

import java.util.ArrayList;

public class Club {
	
	//instance fields
	private ArrayList<Member> memberlist;
	
	//constructor
	public Club(){
		memberlist=new ArrayList<Member>();
	}
	
	public void addMember(String num, String na){
		Member mem=new Member(num,na);
		if(mem.isValidToAdd()){
			memberlist.add(mem);
		}
	}
	public void deleteMember(String s){
		s=s.trim();
		if(s.matches("[0-9]+") && s.length()==5){
			int i=0;
			while(i<memberlist.size()){
				if(memberlist.get(i).getNumber().equals(s)) memberlist.remove(i);
				else i++;
			}
		}
		else if(s.matches(("[a-zA-Z ]+"))){
			int[] m=new int[memberlist.size()];
			for(int i=0;i<memberlist.size();i++){
				String[] s1=memberlist.get(i).getName().trim().split(" ");
				String[] s2=s.split(" ");
				boolean x=true;
				try{
					for(int j=0;j<Math.max(s1.length, s2.length);j++){
						if(!s1[j].equalsIgnoreCase(s2[j])) x=false;
					}
				}
				catch(Exception e){
					x=false;
				}
				if(x) m[i]=i;
				else m[i]=-1;
			}
			for(int i=m.length-1;i>=0;i--){
				if(m[i]>=0) memberlist.remove(m[i]);
			}
		}
	}
	public ArrayList<Member> getClub(){
		return memberlist;
	}
	public void setClub(ArrayList<Member> memberlist){
		this.memberlist=memberlist;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		for(Member m : memberlist){
			sb.append(m.toString());
			sb.append("\n"); 
		}
		return sb.toString(); 
	}	
}