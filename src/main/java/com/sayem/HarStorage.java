package com.sayem;

public class HarStorage {
    private HttpRequest httpRequest;


    HarStorage(String host, String port) {
        httpRequest = new HttpRequest();
        httpRequest.setRequestHostname(host);
        httpRequest.setRequestPort(port);
    }


    public String save(String har) throws Exception {
        httpRequest.setRequestMethod("POST");
        httpRequest.setRequestPath("/results/upload");


        return httpRequest.send(har);
    }
}
