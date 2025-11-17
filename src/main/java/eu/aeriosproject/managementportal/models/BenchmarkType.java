package eu.aeriosproject.managementportal.models;

public enum BenchmarkType {
    CPU("CPU"), NETWORK("Network");

    private final String type;

    BenchmarkType(String type) {
        this.type = type;
    }

    public String getBenchmarkType() {
        return this.type;
    }

    public static BenchmarkType fromString(String benchmarkType) {
        if (benchmarkType == null) {
            throw new IllegalArgumentException("Invalid Benchmark type: " + benchmarkType);
        }

        for (BenchmarkType type : BenchmarkType.values()) {
            if (type.type.equalsIgnoreCase(benchmarkType)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid Benchmark type: " + benchmarkType);
    }
}
