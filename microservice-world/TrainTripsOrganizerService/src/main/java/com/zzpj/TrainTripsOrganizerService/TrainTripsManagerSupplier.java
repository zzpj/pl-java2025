package com.zzpj.TrainTripsOrganizerService;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.List;

public class TrainTripsManagerSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;

    public TrainTripsManagerSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {

        ServiceInstance int1 = new DefaultServiceInstance("001", serviceId, "localhost", 8021, false);
        ServiceInstance int2 = new DefaultServiceInstance("002", serviceId, "localhost", 8022, false);
        ServiceInstance int3 = new DefaultServiceInstance("003", serviceId, "localhost", 8023, false);
        return Flux.just(List.of(int1, int2, int3));
    }
}
