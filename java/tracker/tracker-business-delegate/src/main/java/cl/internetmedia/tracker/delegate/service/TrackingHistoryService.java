package cl.internetmedia.tracker.delegate.service;

import java.util.Collection;
import java.util.Date;

import cl.internetmedia.tracker.common.dto.TrackingHistoryDto;
/**
 * TODO: add some real javadoc
 * @author carlos.duque
 *
 */
public interface TrackingHistoryService {
	long create(TrackingHistoryDto trackingHistoryDto);
    void update(TrackingHistoryDto trackingHistoryDto);
    Collection<TrackingHistoryDto> findByDates(Date startDate, Date endDate);
}
