package ratelimiter.models;

public class Request {

    private final User user;
    private final ApiEndpoints apiEndpoint;

    public Request(User user,  ApiEndpoints apiEndpoint) {
        this.user = user;
        this.apiEndpoint = apiEndpoint;
    }

    //Getters
    public User getUsername() {
        return user;
    }
    public ApiEndpoints getApiEndpoint() {
        return apiEndpoint;
    }

}
