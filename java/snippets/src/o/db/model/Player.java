package o.db.model;

public class Player {
	private String name;
	private String place;
	private int id;

	public Player() {};

	public Player(String a, String b){
		name = a;
		place = b;
	}

	public String getName(){
		return name;
	}

	public void setName(String a){
		name = a;
	}

	public String getPlace(){
		return place;
	}

	public void setPlace(String b){
		place = b;
	}

	public int getId(){
		return id;
	}

	public void setId(int s){
		id = s;
	}

}
