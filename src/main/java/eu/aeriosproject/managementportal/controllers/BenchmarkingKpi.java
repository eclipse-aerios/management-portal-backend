package eu.aeriosproject.managementportal.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import eu.aeriosproject.managementportal.services.BenchmarkingKpiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/portal-backend/benchmarking/kpi")
public class BenchmarkingKpi {
    @Autowired
    private BenchmarkingKpiService benchmarkingKpiService;

    @GetMapping("/federated-domains")
    public ResponseEntity<JsonNode> getNumberOfFederatedDomains() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfFederatedDomains());
    }

    @GetMapping("/infrastructure-elements")
    public ResponseEntity<JsonNode> getNumberOfInfrastructureElements() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfInfrastructureElements());
    }

    @GetMapping("/container-management-frameworks")
    public ResponseEntity<JsonNode> getNumberOfContainerManagementFrameworks() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfContainerManagementFrameworks());
    }

    @GetMapping("/cpu-architectures")
    public ResponseEntity<JsonNode> getNumberOfCpuArchitectures() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfCpuArchitectures());
    }
    @GetMapping("/services")
    public ResponseEntity<JsonNode> getNumberOfUserServices() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfUserServices());
    }

    @GetMapping("/services/components")
    public ResponseEntity<JsonNode> getNumberOfServicesComponent() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfServicesComponents());
    }

    @GetMapping("/authenticated-users")
    public ResponseEntity<JsonNode> getNumberOfAuthenticatedUsers() {
        return ResponseEntity.ok(benchmarkingKpiService.getNumberOfAuthenticatedUsers());
    }
}
