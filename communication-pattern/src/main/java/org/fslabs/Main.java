package org.fslabs;

import org.fslabs.Server.GRPCMultiServer;

public class Main {
    public static void main(String[] args) {
        GRPCMultiServer.create(new BankServiceStream())
                .start()
                .await();


    }
}