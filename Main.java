import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader reader= new BufferedReader(new FileReader(args[0]));
			String line=reader.readLine();
			ArrayList<String> listoflines=new ArrayList<>();
			while (line!=null) {
				listoflines.add(line);
				line=reader.readLine();
			}
			ArrayList<String[]> seperatedlines= new ArrayList<>();               //reading the file and seperate every line to perform operations
			for (String lineeee : listoflines) {
				String[] linearray=lineeee.split(" ");
				seperatedlines.add(linearray);
			}
			reader.close();
		
			AvlTree mafiaFamily=new AvlTree();
			int count=0;
			for (String[] strings : seperatedlines) {
				if (count==0) {
					mafiaFamily.addMember(Float.valueOf(strings[1]), strings[0]);
					count++;
				}
				else if (strings[0].equals("MEMBER_IN")) {
					mafiaFamily.addMember(Float.valueOf(strings[2]), strings[1]);
				}
				else if (strings[0].equals("MEMBER_OUT")) {
					mafiaFamily.deleteMember(Float.valueOf(strings[2]), strings[1]);
				}
				else if (strings[0].equals("INTEL_TARGET")) {
					mafiaFamily.intelTarget(Float.valueOf(strings[2]),Float.valueOf(strings[4]), strings[1], strings[3]);
				}
				else if (strings[0].equals("INTEL_RANK")) {
					mafiaFamily.sameRankFinder(Float.valueOf(strings[2]), strings[1]);
				}
				else if (strings[0].equals("INTEL_DIVIDE")) {
					mafiaFamily.intelDivide();;
				}
			}
			
			//System.out.println(mafiaFamily.log);
			try {
				FileWriter abc=new FileWriter(args[1]);
				abc.write(mafiaFamily.log);
				abc.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getStackTrace().toString());
		}

	}

}
