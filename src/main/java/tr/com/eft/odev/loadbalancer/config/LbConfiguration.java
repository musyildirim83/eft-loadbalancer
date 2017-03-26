package tr.com.eft.odev.loadbalancer.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kjetland.ddsl.dropwizard.DdslConfig;
import com.kjetland.ddsl.dropwizard.DdslServiceId;

import io.dropwizard.Configuration;

public class LbConfiguration extends Configuration {

	@JsonProperty
	@Valid
	@NotNull
	private DdslConfig ddslConfig;

	@JsonProperty
	@NotNull
	private String serviceName;

	@JsonProperty
	@Valid
	private DdslServiceId service1;

	@JsonProperty
	@Valid
	private DdslServiceId service2;

	@JsonProperty
	@Valid
	private DdslServiceId service3;

	public DdslConfig getDdslConfig() {
		return ddslConfig;
	}

	public void setDdslConfig(DdslConfig ddslConfig) {
		this.ddslConfig = ddslConfig;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public DdslServiceId getService1() {
		return service1;
	}

	public void setService1(DdslServiceId service1) {
		this.service1 = service1;
	}

	public DdslServiceId getService2() {
		return service2;
	}

	public void setService2(DdslServiceId service2) {
		this.service2 = service2;
	}

	public DdslServiceId getService3() {
		return service3;
	}

	public void setService3(DdslServiceId service3) {
		this.service3 = service3;
	}

}
