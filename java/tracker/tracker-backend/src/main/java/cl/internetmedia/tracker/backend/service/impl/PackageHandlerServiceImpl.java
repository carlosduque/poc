package cl.internetmedia.tracker.backend.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cl.internetmedia.tracker.backend.dao.PackageHandlerDao;
import cl.internetmedia.tracker.backend.model.PackageHandler;
import cl.internetmedia.tracker.common.dto.PackageHandlerDto;
import cl.internetmedia.tracker.delegate.service.PackageHandlerService;

public class PackageHandlerServiceImpl implements PackageHandlerService {
	private static final Logger LOG = LoggerFactory.getLogger(PackageHandlerServiceImpl.class);
	private PackageHandlerDao packageHandlerDao;

	PackageHandlerServiceImpl() {
	}
	
	public void setPackageHandlerDao(PackageHandlerDao packageHandlerDao) {
		this.packageHandlerDao = packageHandlerDao;
	}

	@Override
	public long create(PackageHandlerDto packageHandlerDto) {
		long id = 0;
		PackageHandler pkgHandler = new PackageHandler();
		pkgHandler.setName(packageHandlerDto.getName());
		pkgHandler.setLastname(packageHandlerDto.getLastname());
		pkgHandler.setPhonenumber(packageHandlerDto.getPhonenumber());
		LOG.debug("----------------------------------service-");
		LOG.debug("Saving to database: {}", pkgHandler);
		LOG.debug("----------------------------------service-");
		id = packageHandlerDao.save(pkgHandler);
		return id;
	}

	@Override
	public Collection<PackageHandlerDto> findAll() {
		LOG.debug("----------------------------------service-");
		LOG.debug("Retrieving package handlers");
		LOG.debug("----------------------------------service-");
		Collection<PackageHandler> pkgHandlers = packageHandlerDao.findAll();
		Collection<PackageHandlerDto> dtos = new ArrayList<PackageHandlerDto>();
		
		PackageHandlerDto pkgHandlerDto = null;
		for(PackageHandler pkgHandler : pkgHandlers){
			pkgHandlerDto = pkgHandler.getPackageHandlerDto();
			dtos.add(pkgHandlerDto);
		}
		return dtos;
	}

	@Override
	public PackageHandlerDto findByPhonenumber(String phonenumber) {
		LOG.debug("----------------------------------service-");
		LOG.debug("Retrieving a package handler by phone number: {}", phonenumber);
		LOG.debug("----------------------------------service-");
		PackageHandler pkgHandler = packageHandlerDao.findByPhonenumber(phonenumber);
		return pkgHandler.getPackageHandlerDto();
	}

	@Override
	public void update(PackageHandlerDto packageHandlerDto) {
		PackageHandler pkgHandler = PackageHandler.valueOf(packageHandlerDto);
		LOG.debug("----------------------------------service-");
		LOG.debug("Updating a package handler: {}", pkgHandler);
		LOG.debug("----------------------------------service-");
		packageHandlerDao.saveOrUpdate(pkgHandler);
	}

	@Override
	public void delete(PackageHandlerDto packageHandlerDto) {
		PackageHandler pkgHandler = PackageHandler.valueOf(packageHandlerDto);
		LOG.debug("----------------------------------service-");
		LOG.debug("Deleting a package handler: {}", pkgHandler);
		LOG.debug("----------------------------------service-");
		packageHandlerDao.delete(pkgHandler);
	}

}
