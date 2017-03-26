package tr.com.eft.odev.loadbalancer.health;

import com.codahale.metrics.health.HealthCheck;

public class LbHealthCheck extends HealthCheck{

	@Override
	protected Result check() throws Exception {
		return Result.healthy();
	}
}
