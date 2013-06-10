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
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

import com.sun.jersey.spi.resource.Singleton;

/**
 * @author slavikos
 * 
 */
@Path("/server")
@Singleton
public class ServerResource extends BaseResource {

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
				ServerInfo sinfo = new ServerInfo();
				sinfo.setName(getStringAttribute(domainRuntimeServer, mbean,
						"Name"));
				_serversInfo.add(sinfo);
			}
			return _serversInfo;
		} catch (Exception ex) {
			throw new WebApplicationException(ex);
		}

	}
}
