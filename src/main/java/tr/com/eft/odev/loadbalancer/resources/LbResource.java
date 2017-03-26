package tr.com.eft.odev.loadbalancer.resources;

import java.io.IOException;
import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.kjetland.ddsl.dropwizard.DdslService;
import com.kjetland.ddsl.dropwizard.DdslServiceId;

import tr.com.eft.odev.loadbalancer.config.LbConfiguration;
import tr.com.eft.odev.loadbalancer.utils.HttpClientUtil;

@Path("/loadbalancer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class LbResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(LbResource.class);

	private final LbConfiguration config;
	private final DdslService ddslService;
	private final Map<String, DdslServiceId> servisMap;

	public LbResource(LbConfiguration config, DdslService ddslService) {
		this.config = config;
		this.ddslService = ddslService;
		this.servisMap = ImmutableMap.of("service1", config.getService1(), "service2", config.getService2(), "service3",
				config.getService3());
	}

	@Path("/serviceName/{serviceName}")
	@GET
	@RolesAllowed("MEMBER")
	public String service(@PathParam("serviceName") String serviceName) {
		return fetch(servisMap.get(serviceName));
	}

	@Path("/broadcast")
	@GET
	@RolesAllowed("ADMIN")
	public String allService() {
		String response = "";
		for (DdslServiceId service : servisMap.values()) {
			response += fetch(service) + "\n";
		}
		return response;
	}

	@Path("/service1")
	@GET
	public String service1() {
		return fetch(config.getService1());
	}

	@Path("/service2")
	@GET
	public String service2() {
		return fetch(config.getService2());
	}

	@Path("/service3")
	@GET
	public String service3() {
		return fetch(config.getService3());
	}

	@Path("serviceGr1")
	@GET
	public String serviceGr1() {
		return fetch(config.getService1()) + "\n" + fetch(config.getService2());
	}

	private String fetch(DdslServiceId service) {
		String strResponse = null;
		String url = null;
		try {
			url = ddslService.getBestLocationUrl(service);
		} catch (Exception e) {
			LOGGER.error("Error fetching best url to other service: " + e.getMessage());
			return "Error fetching best url to other service: " + e.getMessage();
		}

		LOGGER.info("Using other Service: " + url);

		try {
			String result = HttpClientUtil.fetchGet(url);
			strResponse = config.getServiceName() + " got response from " + url + ": " + result;
		} catch (IOException e) {
			strResponse = "Error fetching data from other server: " + url;
			LOGGER.error(strResponse);
		}

		return strResponse;
	}

}
