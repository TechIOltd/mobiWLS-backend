/**
 * 
 */
package com.techio.mobiwls.rest.resources;

import java.util.ArrayList;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.sun.jersey.spi.resource.Singleton;
import com.techio.mobiwls.rest.NotFoundException;

/**
 * @author slavikos
 * 
 */
@Path("/server")
@Singleton
public class ServerResource extends BaseResource {

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	@Path("/{serverName}")
	public ServerInfo getServerInfo(@PathParam("serverName") String serverName) {
		try {
			MBeanServer domainRuntimeServer = lookupDomainRuntimeServiceMBean();
			ObjectName domainMBean = (ObjectName) domainRuntimeServer
					.getAttribute(domainRuntimeServiceMBeanObjectName,
							"DomainConfiguration");
			ObjectName serverMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Servers");
			
			
			ObjectName serverMBean = searchObjectNameWithName(domainRuntimeServer, serverMBeans, serverName);
			if(serverMBean != null) {
				return constructMinimalServerInfo(domainRuntimeServer, serverMBean);
			} else {
				throw new NotFoundException();
			}
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}
		
	}

	protected ObjectName searchObjectNameWithName(MBeanServer server, ObjectName[] objectNames,
			String name) {
		for (ObjectName mbean : objectNames) {
			if (getStringAttribute(server, mbean, "Name").equals(name)) {
				return mbean;
			} else
				continue;
		}
		return null;
	}

	@GET
	@Produces({ JSON_CONTENT_TYPE })
	public List<ServerInfo> getServerNames() {
		List<ServerInfo> _serversInfo = new ArrayList<ServerInfo>();

		try {
			MBeanServer domainRuntimeServer = lookupDomainRuntimeServiceMBean();
			ObjectName domainMBean = (ObjectName) domainRuntimeServer
					.getAttribute(domainRuntimeServiceMBeanObjectName,
							"DomainConfiguration");

			// fetch domain server
			ObjectName serverMBeans[] = (ObjectName[]) domainRuntimeServer
					.getAttribute(domainMBean, "Servers");

			for (ObjectName mbean : serverMBeans) {
				_serversInfo.add(constructMinimalServerInfo(
						domainRuntimeServer, mbean));
			}
			return _serversInfo;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}
}
