package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.models.NetworkPort;
import eu.aeriosproject.managementportal.models.Service;
import eu.aeriosproject.managementportal.models.ServiceComponent;
import eu.aeriosproject.managementportal.models.serviceform.NewDeployment;
import eu.aeriosproject.managementportal.models.tosca.Tosca;
import eu.aeriosproject.managementportal.services.DeploymentService;
import eu.aeriosproject.managementportal.services.ToscaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/portal-backend/deployments")
public class Deployment {
    Logger logger = LoggerFactory.getLogger(Deployment.class);
    @Autowired
    private DeploymentService deploymentService;
    @Autowired
    private ToscaService toscaService;

    @PostMapping(value = {"", "/"})
    public ResponseEntity<String> orchestrate(@RequestBody NewDeployment newDeployment) {
        String serviceId = UUID.randomUUID().toString();
        serviceId = serviceId.split("-")[4];
        logger.info("Orchestrating Service with ID: {}", serviceId);

        logger.debug("Creating the TOSCA YAML...");
        Tosca tosca = toscaService.buildTosca(newDeployment);

//        DumperOptions options = new DumperOptions();
        Yaml yaml = new Yaml(new Constructor(Tosca.class, new LoaderOptions()));
        String yamlString = yaml.dumpAsMap(tosca);
        // FIXME use snakeYaml methods
        yamlString = yamlString.replaceAll("standard", "Standard");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.NetworkRequirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.HostRequirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.HostProperties", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.Requirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.EnergyProperties", "");
//        yamlString = yamlString.replaceAll("null", "{}");

        logger.debug("TOSCA YAML created");
        logger.debug("\n"+yamlString);

//        logger.debug("Sending TOSCA to HLO FE...");
        logger.debug("Initiating the orchestration process...");
        deploymentService.initOrchestration(yamlString, serviceId);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/yaml"))
                .body(yamlString);
    }

    @PostMapping(value = "/yaml")
    public ResponseEntity<String> onlyCreateAndReturnTosca(@RequestBody NewDeployment newDeployment) {
        logger.info("TOSCA generation without requesting orchestration");
        String serviceId = UUID.randomUUID().toString();
        serviceId = serviceId.split("-")[4];
        logger.info("Simulated Service ID: {}", serviceId);
        logger.debug("Creating the TOSCA YAML...");
        Tosca tosca = toscaService.buildTosca(newDeployment);

//        DumperOptions options = new DumperOptions();
        Yaml yaml = new Yaml(new Constructor(Tosca.class, new LoaderOptions()));
        String yamlString = yaml.dumpAsMap(tosca);
        // FIXME use snakeYaml methods
        yamlString = yamlString.replaceAll("standard", "Standard");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.NetworkRequirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.HostRequirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.HostProperties", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.Requirement", "");
        yamlString = yamlString.replaceAll(" !!eu.aeriosproject.managementportal.models.tosca.EnergyProperties", "");
//        yamlString = yamlString.replaceAll("null", "{}");

        logger.debug("TOSCA YAML created");
        logger.debug("\n"+yamlString);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("application/yaml"))
                .body(yamlString);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, value = {"/services/{id}", "/services/{id}/"})
    public ResponseEntity<Void> reOrchestrateFinishedService(@PathVariable String id) {
        deploymentService.reOrchestrate(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/services", "/services/"})
    public List<Service> listServices() {
        return deploymentService.listServices();
    }

    @GetMapping(value = {"/services/{id}/components", "/services/{id}/components/"})
    public List<ServiceComponent> getServiceComponents(@PathVariable String id) {
        return deploymentService.listServiceComponentsWithPorts(id);
    }

    @GetMapping(value = "/services/{id}/components/{componentId}/ports")
    public List<NetworkPort> getServiceComponentNetworkPorts(@PathVariable String id, @PathVariable String componentId){
        return deploymentService.getNetworkPortDetailsFromComponent(componentId);
    }

    @GetMapping(value = "/services/{id}")
    public Service getService(@PathVariable String id) {
        return deploymentService.getServiceById(id);
    }

    @DeleteMapping(value = "/services/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
       deploymentService.deleteServiceById(id);
       return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "/services/{id}/clear")
    public ResponseEntity<Void> clearService(@PathVariable String id) {
        deploymentService.clearServiceById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = {"/hlo-ai-models", "/hlo-ai-models/"})
    public List<String> listHloAiModels() {
        // TODO retrieve dynamically from Context Broker
        return Arrays.asList("Reinforcement learning", "Multi-parameter optimization");
    }
}
