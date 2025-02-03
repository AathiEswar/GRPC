package org.fslabs;

import org.fslabs.server.GRPCMultiServiceServer;
import org.fslabs.service.GuessGameService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GRPCMultiServiceServer
                .create(new GuessGameService())
                .start()
                .await();
    }
}