package com.sayem;

public class BrowsermobProxy {
    private HttpRequest httpRequest;
    private String baseURL;
    private String response;


    BrowsermobProxy(String apiHostName, String apiPort) {
        httpRequest = new HttpRequest();
        httpRequest.setRequestHostname(apiHostName);
        httpRequest.setRequestPort(apiPort);
    }


    public void init(String proxyPort) {
        // Base URL for API requests
        baseURL = "/proxy/" + proxyPort;


        // Proxy initialization via REST API
        httpRequest.setRequestMethod("POST");
        httpRequest.setRequestPath("/proxy?port=" + proxyPort);


        try {
            httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void createHar(String pageId) {
        httpRequest.setRequestMethod("PUT");


        String path = baseURL;
        path += "/har";
        path += "?initialPageRef=" + pageId;
        path += "&captureHeaders=true";
        path += "&captureContent=true";


        httpRequest.setRequestPath(path);


        try {
            httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public String fetchHar() {
        httpRequest.setRequestMethod("PUT");


        String path = baseURL;

        path += "/har";


        httpRequest.setRequestPath(path);


        try {
            response = httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }


        return response;
    }


    public void limitNetwork(String bw_down, String bw_up, String latency) {
        httpRequest.setRequestMethod("PUT");


        String path = baseURL;
        path += "/limit";
        path += "?upstreamKbps=" + bw_up;
        path += "&downstreamKbps=" + bw_down;
        path += "&latency=" + latency;


        httpRequest.setRequestPath(path);


        try {
            httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void addToBlackList(String regEx) {
        httpRequest.setRequestMethod("PUT");


        String path = baseURL;


        path += "/blacklist";
        path += "?status=-200";
        path += "&regex=" + regEx;


        httpRequest.setRequestPath(path);


        try {
            httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void terminate() {
        httpRequest.setRequestMethod("DELETE");


        httpRequest.setRequestPath(baseURL);


        try {
            httpRequest.send();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
