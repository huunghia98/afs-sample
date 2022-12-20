package com.afs.example;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloWorld implements BundleActivator {
    private ScheduledExecutorService scheduler;

    public void start(BundleContext ctx) {
        System.out.println("Hello world.");
        PackMessage isoMessage = new PackMessage();
        String message = isoMessage.pack();
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            ISO8583Client client = new ISO8583Client(message);
            System.out.println("Sent");
        }, 1000, 2000, TimeUnit.MILLISECONDS);
    }
    public void stop(BundleContext bundleContext) {
        scheduler.shutdown();
        System.out.println("Goodbye world.");
    }
}