package cl.internetmedia.tracker.backend.model;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.internetmedia.tracker.common.dto.TrackingHistoryDto;

public class TrackingHistory {
	private static final Logger LOG = LoggerFactory.getLogger(TrackingHistory.class);
	private long id;
	private Date date;
	private double latitude;
	private double longitude;
	private PackageHandler packageHandler;
	
	public static TrackingHistory convert(TrackingHistoryDto trackingHistoryDto) {
		LOG.debug("Converting {} ", trackingHistoryDto);
		TrackingHistory trackingHistory = new TrackingHistory();
		if(trackingHistoryDto.getId() > 0)
			trackingHistory.setId(trackingHistoryDto.getId());
		if(trackingHistoryDto.getDate() != null)
			trackingHistory.setDate(trackingHistoryDto.getDate());
		if(trackingHistoryDto.getPackageHandlerDto() != null)
			trackingHistory.setPackageHandler(PackageHandler.valueOf(trackingHistoryDto.getPackageHandlerDto()));
		trackingHistory.setLatitude(trackingHistoryDto.getLatitude());
		trackingHistory.setLongitude(trackingHistoryDto.getLongitude());
		LOG.debug("        --> ", trackingHistory);
		return trackingHistory;
	}

	public TrackingHistory (){
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

	public PackageHandler getPackageHandler() {
		return packageHandler;
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

	public void setPackageHandler(PackageHandler packageHandler) {
		this.packageHandler = packageHandler;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TrackingHistory [id=");
		builder.append(id);
		builder.append(", date=");
		builder.append(date);
		builder.append(", latitude=");
		builder.append(latitude);
		builder.append(", longitude=");
		builder.append(longitude);
		builder.append(", packageHandler=");
		builder.append(packageHandler);
		builder.append("]");
		return builder.toString();
	}

	public static TrackingHistory valueOf(TrackingHistoryDto dto) {
		LOG.debug("Converting {} ", dto);
		TrackingHistory th = new TrackingHistory();
		if(dto.getId() > 0)
			th.setId(dto.getId());
		if(dto.getDate() != null)
			th.setDate(dto.getDate());
		th.setLatitude(dto.getLatitude());
		th.setLongitude(dto.getLongitude());
		if(dto.getPackageHandlerDto() != null) {
			PackageHandler pkgHandler = PackageHandler.valueOf(dto.getPackageHandlerDto());
			th.setPackageHandler(pkgHandler);
		}
		LOG.debug("        --> ", th);
		return th;
	}
}
