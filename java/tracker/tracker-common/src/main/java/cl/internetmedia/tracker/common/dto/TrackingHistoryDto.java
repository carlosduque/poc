package cl.internetmedia.tracker.common.dto;

import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrackingHistoryDto implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(TrackingHistoryDto.class);

	private static final long serialVersionUID = 1L;

	private long id;
	private Date date;
	private double latitude;
	private double longitude;
	private PackageHandlerDto packageHandlerDto;

	public TrackingHistoryDto() {
		super();
	}

	public TrackingHistoryDto(long id, Date date, double latitude, double longitude, PackageHandlerDto pkgHandlerDto) {
		super();
		this.id = id;
		this.date = date;
		this.latitude = latitude;
		this.longitude = longitude;
		this.packageHandlerDto = pkgHandlerDto;
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public PackageHandlerDto getPackageHandlerDto() {
		return packageHandlerDto;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setPackageHandlerDto(PackageHandlerDto pkgHandlerDto) {
		this.packageHandlerDto = pkgHandlerDto;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackingHistoryDto [id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", packageHandlerDto=");
		builder.append(packageHandlerDto);
		builder.append("]");
		return builder.toString();
	}

}
