package cl.internetmedia.tracker.delegate.service;

import java.util.Collection;

import cl.internetmedia.tracker.common.dto.PackageHandlerDto;
/**
 * TODO: add some real javadoc
 * @author carlos.duque
 *
 */
public interface PackageHandlerService {
	long create(PackageHandlerDto packageHandler);
    Collection<PackageHandlerDto> findAll();
    PackageHandlerDto findByPhonenumber(String phonenumber);
    void update(PackageHandlerDto packageHandler);
    void delete(PackageHandlerDto packageHandler);
}
