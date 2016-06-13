package cl.internetmedia.tracker.backend.dao;

import java.util.Collection;
import java.util.Date;

import cl.internetmedia.tracker.backend.model.TrackingHistory;

public interface TrackingHistoryDao {
	public long save(TrackingHistory trackingHistory);
	public Collection<TrackingHistory> findByDates(Date startDate, Date endDate);
	public void saveOrUpdate(TrackingHistory trackingHistory);
	public void delete(TrackingHistory trackingHistory);
	public void delete(long id);
}
