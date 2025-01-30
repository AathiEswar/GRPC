package org.fslabs;

import io.grpc.Server;
import org.fslabs.Server.GRPCMultiServer;
import org.fslabs.Services.FlowControlService;
import org.fslabs.Services.TransferService;

public class Main {
    public static void main(String[] args) {
         GRPCMultiServer.create(new BankServiceStream() , new TransferService() , new FlowControlService())
                .start()
                .await();
    }
}