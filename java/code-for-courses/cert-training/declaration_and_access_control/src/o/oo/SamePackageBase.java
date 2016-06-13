package o.oo;

import o.oo.Base;

public class SamePackageBase extends Base {
  public static void main(String[] args) {
    SamePackageBase samePackage = new SamePackageBase();
    System.out.println(samePackage.pub);
    System.out.println(samePackage.prot);
    System.out.println(samePackage.def);
    //System.out.println(samePackage.priv);  //Compiler error: priv has private access in Base

	samePackage.pubmethod();
	samePackage.protmethod();
	samePackage.defmethod();
	//samePackage.privmethod();  //Compiler error: cannot find symbol
  }
}