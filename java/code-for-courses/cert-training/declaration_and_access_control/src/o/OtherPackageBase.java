package o;

import o.oo.Base;

public class OtherPackageBase extends Base {
  public static void main(String[] args) {
    OtherPackageBase otherPackageBase = new OtherPackageBase();
    System.out.println(otherPackageBase.pub);
    System.out.println(otherPackageBase.prot);
    //System.out.println(otherPackageBase.def);  //Compiler error: def is not public in Base; cannot be accessed from outside package
    //System.out.println(otherPackageBase.priv);  //Compiler error: priv has private access in Base

	otherPackageBase.pubmethod();
	otherPackageBase.protmethod();
	otherPackageBase.defmethod();
	//otherPackageBase.privmethod();  //Compiler error: cannot find symbol
  }
}