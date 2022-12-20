package com.afs.example;

public class Test {
    public static void main(String[] args) {
        PackMessage packMessage = new PackMessage();
        ISO8583Client client = new ISO8583Client(packMessage.pack());
    }
}
