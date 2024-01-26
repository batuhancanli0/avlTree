/*
 Member class to keep information like rank,gms,name and left right child member for tree structure 
 and constructors
  */

public class Member {
    String nameSurname;
    float gms;
	Member rightMember;
	Member leftMember;
	int rank;
	
	
	public Member() {
		gms=0;
		rightMember=null;
		leftMember=null;
		rank=0;
	}
	
	public Member(String nameSurname,float gms,Member right,Member left) {
		this.nameSurname=nameSurname;
		this.gms=gms;
		rightMember=right;
		leftMember=left;
		rank=0;
		
	}

}
