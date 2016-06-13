package cl.internetmedia.tracker.backend.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cl.internetmedia.tracker.backend.dao.PackageHandlerDao;
import cl.internetmedia.tracker.backend.model.PackageHandler;

@Repository
@Transactional
public class PackageHandlerDaoHibernateImpl implements PackageHandlerDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(PackageHandlerDaoHibernateImpl.class);

	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public long save(PackageHandler packageHandler) {
		LOG.debug("**********************************dao*****");
		LOG.debug("save(): {}", packageHandler);
		LOG.debug("**********************************dao*****");
		long id = (Long) this.sessionFactory.getCurrentSession().save(packageHandler);
		return id;		
	}

	@Transactional(readOnly = true)	
    public PackageHandler findById(long id){
		PackageHandler packageHandler = null;
		LOG.debug("**********************************dao*****");
		LOG.debug("findById(): {}", id);
		LOG.debug("**********************************dao*****");
		packageHandler = (PackageHandler) this.sessionFactory.getCurrentSession().createQuery(
                "from PackageHandler where id = ?").setLong(0, id).uniqueResult();
    	return packageHandler;
    }

	@Transactional(readOnly = true)	
    public PackageHandler findByPhonenumber(String phonenumber){
		PackageHandler packageHandler = null;
		LOG.debug("**********************************dao*****");
		LOG.debug("findByPhonenumber(): {}", phonenumber);
		LOG.debug("**********************************dao*****");
		packageHandler = (PackageHandler) this.sessionFactory.getCurrentSession().createQuery(
                "from PackageHandler where phonenumber = ?").setString(0, phonenumber).uniqueResult();
    	return packageHandler;
    }

	@Transactional
	@SuppressWarnings("unchecked")
    public void saveOrUpdate(PackageHandler packageHandler){
		LOG.debug("**********************************dao*****");
		LOG.debug("saveOrUpdate(): {}", packageHandler);
		LOG.debug("**********************************dao*****");
    	this.sessionFactory.getCurrentSession().saveOrUpdate(packageHandler);
    }

	public void delete(PackageHandler packageHandler) {
		LOG.debug("**********************************dao*****");
		LOG.debug("delete(): {}", packageHandler);
		LOG.debug("**********************************dao*****");
		this.sessionFactory.getCurrentSession().delete(packageHandler);		
	}

	@Transactional
	public void delete(long id) {
		PackageHandler packageHandler = null;
		LOG.debug("**********************************dao*****");
		LOG.debug("delete(): {}", id);
		LOG.debug("**********************************dao*****");
		packageHandler = (PackageHandler) this.sessionFactory.getCurrentSession().createQuery("from PackageHandler" + " where id = ?").setLong(0, id).uniqueResult();
		this.sessionFactory.getCurrentSession().delete(packageHandler);		
	}

	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public Collection<PackageHandler> findAll() {
		List<PackageHandler> list = null;
		LOG.debug("**********************************dao*****");
		LOG.debug("findAll()");
		list = this.sessionFactory.getCurrentSession().createQuery("from PackageHandler order by id").list();
		LOG.debug("found {}", list.size());
		LOG.debug("**********************************dao*****");
		return list;
	}
}
