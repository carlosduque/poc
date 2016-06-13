package cl.internetmedia.tracker.backend.dao.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import cl.internetmedia.tracker.backend.dao.TrackingHistoryDao;
import cl.internetmedia.tracker.backend.model.TrackingHistory;

@Repository
@Transactional
public class TrackingHistoryDaoHibernateImpl implements TrackingHistoryDao {
	private static final Logger LOG = LoggerFactory.getLogger(TrackingHistoryDaoHibernateImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public long save(TrackingHistory trackingHistory) {
		LOG.debug("save(): {}", trackingHistory);
		long id = (Long) this.sessionFactory.getCurrentSession().save(trackingHistory);
		return id;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Collection<TrackingHistory> findByDates(Date startDate, Date endDate) {
		LOG.debug("findByDates(): {} - {}", startDate, endDate);
		Query query = this.sessionFactory.getCurrentSession().getNamedQuery("findByDates");
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		Collection<TrackingHistory> result = query.list();
		return result;
	}
	
	@Transactional
	@Override
	public void saveOrUpdate(TrackingHistory trackingHistory) {
		LOG.debug("saveOrUpdate(): {}", trackingHistory);
		this.sessionFactory.getCurrentSession().saveOrUpdate(trackingHistory);
	}

	@Transactional
	@Override
	public void delete(TrackingHistory trackingHistory) {
		LOG.debug("delete(): {}", trackingHistory);
		this.sessionFactory.getCurrentSession().delete(trackingHistory);
	}

	@Transactional
	@Override
	public void delete(long id) {
		TrackingHistory trackingHistory = null;
		LOG.debug("delete(): {}", id);
		trackingHistory = (TrackingHistory) this.sessionFactory.getCurrentSession().createQuery("from TrackingHistory" + " where id = ?").setLong(0, id).uniqueResult();
		this.sessionFactory.getCurrentSession().delete(trackingHistory );
	}
}
