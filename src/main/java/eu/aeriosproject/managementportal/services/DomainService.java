package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.Domain;
import eu.aeriosproject.managementportal.models.InfrastructureElement;

import java.util.List;

public interface DomainService {
    public List<Domain> list();
    public Domain getById(String domainId);
    public Domain getEntrypointDomain(Boolean reduced);
    public List<InfrastructureElement> getDomainIEs(String domainId);
}
