package com.sayem;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.*;


public class Main {


    // BrowserMob Proxy API
    private static final String PROXY_API_HOST = "localhost";
    private static final String PROXY_API_PORT = "8080";


    // Temporary proxy for browser you create via BrowserMob Proxy.
    // PROXY_HOST must be equal to PROXY_API_HOST
    private static final String PROXY_HOST = PROXY_API_HOST;
    private static final String PROXY_PORT = "9090";


    // Network configuration
    private static final String DOWNSTREAM_KBPS = "1024";
    private static final String UPSTREAM_KBPS = "512";
    private static final String LATENCY_MS = "100";


    // HAR Storage
    private static final String HARSTORAGE_HOST = "localhost";
    private static final String HARSTORAGE_PORT = "5000";


    public static void main(String[] args) {


        // BrowserMob Proxy constructor
        BrowsermobProxy bmp = new BrowsermobProxy(PROXY_API_HOST, PROXY_API_PORT);


        // Temporary proxy initialization
        bmp.init(PROXY_PORT);


        // Change browser settings
        Proxy proxy = new Proxy();


        String PROXY = PROXY_HOST + ":" + PROXY_PORT;


        proxy.setHttpProxy(PROXY);
        proxy.setSslProxy(PROXY);


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);


        WebDriver driver = new FirefoxDriver(capabilities);


        // Network emulation
        bmp.limitNetwork(DOWNSTREAM_KBPS, UPSTREAM_KBPS, LATENCY_MS);


        // Create new HAR container
        bmp.createHar("Home_Page");


        // Navigate to target webpage
        try {
            driver.navigate().to("http://www.totsy.com");


            Thread.sleep(2000);


            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Read data from container
        String har = bmp.fetchHar();


        // Send results to HAR Storage
        try {
            HarStorage hs = new HarStorage(HARSTORAGE_HOST, HARSTORAGE_PORT);


            String response = hs.save(har);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Close the browser
        driver.quit();


        // Terminate temporary proxy
        bmp.terminate();
    }
}
