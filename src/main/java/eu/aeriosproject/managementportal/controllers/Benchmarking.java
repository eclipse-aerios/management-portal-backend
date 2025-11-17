package eu.aeriosproject.managementportal.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.aeriosproject.managementportal.models.BenchmarkType;
import eu.aeriosproject.managementportal.services.BenchmarkingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/portal-backend/benchmarking")
public class Benchmarking {
    Logger logger = LoggerFactory.getLogger(Benchmarking.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private BenchmarkingService benchmarkingService;

    @PostMapping("/{benchmarkType}/{ieId}")
    public ResponseEntity<JsonNode> runCpuTest(@PathVariable String benchmarkType, @PathVariable String ieId) {
        BenchmarkType benchmarkTypeEnum;
        try {
            benchmarkTypeEnum = BenchmarkType.fromString(benchmarkType);
        } catch (IllegalArgumentException e) {
            JsonNode error = MAPPER.createObjectNode().put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(benchmarkingService.runBenchmarking(benchmarkTypeEnum, ieId));
    }


    @GetMapping("/{benchmarkId}")
    public ResponseEntity<JsonNode> getCpuTest(@PathVariable String benchmarkId) {
        return ResponseEntity.ok(benchmarkingService.getBenchmarking(benchmarkId));
    }

    @GetMapping("/list/{ieId}")
    public ResponseEntity<JsonNode> getBenchmarksByIE(
            @PathVariable String ieId,
            @RequestParam(required = false) String benchmarkType
    ) {
        BenchmarkType benchmarkTypeEnum;
        if (ieId.isEmpty()) {
            return ResponseEntity.ok(benchmarkingService.getBenchmarksWithoutInfrastructureElement());
        }
        if (benchmarkType != null) {
            try {
                benchmarkTypeEnum = BenchmarkType.fromString(benchmarkType);
            } catch (IllegalArgumentException e) {
                JsonNode error = MAPPER.createObjectNode().put("error", e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
            }
            return ResponseEntity.ok(benchmarkingService.getBenchmarksByIE(ieId, benchmarkTypeEnum));
        }
        return ResponseEntity.ok(benchmarkingService.getBenchmarksByIE(ieId));
    }

    @PostMapping("/cpu/fromGeekbenchUrl")
    public ResponseEntity<JsonNode> addFromGeekbenchUrl(@RequestBody Map<String, String> requestBody) {
        String url = requestBody.get("url");
        JsonNode response = benchmarkingService.addFromGeekbenchUrl(url);
        if (response == null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode error =  mapper.createObjectNode()
                    .put("error", "The benchmark with the specified URL already exists.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
