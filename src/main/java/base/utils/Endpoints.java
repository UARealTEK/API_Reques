package base.utils;

public enum Endpoints {

    USERS("api/users");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public static String getEndpoint(Endpoints endpoint) {
        return endpoint.endpoint;
    }
}
