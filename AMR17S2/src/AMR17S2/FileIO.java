package AMR17S2;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileIO {

	private File instructionFile; 
	private File memberFile; 
	private File reportFile; 
	private File resultFile; 
	private Club c;
	private StringBuilder sbr;

	public FileIO(String[] filenames){
		memberFile=new File(filenames[0]);
		instructionFile=new File(filenames[1]);
		resultFile=new File(filenames[2]);
		reportFile=new File(filenames[3]);
		c=new Club();
		sbr = new StringBuilder(); 
	}
	
	public void saveResult(){
		try {
			PrintWriter out=new PrintWriter(new FileOutputStream(resultFile));
			out.println(c.toString().trim());
			out.close();
		} 
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void saveReport(){
		try {
			PrintWriter out=new PrintWriter(new FileWriter(reportFile, true));
			out.println(sbr.toString().trim());
			out.close();
		} 
		catch(Exception e){
			e.printStackTrace();
		}
	}	
	public void readInstruction(){
		try{
			Scanner scan=new Scanner(instructionFile);
			while (scan.hasNextLine()){
				String instruction=scan.nextLine(); 
				Scanner sc=new Scanner(instruction); 
				String keyword;
				if (sc.hasNext()) {
					keyword=sc.next(); 
					if (sc.hasNextLine()) {
						if (keyword.equalsIgnoreCase("add")){
							String[] param=sc.nextLine().split(";");
							String[] compulsory={"",""};
							int n=c.getClub().size();
							for(int i=0;i<param.length;i++){
								String[] element=param[i].trim().split(" ");
								if(element[0].equalsIgnoreCase("number")) compulsory[0]=element[1].trim();
								else if(element[0].equalsIgnoreCase("name")){
									for(int k=1;k<element.length;k++) compulsory[1]+=(element[k]+" ");
									compulsory[1]=compulsory[1].trim();
								}
								
							}
							int r=0;
							for(Member me : c.getClub()){
								if(me.getNumber().equals(compulsory[0])) r++;
							}
							if(compulsory[0]!="" && r==0 && compulsory[1]!=""){
								c.addMember(compulsory[0], compulsory[1]);
								if((!compulsory[0].isEmpty() && compulsory[0].matches("[0-9]+") && compulsory[0].length()==5) && (!compulsory[1].isEmpty() && compulsory[1].matches("[a-zA-Z ]+"))){
									for(int i=0;i<param.length;i++){
										String[] element=param[i].trim().split(" ");
										if(element[0].equalsIgnoreCase("birthday")) c.getClub().get(n).setBirthday(element[1]);
										else if(element[0].equalsIgnoreCase("mileage")){
											if(element[1].matches("\\d+\\.\\d+[a-zA-Z]+") || element[1].matches("\\d+[a-zA-Z]+")){
												if(element[1].substring((element[1].length()-2), (element[1].length())).equalsIgnoreCase("km")){
													Pattern p = Pattern.compile("[^0-9.]+");
													Matcher m = p.matcher(element[1]);
													c.getClub().get(n).setMileage(Double.valueOf(m.replaceAll("").trim()));
												}
											}
										}
										else if(element[0].equalsIgnoreCase("points")){
											if(element[1].matches("\\d+\\.\\d+") || element[1].matches("\\d+")) c.getClub().get(n).setPoints(Double.parseDouble(element[1]));
										}
										else if(element[0].equalsIgnoreCase("tier")) c.getClub().get(n).setTier(element[1]);
										else if(element[0].equalsIgnoreCase("address")){
											String[] ad={""};
											for(int j=1;j<element.length;j++) ad[0]+=(" "+element[j]);
											ad[0]=ad[0].trim();
											c.getClub().get(n).setAddress(ad);
										}
									}
								}
							}
							else if(compulsory[0]!="" && r!=0){
								for(int k=0;k<c.getClub().size();k++){
									if(c.getClub().get(k).getNumber().equals(compulsory[0])){
										for(int i=0;i<param.length;i++){
											String[] element=param[i].trim().split(" ");
											if(element[0].equalsIgnoreCase("Name")){
												c.getClub().get(k).setName(compulsory[1]);
											}
											else if(element[0].equalsIgnoreCase("birthday")) c.getClub().get(k).setBirthday(element[1]);
											else if(element[0].equalsIgnoreCase("mileage")){
												if(element[1].matches("\\d+\\.\\d+[a-zA-Z]+") || element[1].matches("\\d+[a-zA-Z]+")){
													if(element[1].substring((element[1].length()-2), (element[1].length())).equalsIgnoreCase("km")){
														Pattern p = Pattern.compile("[^0-9.]+");
														Matcher m = p.matcher(element[1]);
														c.getClub().get(k).setMileage(Double.valueOf(m.replaceAll("").trim()));
													}
												}
											}
											else if(element[0].equalsIgnoreCase("points")){
												if(element[1].matches("\\d+\\.\\d+") || element[1].matches("\\d+")) c.getClub().get(k).setPoints(Double.parseDouble(element[1]));
											}
											else if(element[0].equalsIgnoreCase("tier")) c.getClub().get(k).setTier(element[1]);
											else if(element[0].equalsIgnoreCase("address")){
												String[] ad={""};
												for(int j=1;j<element.length;j++) ad[0]+=(" "+element[j]);
												ad[0]=ad[0].trim();
												c.getClub().get(k).setAddress(ad);
											}
										}
									}
								}
							}
						}
						if(keyword.equalsIgnoreCase("delete")){
							if(sc.hasNext()){
								String[] param=new String[2];
								param[0]=sc.next();
								if(sc.hasNextLine()){
									param[1]=sc.nextLine();
									c.deleteMember(param[1].trim());
								}
							}
						}
						if(keyword.equalsIgnoreCase("earn")){
							String[] param=sc.nextLine().split(";");
							String[] element = new String[2];
							element[0]=param[0].trim().split(" ")[1];
							element[1]=param[1].trim().split(" ")[1];
							for(Member memb : c.getClub()){
								if(memb.getNumber().equals(element[0])){
									Pattern p = Pattern.compile("[^0-9.]+");
									Matcher m = p.matcher(element[1]);
									memb.earnPoints(Double.valueOf(m.replaceAll("").trim()));
									if(memb.getPoints()<5000) memb.setTier("Silver");
									else if(memb.getPoints()>=5000 && memb.getPoints()<10000) memb.setTier("Gold");
									else if(memb.getPoints()>=10000) memb.setTier("Platinum");
								}
							}
						}
						if(keyword.equalsIgnoreCase("redeem")){
							String[] param=sc.nextLine().split(";");
							String[] element = new String[2];
							element[0]=param[0].trim().split(" ")[1];
							element[1]=param[1].trim().split(" ")[1];
							for(Member memb : c.getClub()){
								if(memb.getNumber().equals(element[0]) && memb.getPoints()>=Double.parseDouble(element[1])){
									memb.redeemPoints(Double.parseDouble(element[1]));
									if(memb.getPoints()<5000) memb.setTier("Silver");
									else if(memb.getPoints()>=5000 && memb.getPoints()<10000) memb.setTier("Gold");
									else memb.setTier("Platinum");
								}
							}
						}
						if(keyword.equalsIgnoreCase("query")){
							if(sc.hasNext()){
								String[] param=new String[2];
								param[0]=sc.next();
								if(sc.hasNext()){
									param[1]=sc.next();
									if(param[1].equalsIgnoreCase("Platinum") || param[1].equalsIgnoreCase("Gold") || param[1].equalsIgnoreCase("Silver")){
										ArrayList<Member> mem=new ArrayList<Member>();
										for(int i=0;i<c.getClub().size();i++){
											if(c.getClub().get(i).getTier().equalsIgnoreCase(param[1])) mem.add(c.getClub().get(i));
										}
										int min=0;
										Member temp;
										for(int i=0;i<mem.size();i++){
											min=i;
											for(int j=i+1;j<mem.size();j++){
												String[] name1=mem.get(min).getName().split(" ");
												String[] name2=mem.get(j).getName().split(" ");
												if(name1[0].length()<name2[0].length()){
													for(int k=0;k<name1[0].length();k++){
														if(name1[0].charAt(k)>name2[0].charAt(k)){
															min=j;
															break;
														}
														else if(name1[0].charAt(k)<name2[0].charAt(k)) break;
														else if(name1[0].charAt(k)==name2[0].charAt(k)) continue;
													}
												}
												else if(name1[0].length()>name2[0].length()){
													for(int k=0;k<name2[0].length();k++){
														if(name1[0].charAt(k)>name2[0].charAt(k)){
															min=j;
															break;
														}
														else if(name1[0].charAt(k)<name2[0].charAt(k)) break;
														else if(k<name2[0].length()-1 && name1[0].charAt(k)==name2[0].charAt(k)) continue;
														else if(k==name2[0].length()-1 && name1[0].charAt(k)==name2[0].charAt(k)){
															min=j;
															break;
														}
													}
												}
												else if(name1[0].length()==name2[0].length()){
													for(int k=0;k<name1[0].length();k++){
														if(name1[0].charAt(k)>name2[0].charAt(k)){
															min=j;
															break;
														}
														else if(name1[0].charAt(k)<name2[0].charAt(k)) break;
														else if(k<name1[0].length()-1 && name1[0].charAt(k)==name2[0].charAt(k)) continue;
														else if(k==name1[0].length()-1 && name1[0].charAt(k)==name2[0].charAt(k)){
															if(name1[1].length()<name2[1].length()){
																for(int l=0;l<name1[0].length();l++){
																	if(name1[1].charAt(l)>name2[1].charAt(l)){
																		min=j;
																		break;
																	}
																	else if(name1[1].charAt(l)<name2[1].charAt(l)) break;
																	else if(name1[1].charAt(l)==name2[1].charAt(l)) continue;
																}
															}
															else if(name1[1].length()>name2[1].length()){
																for(int l=0;l<name2[0].length();l++){
																	if(name1[1].charAt(l)>name2[1].charAt(l)){
																		min=j;
																		break;
																	}
																	else if(name1[1].charAt(l)<name2[1].charAt(l)) break;
																	else if(l<name2[1].length()-1 && name1[1].charAt(l)==name2[1].charAt(l)) continue;
																	else if(l==name2[1].length()-1 && name1[0].charAt(l)==name2[0].charAt(l)){
																		min=j;
																		break;
																	}
																}
															}
															else if(name1[1].length()==name2[1].length()){
																for(int l=0;l<name1[1].length();l++){
																	if(name1[1].charAt(l)>name2[1].charAt(l)){
																		min=j;
																		break;
																	}
																	else if(name1[1].charAt(l)<name2[1].charAt(l)) break;
																	else if(l<name1[1].length()-1 && name1[1].charAt(l)==name2[1].charAt(l)) continue;
																	else if(l==name1[1].length()-1 && name1[1].charAt(l)==name2[1].charAt(l)){
																		if(Integer.parseInt(mem.get(min).getNumber())>Integer.parseInt(mem.get(j).getNumber())){
																			min=j;
																		}
																	}
																}
															}
														}
													}
												}
											}
											if(i!=min){
												temp=mem.get(i);
												mem.set(i,mem.get(min));
												mem.set(min,temp);
											}
										}
										sbr.append("----query tier "+param[1]+" ----"+"\n");
										sbr.append(mem.get(0).toString());
										for(int i=1;i<mem.size();i++){
											sbr.append("\n");
											sbr.append(mem.get(i).toString());
										}
										sbr.append("-------------------------"+"\n");
										sbr.append("\n"); 
									}
									else if(param[0].equalsIgnoreCase("age")){
										DecimalFormat df=new DecimalFormat("0");
										sbr.append("----query age mileage----"+"\n");
										sbr.append("Total Airline Members: "+c.getClub().size());
										sbr.append("\n");
										sbr.append("Age based mileage distribution");
										sbr.append("\n");
										double mil1=0;
										for(Member m : c.getClub()){
											if(m.getAge()>0 && m.getAge()<=8) mil1+=m.getMileage();
										}
										sbr.append("(0,8]: "+df.format(mil1));
										sbr.append("\n");
										double mil2=0;
										for(Member m : c.getClub()){
											if(m.getAge()>8 && m.getAge()<=18) mil2+=m.getMileage();
										}
										sbr.append("(8,18]: "+df.format(mil2));
										sbr.append("\n");
										double mil3=0;
										for(Member m : c.getClub()){
											if(m.getAge()>18 && m.getAge()<=65) mil3+=m.getMileage();
										}
										sbr.append("(18,65]: "+df.format(mil3));
										sbr.append("\n");
										double mil4=0;
										for(Member m : c.getClub()){
											if(m.getAge()>65) mil4+=m.getMileage();
										}
										sbr.append("(65,-): "+df.format(mil4));
										sbr.append("\n");
										double mil5=0;
										for(Member m : c.getClub()){
											if(m.getAge()==0) mil5+=m.getMileage();
										}
										sbr.append("Unknown: "+df.format(mil5)+"\n");
										sbr.append("-------------------------"+"\n\n");
									}
								}
							}
						}
					}
					else continue;
					sc.close();
				}
				else continue;
			}
			scan.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	public void readMember(){
		try{
			Scanner scann=new Scanner(memberFile);
			Scanner sca=new Scanner(memberFile);
			int n1=0;
			while (scann.hasNextLine()){
				if(scann.nextLine().isEmpty()) n1++;
			}
			String[] n2=new String[n1+1];
			for(int i=0;i<n1+1;i++) n2[i]="";
			int n3=0;
			while (sca.hasNextLine()){
				String strin=sca.nextLine();
				if(!strin.isEmpty()) n2[n3]+=(strin+";");
				else n3++;
			}
			for(int i=0;i<n1+1;i++){
				if(!n2[i].isEmpty()){
					String[] n4=n2[i].split(";");
					String[] compulsory = {"",""};
					for(int j=0;j<n4.length;j++){
						String[] n5=n4[j].split(" ");
						if(n5[0].equalsIgnoreCase("number")) compulsory[0]=n5[1].trim();
						else if(n5[0].equalsIgnoreCase("name")){
							for(int k=1;k<n5.length;k++) compulsory[1]+=(n5[k]+" ");
							compulsory[1]=compulsory[1].trim();
						}
					}
					int n=0;
					for(Member me : c.getClub()){
						if(me.getNumber().equals(compulsory[0])) n++;
					}
					if(compulsory[0]!="" && n==0 && compulsory[1]!=""){
						c.addMember(compulsory[0], compulsory[1]);
						if((!compulsory[0].isEmpty() && compulsory[0].matches("[0-9]+") && (compulsory[0].length()==5)) && (!compulsory[1].isEmpty() && compulsory[1].matches("[a-zA-Z ]+"))){
							for(int j=0;j<n4.length;j++){
								String[] n5=n4[j].split(" ");
								if(n5[0].equalsIgnoreCase("birthday")) c.getClub().get(c.getClub().size()-1).setBirthday(n5[1]);
								else if(n5[0].equalsIgnoreCase("mileage")){
									if(n5[1].matches("\\d+\\.\\d+[a-zA-Z]*") || n5[1].matches("\\d+[a-zA-Z]*")){
										if(n5[1].substring((n5[1].length()-2), (n5[1].length())).equalsIgnoreCase("km")){
											Pattern p = Pattern.compile("[^0-9.]+");
											Matcher m = p.matcher(n5[1]);
											c.getClub().get(c.getClub().size()-1).setMileage(Double.valueOf(m.replaceAll("").trim()));
										}
									}
								}
								else if(n5[0].equalsIgnoreCase("points")){
									if(n5[1].matches("\\d+\\.\\d+") || n5[1].matches("\\d+")) c.getClub().get(c.getClub().size()-1).setPoints(Double.parseDouble(n5[1]));
								}
								else if(n5[0].equalsIgnoreCase("tier")) c.getClub().get(c.getClub().size()-1).setTier(n5[1]);
								else if(n5[0].equalsIgnoreCase("address")){
									int l=1;
									for(int k=j+1;k<n4.length;k++){
										String[] n6=n4[k].trim().split(" ");
										if(!n6[0].isEmpty() && !n6[0].equalsIgnoreCase("number") && !n6[0].equalsIgnoreCase("name") && !n6[0].equalsIgnoreCase("birthday") && !n6[0].equalsIgnoreCase("tier") && !n6[0].equalsIgnoreCase("milage") && !n6[0].equalsIgnoreCase("points") && !n6[0].equalsIgnoreCase("address")) l++;
										else break;
									}
									String[] ad=new String[l];
									for(int k=0;k<l;k++) ad[k]="";
									for(int k=1;k<n5.length;k++) ad[0]+=(" "+n5[k]);
									ad[0]=ad[0].trim();
									if(l>1){
										for(int k=j+1;k<j+l;k++){
											String[] n6=n4[k].split(" ");
											for(int x=0;x<n6.length;x++) ad[k-j]+=(" "+n6[x]);
											ad[k-j]=ad[k-j].trim();
										}
									}
									c.getClub().get(c.getClub().size()-1).setAddress(ad);
									j=j+l-1;
								}
							}
						}
					}
					else if(compulsory[0]!="" && n!=0){
						for(int y=0;y<c.getClub().size();y++){
							if(c.getClub().get(y).getNumber().equals(compulsory[0])){
								for(int j=0;j<n4.length;j++){
									String[] n5=n4[j].split(" ");
									if(n5[0].equalsIgnoreCase("name")){
										String nm="";
										for(int k=1;k<n5.length;k++) nm+=n5[k]+" ";
										nm=nm.trim();
										c.getClub().get(y).setName(nm);
									}
									else if(n5[0].equalsIgnoreCase("birthday")) c.getClub().get(y).setBirthday(n5[1]);
									else if(n5[0].equalsIgnoreCase("mileage")){
										if(n5[1].matches("\\d+\\.\\d+[a-zA-Z]+") || n5[1].matches("\\d+[a-zA-Z]+")){
											if(n5[1].substring((n5[1].length()-2), (n5[1].length())).equalsIgnoreCase("km")){
												Pattern p = Pattern.compile("[^0-9.]+");
												Matcher m = p.matcher(n5[1]);
												c.getClub().get(y).setMileage(Double.valueOf(m.replaceAll("").trim()));
											}
										}
									}
									else if(n5[0].equalsIgnoreCase("points")){
										if(n5[1].matches("\\d+\\.\\d+") || n5[1].matches("\\d+")) c.getClub().get(y).setPoints(Double.parseDouble(n5[1]));
									}
									else if(n5[0].equalsIgnoreCase("tier")) c.getClub().get(y).setTier(n5[1]);
									int l=1;
									for(int k=j+1;k<n4.length;k++){
										String[] n6=n4[k].split(" ");
										if(!n6[0].isEmpty() && !n6[0].equalsIgnoreCase("number") && !n6[0].equalsIgnoreCase("name") && !n6[0].equalsIgnoreCase("birthday") && !n6[0].equalsIgnoreCase("tier") && !n6[0].equalsIgnoreCase("milage") && !n6[0].equalsIgnoreCase("points") && !n6[0].equalsIgnoreCase("address")) l++;
									}
									String[] ad=new String[l];
									for(int k=0;k<l;k++) ad[k]="";
									for(int k=1;k<n5.length;k++) ad[0]+=(" "+n5[k]);
									ad[0]=ad[0].trim();
									if(l>1){
										for(int k=j+1;k<j+l-1;k++){
											String[] n6=n4[k].split(" ");
											for(int x=0;x<n6.length;x++) ad[k-j]+=(" "+n6[x]);
											ad[k-j]=ad[k-j].trim();
										}
									}
									c.getClub().get(y).setAddress(ad);
								}
							}
						}
					}
				}
			}
			sca.close();
			scann.close();
		}
		catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
}