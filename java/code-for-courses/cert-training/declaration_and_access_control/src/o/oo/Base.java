package o.oo;

public class Base {
	public String pub = "This is a public access value";
	protected String prot = "This is a protected access value";
	String def = "This is a default access value"; //default package level
	private String priv = "This is a private access value";
	
	public void pubmethod() {
		System.out.println("public access method");
	}
	public void protmethod() {
		System.out.println("protected access method");
	}
	public void defmethod() {
		System.out.println("default access method");
	}
	private void privmethod() {
		System.out.println("private access method");
	}
}