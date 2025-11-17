package eu.aeriosproject.managementportal.services;

import com.fasterxml.jackson.databind.JsonNode;
import eu.aeriosproject.managementportal.models.BenchmarkType;

public interface BenchmarkingService {
    public JsonNode runBenchmarking(BenchmarkType benchmarkType, String ieId);
    public JsonNode getBenchmarking(String benchmarkId);
    public JsonNode getBenchmarksByIE(String ieId);
    public JsonNode getBenchmarksByIE(String ieId, BenchmarkType benchmarkType);
    public JsonNode addFromGeekbenchUrl(String geekBenchUrl);
    public JsonNode getBenchmarksWithoutInfrastructureElement();
    // TODO
    //  deleteBenchmarksFromIE(String ieId, BenchmarkType benchmarkType)
    //  deleteBenchmark(String benchmarkId)
}
