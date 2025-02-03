package org.fslabs.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

public class GRPCMultiServiceServer {
    private final Logger logger = LoggerFactory.getLogger(GRPCMultiServiceServer.class);

    // initialize Server with port and services
    // create methods - create , start , await , stop

    private final Server server;

    public GRPCMultiServiceServer(Server server) {
        this.server = server;
    }

    public static GRPCMultiServiceServer create(BindableService... services) {
        return create(6969, services);
    }

    public static GRPCMultiServiceServer create(int port, BindableService... services) {
        // build the server with port
        ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);

        // add the services
        Arrays.asList(services).forEach(serverBuilder::addService);

        // return the grpc server
        return new GRPCMultiServiceServer(serverBuilder.build());
    }

    public GRPCMultiServiceServer start() throws IOException {
        try {
            server.start();
            logger.info("GRPC server started , port : {}" , server.getPort());
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void await() {
        try {
            server.awaitTermination();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            logger.info("GRPC server is stopped");
            server.shutdownNow();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
