package org.fslabs;

import io.grpc.ServerBuilder;
import org.fslabs.Services.BankServiceStream;

public class GRPCServerSIngle {
    public static void main(String[] args) throws Exception {
        // create instance of the server
        var server = ServerBuilder
                // port number
                .forPort(6969)
                // add the service the needs to be communicated
                .addService(new BankServiceStream())
                .build();

        server.start();

        server.awaitTermination();
    }
}
