package cl.internetmedia.tracker.backend.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.internetmedia.tracker.backend.dao.TrackingHistoryDao;
import cl.internetmedia.tracker.backend.model.TrackingHistory;
import cl.internetmedia.tracker.common.dto.TrackingHistoryDto;
import cl.internetmedia.tracker.delegate.service.TrackingHistoryService;

public class TrackingHistoryServiceImpl implements TrackingHistoryService {
	private static final Logger LOG = LoggerFactory.getLogger(TrackingHistoryServiceImpl.class);
	private TrackingHistoryDao trackingHistoryDao;

	TrackingHistoryServiceImpl() {
	}
	
	public void setTrackingHistoryDao(TrackingHistoryDao dao) {
		this.trackingHistoryDao = dao;
	}

	@Override
	public long create(TrackingHistoryDto trackingHistoryDto) {
		long id = 0;
		TrackingHistory trackingHistory = TrackingHistory.valueOf(trackingHistoryDto);
		LOG.debug("Saving to database: ", trackingHistory);
		id = trackingHistoryDao.save(trackingHistory);
		return id;
	}

	@Override
	public Collection<TrackingHistoryDto> findByDates(Date startDate, Date endDate) {
		LOG.debug("Retrieving tracking histories by date [{} - {}]", startDate, endDate);

		Collection<TrackingHistory> trackingHistories = trackingHistoryDao.findByDates(startDate, endDate);
		Collection<TrackingHistoryDto> dtos = new ArrayList<TrackingHistoryDto>();

		TrackingHistoryDto dto = null;
		for(TrackingHistory trackingHistory : trackingHistories){
			dto = new TrackingHistoryDto();
			if(trackingHistory.getId() > 0)
				dto.setId(trackingHistory.getId());
			if(trackingHistory.getDate() != null)
				dto.setDate(trackingHistory.getDate());
			dto.setLatitude(trackingHistory.getLatitude());
			dto.setLongitude(trackingHistory.getLongitude());
			if(trackingHistory.getPackageHandler() != null) {
				dto.setPackageHandlerDto(trackingHistory.getPackageHandler().getPackageHandlerDto());
			}
			LOG.debug(" * dto: {} ", dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void update(TrackingHistoryDto trackingHistoryDto) {
		LOG.debug("Updating tracking history");
		TrackingHistory trackingHistory = TrackingHistory.valueOf(trackingHistoryDto);
		trackingHistoryDao.saveOrUpdate(trackingHistory);
	}

}
