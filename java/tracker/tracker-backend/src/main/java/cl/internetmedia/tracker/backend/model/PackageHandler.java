package cl.internetmedia.tracker.backend.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.internetmedia.tracker.common.dto.PackageHandlerDto;

public class PackageHandler {
	private static final Logger LOG = LoggerFactory.getLogger(PackageHandler.class);
	private long id;
	private String name;
	private String lastname;
	private String phonenumber;

	public static PackageHandler valueOf(PackageHandlerDto dto) {
		LOG.debug("Converting {} ", dto);
		PackageHandler pkgHandler = new PackageHandler();
		if(dto.getId() > 0)
			pkgHandler.setId(dto.getId());
		if(dto.getName() != null && !dto.getName().isEmpty())
			pkgHandler.setName(dto.getName());
		if(dto.getLastname() != null && !dto.getLastname().isEmpty())
			pkgHandler.setLastname(dto.getLastname());
		if(dto.getPhonenumber() != null && !dto.getPhonenumber().isEmpty())
			pkgHandler.setPhonenumber(dto.getPhonenumber());
		LOG.debug("        --> ", pkgHandler);
		return pkgHandler;
	}

	public PackageHandler (){
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the last name
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the last name to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the phone number
	 */
	public String getPhonenumber() {
		return phonenumber;
	}
	/**
	 * @param phonenumber the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PackageHandler [id=");
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
	
	public PackageHandlerDto getPackageHandlerDto() {
		PackageHandlerDto dto = new PackageHandlerDto();
		dto.setId(this.id);
		dto.setName(this.name);
		dto.setLastname(this.lastname);
		dto.setPhonenumber(this.phonenumber);
		return dto;
	}
}
