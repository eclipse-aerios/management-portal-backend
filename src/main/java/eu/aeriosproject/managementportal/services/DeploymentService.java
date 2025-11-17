package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.NetworkPort;
import eu.aeriosproject.managementportal.models.Service;
import eu.aeriosproject.managementportal.models.ServiceComponent;
import eu.aeriosproject.managementportal.models.ServiceComponentEntity;

import java.util.List;

public interface DeploymentService {
    public List<Service> listServices();
    public Service getServiceById(String serviceId);
    public List<ServiceComponentEntity> listServiceComponents(String id);
    public List<ServiceComponent> listServiceComponentsWithPorts(String id);
    public List<NetworkPort> getNetworkPortDetailsFromComponent(String componentId);
    public void deleteServiceById(String serviceId);
    public void clearServiceById(String serviceId);
    public void initOrchestration(String tosca, String serviceId);
    public void reOrchestrate(String serviceId);
}
