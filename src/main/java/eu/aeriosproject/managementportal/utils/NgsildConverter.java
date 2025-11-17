package eu.aeriosproject.managementportal.utils;

// TODO improve and use
public class NgsildConverter {
    public static final String NGSI_LD_PREFIX = "urn:ngsi-ld:";
    public static final String DOMAIN = NGSI_LD_PREFIX.concat("Domain:");
    public static final String IE = NGSI_LD_PREFIX.concat("InfrastructureElement:");
    public static final String SERVICE = NGSI_LD_PREFIX.concat("Service:");
    public static final String BENCHMARK = NGSI_LD_PREFIX.concat("Benchmark:");
    public static final String NOTIFICATION = NGSI_LD_PREFIX.concat("Alert:");

    public static String trimId(String id) {
        return id.replaceAll("urn:ngsi-ld:[A-Za-z]+:", "");
    }

    public static String trimServiceComponentId(String id) {
        return id.replaceAll("urn:ngsi-ld:Service:[A-Za-z0-9]+:Component:", "");
    }

    public static String trimSubEntityId(String id) {
        return id.replaceAll("urn:ngsi-ld:[A-Za-z]+:[A-Za-z0-9]+:[A-Za-z]+:", "");
    }

    public static String getDomainId(String id) {
        return (id.contains(DOMAIN)) ? id : DOMAIN.concat(id);
    }

    public static String getInfrastructureElementId(String id) {
        return (id.contains(IE)) ? id : IE.concat(id);
    }

    public static String getServiceId(String id) {
        return (id.contains(SERVICE)) ? id : SERVICE.concat(id);
    }

    public static String getBenchmarkId(String id) {
        return (id.contains(BENCHMARK)) ? id : BENCHMARK.concat(id);
    }

    public static String convert(String id, String type) {
        return switch (type) {
            case "SERVICE" -> SERVICE.concat(id);
            case "DOMAIN" -> DOMAIN.concat(id);
            case "IE" -> IE.concat(id);
            case "BENCHMARK" -> BENCHMARK.concat(id);
            case "NOTIFICATION" -> NOTIFICATION.concat(id);
            default -> id;
        };
    }

    public static boolean isNgsiLdId (String id) {
        return id.startsWith("urn:ngsi-ld:[A-Za-z]+:");
    }

    // TODO check if contains NGSI-LD prefix and entity type
}
