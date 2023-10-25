package org.nasa.rover.application.Request;

public class Request implements IRequest{

    private final String typedRequest;
    public Request(String typedRequest) {
        this.typedRequest = typedRequest;
    }

    @Override
    public void create() {

    }
}
