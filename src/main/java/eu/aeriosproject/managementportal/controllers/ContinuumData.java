package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.models.Domain;
import eu.aeriosproject.managementportal.models.InfrastructureElement;
import eu.aeriosproject.managementportal.services.DomainService;
import eu.aeriosproject.managementportal.services.IEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/portal-backend/continuumdata")
public class ContinuumData {

    @Autowired
    private DomainService domainService;
    @Autowired
    private IEService ieService;

    @GetMapping(value = "/domains/{domainId}")
    public Domain getDomain(@PathVariable String domainId, @RequestHeader("Authorization") String bearerToken) {
        return domainService.getById(domainId);
    }

    @GetMapping(value = {"/domains", "/domains/"})
    public List<Domain> listDomains() {
        return domainService.list();
    }

    @GetMapping(value = "/domains/entrypoint")
    public Domain getEntrypoint(@RequestParam(name="reduced",required = false, defaultValue = "false") Boolean reduced) {
        System.out.println(reduced);
        return domainService.getEntrypointDomain(reduced);
    }

    @GetMapping(value = {"/domains/{domainId}/infrastructure-elements", "/domains/{domainId}/infrastructure-elements/"})
    public List<InfrastructureElement> getDomainIEs(@PathVariable String domainId) {
        return domainService.getDomainIEs(domainId);
    }

    @GetMapping(value = "/infrastructure-elements/{ieId}")
    public InfrastructureElement getInfrastructureElement(@PathVariable String ieId) {
        return ieService.getById(ieId);
    }

    @GetMapping(value = {"/infrastructure-elements", "/infrastructure-elements/", "/infrastructureElements", "/infrastructureElements/"})
    public List<InfrastructureElement> listInfrastructureElements() {
        return ieService.list();
    }

}
