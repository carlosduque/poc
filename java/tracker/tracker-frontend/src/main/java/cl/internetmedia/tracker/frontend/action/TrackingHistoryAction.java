package cl.internetmedia.tracker.frontend.action;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
import cl.internetmedia.tracker.common.dto.TrackingHistoryDto;
import cl.internetmedia.tracker.delegate.service.PackageHandlerService;
import cl.internetmedia.tracker.delegate.service.TrackingHistoryService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
 
public class TrackingHistoryAction extends ActionSupport implements SessionAware {
	private static final Logger LOG = LoggerFactory.getLogger(TrackingHistoryAction.class);

	private static final long serialVersionUID = 1L;

	// For SessionAware
	private Map<String, Object> session;
	Collection<TrackingHistoryDto> crumbs;
	private TrackingHistoryService trackingHistoryService;
	private Date startDate;
	private Date endDate;

	public String execute() {
		long someDays = 5 * 24 * 60 * 60 * 1000L;
		endDate = new Date();
		startDate = new Date(endDate.getTime() - someDays);
		LOG.info("Getting tracking data for date range: [startDate:{} - endDate:{}]", startDate, endDate);
		setCrumbs(trackingHistoryService.findByDates(getStartDate(), getEndDate()));
		LOG.info("Crumbs: {} ", getCrumbs());

		return SUCCESS;
    }
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	public TrackingHistoryService getTrackingHistoryService() {
		return trackingHistoryService;
	}

	public void setTrackingHistoryService(TrackingHistoryService trackingHistoryService) {
		this.trackingHistoryService = trackingHistoryService;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Collection<TrackingHistoryDto> getCrumbs() {
		return crumbs;
	}
 
	public void setCrumbs(Collection<TrackingHistoryDto> crumbs) {
		this.crumbs = crumbs;
	}

}