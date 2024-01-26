
import java.util.ArrayList;
import java.util.Arrays;

/*
  I used avl data structure to deal with the rank difference and rotations in the family
  */

public class AvlTree {
	Member root;
	String log="";        // to write operation's results
	
	
	public int getRank(Member member) {     //this function for getting -1 rank (height) for null nodes.So nodes  with 0 child will be 0 rank
		if (member==null) {
			return -1;
		}
		return member.rank;
	}
	public Member rightRotation(Member member1) {    //this code does a right rotation and update ranks of affected nodes
		Member member1left=member1.leftMember;
		Member member1leftright=member1left.rightMember;
		member1left.rightMember=member1;
		member1.leftMember=member1leftright;
		member1.rank=Math.max(getRank(member1.leftMember), getRank(member1.rightMember))+1;
		member1left.rank=Math.max(getRank(member1left.leftMember), getRank(member1left.rightMember))+1;
		
		return member1left;
		
	}
	public Member leftRotation(Member member1) {       //this code does a left rotation and update ranks of affected nodes
		Member member1right=member1.rightMember;
		Member member1rightleft=member1right.leftMember;
		member1right.leftMember=member1;
		member1.rightMember=member1rightleft;
		member1.rank=Math.max(getRank(member1.leftMember), getRank(member1.rightMember))+1;
		member1right.rank=Math.max(getRank(member1right.leftMember), getRank(member1right.rightMember))+1;
		
		
		
		
		return member1right;
	}
	public Member addMember(Member root,float gms,String nameSurname) { //this function recursively finds the right position for new members and adds them to tree
		                                                                //then check balances and does the operations if necessary to keep rank diff 1
		Member newMember= new Member(nameSurname,gms,null,null);
		if (root==null) {
			root=newMember;
			
		}
		else if (root.gms<newMember.gms) {
			log=log+root.nameSurname+" welcomed "+newMember.nameSurname+"\n";
			root.rightMember=addMember(root.rightMember, gms, nameSurname);
	
		}
		else if (root.gms>newMember.gms) {
			log=log+root.nameSurname+" welcomed "+newMember.nameSurname+"\n";
			root.leftMember=addMember(root.leftMember, gms, nameSurname);
			
			
		}
		root.rank=Math.max(getRank(root.leftMember), getRank(root.rightMember))+1;
		int balance=getRank(root.rightMember)-getRank(root.leftMember);
		//left heavy tree if balance<-1
		//according to left member's balance it operates right rotation or left_right rotation
		if (balance<-1) {
			int balanceleft=getRank(root.leftMember.rightMember)-getRank(root.leftMember.leftMember);
			if (balanceleft<=0) {
				return rightRotation(root);
			}
			else {
				root.leftMember=leftRotation(root.leftMember);
				return rightRotation(root);
			}
		}
		//right heavy tree if balance>1
				//according to right member's balance it operates left rotation or right_left rotation
		if (balance>1) {
			int balanceright =getRank(root.rightMember.rightMember)-getRank(root.rightMember.leftMember);
			if (balanceright>=0) {
				return leftRotation(root);
			}
			else {
				root.rightMember=rightRotation(root.rightMember);
				return leftRotation(root);
				
			}
		}
		return root;
	}
	//to use add without root
	public void addMember(float gms,String nameSurname) {
		root=addMember(root, gms, nameSurname);
	}
	//this func will help in deletion to find min of right subtree to replace.
	public Member findMin(Member root) {
		if (root!=null) {
			while (root.leftMember!=null) {
				root=root.leftMember;
			}
		}
		return root;
	}
	public Member deleteMember(Member root,float gms,String nameSurname) { /*this func deletes a member and replaces them like binary tree and then check for balances 
	                                                                         like add func  */
		if (root==null) {
			return null;
		}
		else if (gms>root.gms) {
			root.rightMember=deleteMember(root.rightMember, gms, nameSurname);
		}
		else if (gms<root.gms) {
			root.leftMember=deleteMember(root.leftMember, gms, nameSurname);
		}
		else if (gms==root.gms) {
			//leaf node case
			if (root.leftMember==null && root.rightMember==null) {
				log=log+nameSurname+" left the family, replaced by nobody"+"\n";
				return null;
			}
			//2 child case
			else if (root.rightMember!=null && root.leftMember!=null) {
				Member replecament=findMin(root.rightMember);
				log=log+nameSurname+" left the family, replaced by "+replecament.nameSurname+"\n";
				root.gms=replecament.gms;
				root.nameSurname=replecament.nameSurname;
				root.rightMember=deleteMember(root.rightMember, replecament.gms, replecament.nameSurname);
				String[] logArray=log.split("\n");
				ArrayList<String> logArrayList = new ArrayList<String>(Arrays.asList(logArray));
				logArrayList.remove(logArrayList.size()-1);
				String newlog="";
				for (String logs : logArrayList) {
					newlog=newlog+logs+"\n";
				}
				log=newlog;
			}
			//1 child cases
			else if (root.leftMember!=null) {
				log=log+nameSurname+" left the family, replaced by "+root.leftMember.nameSurname+"\n";
				return root.leftMember;
			}
			else if (root.rightMember!=null) {
				log=log+nameSurname+" left the family, replaced by "+root.rightMember.nameSurname+"\n";
				return root.rightMember;
			}
		}
		root.rank=Math.max(getRank(root.leftMember), getRank(root.rightMember))+1;
		int balance=getRank(root.rightMember)-getRank(root.leftMember);
		if (balance<-1) {
			int balanceleft=getRank(root.leftMember.rightMember)-getRank(root.leftMember.leftMember);
			if (balanceleft<=0) {
				return rightRotation(root);
			}
			else {
				root.leftMember=leftRotation(root.leftMember);
				return rightRotation(root);
			}
		}
		if (balance>1) {
			int balanceright =getRank(root.rightMember.rightMember)-getRank(root.rightMember.leftMember);
			if (balanceright>=0) {
				return leftRotation(root);
			}
			else {
				root.rightMember=rightRotation(root.rightMember);
				return leftRotation(root);
				
			}
		}
	
	return root;	
	}
	public void deleteMember(float gms,String nameSurname) {
		root=deleteMember(root, gms, nameSurname);
	}
	//to use it in intel_target method
	ArrayList<Float> gmsPath1=new ArrayList<>();
	ArrayList<String> namePath1=new ArrayList<>();
	ArrayList<Float> gmsPath2=new ArrayList<>();
	ArrayList<String> namePath2=new ArrayList<>();
	/*
	 * path filler fills the paths of root to given node.This paths will be used to find lowest common anchestor
	 * */
	public Member pathFiller(Member root,float gms,String nameSurname,ArrayList<String>namePath,ArrayList<Float>gmsPath) {
		
		if (root==null) {
			return root;
		}
		gmsPath.add(root.gms);
		namePath.add(root.nameSurname);
		if (root.gms<gms) {
			return pathFiller(root.rightMember,gms, nameSurname,namePath,gmsPath);
		}
		else if (gms<root.gms) {
			return pathFiller(root.leftMember, gms, nameSurname,namePath,gmsPath);
		}
		else if (gms==root.gms) {
			
			
			return root;
		}
		return root;
	}
	public Member pathFiller(float gms,String nameSurname,ArrayList<String>namePath,ArrayList<Float>gmsPath) {
		return pathFiller(root, gms, nameSurname,namePath,gmsPath);
	}
	//to make floats 3digit after the floating point.
	public String floatNormalizer(float float1) {

		String formattedString = String.format("%.3f", float1);
		String returnString= formattedString.replace(',', '.');
		return returnString;
		
	}
	//intelTarget looks path of the two nodes and finds their lca
	public void intelTarget(float gms1,float gms2,String nameSurname1,String nameSurname2) {
		pathFiller(gms1, nameSurname1, namePath1, gmsPath1);
		pathFiller(gms2, nameSurname2, namePath2, gmsPath2);
		
		
		
		int iteration = Math.min(gmsPath1.size(), gmsPath2.size());
		for (int i = 0; i < iteration; i++) {
			if (gmsPath1.size()==1||gmsPath2.size()==1) {
				String gmString=floatNormalizer(gmsPath1.get(i));
				log=log+"Target analysis result: "+namePath1.get(i)+" "+gmString+"\n";
				break;
			}
			if (gmsPath1.get(i).equals(gmsPath2.get(i))) {
				continue;
				
			} else {
				if (i==0) {
					String gmString=floatNormalizer(gmsPath1.get(i));
					log=log+"Target analysis result: "+namePath1.get(i)+" "+gmString+"\n";
					break;
					
				}
				else {
					String gmString=floatNormalizer(gmsPath1.get(i));
					log=log+"Target analysis result: "+namePath1.get(i-1)+" "+gmString+"\n";
					break;
					
				}

			}
		}
		gmsPath1.clear();
		namePath1.clear();
		gmsPath2.clear();
		namePath2.clear();
		
	}
	//to use in sameRankFinder search returns the member and we can take its rank
	public Member searchMember(Member root,float gms,String nameSurname) {
		
		if (root==null) {
			return root;
		}
		if (root.gms<gms) {
			return searchMember(root.rightMember, gms, nameSurname);
		}
		else if (gms<root.gms) {
			return searchMember(root.leftMember, gms, nameSurname);
		}
		else if (gms==root.gms) {
			
			return root;
		}
		return root;
	}
	ArrayList<Float> sameRankgms= new ArrayList<>();
	ArrayList<String> sameRankNameSurname=new ArrayList<>();
	public void sameRankFinder(Member root,float gms,String nameSurname,int rank) {
		
		//this function traverses the tree in-order traversal and finds member with same rank with given rank
		
		if (root==null) {
			return;
		}
		
		sameRankFinder(root.leftMember, gms,nameSurname,rank);
		if (root.rank==rank) {
			sameRankgms.add(root.gms);
			sameRankNameSurname.add(root.nameSurname);
		}
		sameRankFinder(root.rightMember, gms, nameSurname,rank);
		
	}
	public void sameRankFinder(float gms,String nameSurname) {
		Member targetMember=searchMember(root, gms, nameSurname);
		
		sameRankFinder(root, gms, nameSurname,targetMember.rank);
		log=log+"Rank Analysis Result: ";
		for (int i = 0; i < sameRankgms.size(); i++) {
			String gmString=floatNormalizer(sameRankgms.get(i));
			log=log+sameRankNameSurname.get(i)+" ";
			log=log+gmString+" ";
		}
		log=log+"\n";
		sameRankgms.clear();
		sameRankNameSurname.clear();
	}
	//this function finds largest independent set size by looking largest independent set size of rooted with other members if root is a member then size is 1+largest indep set size of its 
	//grandchildrens else its largest indep set size of its childrens and we are taking max in the end
	public int intelDivide(Member root) {
		if (root==null) {
			return 0;
		}
		int without=intelDivide(root.leftMember)+intelDivide(root.rightMember);
		int with=1;
		if (root.leftMember!=null) {
			with=with+intelDivide(root.leftMember.leftMember)+intelDivide(root.leftMember.rightMember);
		}
		if (root.rightMember!=null) {
			with=with+intelDivide(root.rightMember.rightMember)+intelDivide(root.rightMember.leftMember);
		}
		return Math.max(without, with);
	}
	public void intelDivide() {
		int max=intelDivide(root);
		log=log+"Division Analysis Result: "+String.valueOf(max)+"\n";
	}
	

}
