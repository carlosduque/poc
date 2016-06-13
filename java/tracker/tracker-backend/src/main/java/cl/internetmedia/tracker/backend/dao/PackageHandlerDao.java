package cl.internetmedia.tracker.backend.dao;

import java.util.Collection;
import cl.internetmedia.tracker.backend.model.PackageHandler;

public interface PackageHandlerDao {
	public long save(PackageHandler packageHandler);
	public PackageHandler findById(long id);
	public PackageHandler findByPhonenumber(String phonenumber);
	public void saveOrUpdate(PackageHandler packageHandler);
	public void delete(PackageHandler packageHandler);
	public void delete(long id);
    public Collection<PackageHandler> findAll();
}
