package cl.internetmedia.tracker.common.dto;

import java.io.Serializable;

public class PackageHandlerDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;
  private String name;
  private String lastname;
  private String phonenumber;

  public PackageHandlerDto() {
	  super();
  }

  public PackageHandlerDto(long id, String name, String lastname, String phonenumber) {
    this.id = id;
    this.name = name;
    this.lastname = lastname;
    this.phonenumber = phonenumber;
  }

  public long getId() {
	return id;
  }

  public String getName() {
	return name;
  }

  public String getLastname() {
	return lastname;
  }

  public String getPhonenumber() {
	return phonenumber;
  }
  
  public void setId(long id) {
	this.id = id;
  }

  public void setName(String name) {
	this.name = name;
  }

  public void setLastname(String lastname) {
	this.lastname = lastname;
  }
  
  public void setPhonenumber(String phonenumber) {
	this.phonenumber = phonenumber;
  }

@Override
public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("PackageHandlerDto [id=");
	builder.append(id);
	builder.append(", name=");
	builder.append(name);
	builder.append(", lastname=");
	builder.append(lastname);
	builder.append(", phonenumber=");
	builder.append(phonenumber);
	builder.append("]");
	return builder.toString();
}
}
