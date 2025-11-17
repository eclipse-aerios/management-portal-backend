package eu.aeriosproject.managementportal.controllers;

import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParameters;
import eu.aeriosproject.managementportal.models.SelfOptimizationAnomalyParametersType;
import eu.aeriosproject.managementportal.services.SelfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/portal-backend/self")
public class Self {
    Logger logger = LoggerFactory.getLogger(Self.class);
    @Autowired
    private SelfService selfService;

    @GetMapping("/{ieId}/optimization/anomaly/parameters/{type}")
    public SelfOptimizationAnomalyParameters GetSelfOptimizationAnomalyParameters(@PathVariable String ieId, @PathVariable SelfOptimizationAnomalyParametersType type){
        return selfService.GetSelfOptimizationAnomalyParameters(ieId, type);
    }

    @PutMapping("/{ieId}/optimization/anomaly/parameters/{type}")
    public ResponseEntity<Void> UpdateSelfOptimizationAnomalyParameters(
            @PathVariable String ieId,
            @PathVariable SelfOptimizationAnomalyParametersType type,
            @RequestBody SelfOptimizationAnomalyParameters parameters){
        selfService.UpdateSelfOptimizationAnomalyParameters(ieId, type, parameters);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
