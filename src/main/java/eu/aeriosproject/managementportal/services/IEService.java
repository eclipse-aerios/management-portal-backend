package eu.aeriosproject.managementportal.services;

import eu.aeriosproject.managementportal.models.InfrastructureElement;

import java.util.List;

public interface IEService {
    public List<InfrastructureElement> list();
    public InfrastructureElement getById(String ieId);
}
