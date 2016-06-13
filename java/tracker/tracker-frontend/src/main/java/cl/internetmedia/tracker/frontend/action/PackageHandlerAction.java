package cl.internetmedia.tracker.frontend.action;

import java.util.Collection;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

import cl.internetmedia.tracker.common.dto.PackageHandlerDto;
import cl.internetmedia.tracker.delegate.service.PackageHandlerService;

public class PackageHandlerAction extends ActionSupport implements SessionAware {
	private static final Logger LOG = LoggerFactory.getLogger(PackageHandlerAction.class);

	private static final long serialVersionUID = 1L;

	// For SessionAware
	private Map<String, Object> session;
	private PackageHandlerDto pkgHandler;
	private PackageHandlerService packageHandlerService;
	private Collection<PackageHandlerDto> pkgHandlers;

    public String execute() {
    	LOG.info("Registering {}, {} as a package handler", getPkgHandler().getLastname(), getPkgHandler().getName());
    	getPkgHandler().setId(packageHandlerService.create(getPkgHandler()));

    	return SUCCESS;
    }

    public String list() {
    	LOG.info("Retrieving all package handlers");
    	setPkgHandlers(packageHandlerService.findAll());

    	return SUCCESS;
    }

	public void validate() {
		//Service service = new Service();
		//service.validate(someField);
//		if (getPkgHandler().getName().length() == 0) {
//			addFieldError("username", getText("user.name.required"));
//		}
//		if (getPkgHandler().getLastname().length() == 0) {
//			addFieldError("lastname", getText("user.lastname.required"));
//		}
//		if (getPkgHandler().getPhonenumber().length() == 0) {
//			addFieldError("phonenumber", getText("user.phonenumber.required"));
//		}

	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;	
	}

	public PackageHandlerService getPackageHandlerService() {
		return packageHandlerService;
	}

	public void setPackageHandlerService(PackageHandlerService packageHandlerService) {
		this.packageHandlerService = packageHandlerService;
	}

	public PackageHandlerDto getPkgHandler() {
		return this.pkgHandler;
	}

	public void setPkgHandler(PackageHandlerDto packageHandler) {
		this.pkgHandler = packageHandler;
	}

	public Collection<PackageHandlerDto> getPkgHandlers() {
		return pkgHandlers;
	}


	public void setPkgHandlers(Collection<PackageHandlerDto> packageHandlers) {
		this.pkgHandlers = packageHandlers;
	}
}
