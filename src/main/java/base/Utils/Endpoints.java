package base.Utils;

public enum Endpoints {

    USERS("api/users"),
    RESOURSES("api/unknown"),
    REGISTER("api/register");

    private final String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public static String getEndpoint(Endpoints endpoint) {
        return endpoint.endpoint;
    }
}
