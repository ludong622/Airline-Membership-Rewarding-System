package AMR17S2;

public class AMR {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length==4){
			FileIO fio=new FileIO(args);
			fio.readMember();
			fio.readInstruction();
			fio.saveResult();
			fio.saveReport();
		}
	}

}
