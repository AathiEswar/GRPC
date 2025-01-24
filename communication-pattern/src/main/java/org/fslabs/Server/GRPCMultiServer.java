package org.fslabs.Server;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class GRPCMultiServer {
    private static final Logger logger = LoggerFactory.getLogger(GRPCMultiServer.class);

    private final Server server ;

    public GRPCMultiServer(Server server) {
        this.server = server;
    }

    public static GRPCMultiServer create(BindableService... services){
        return create(6969 , services);
    }

    public static GRPCMultiServer create(int port, BindableService... service){
        // server builder with port initiated
        ServerBuilder<?> serverBuilder = ServerBuilder
                .forPort(port);

        // add multiple services through stream
        Arrays.asList(service).forEach(serverBuilder::addService);

        // create new instance and return it
        return new GRPCMultiServer(serverBuilder.build());

    }

    public GRPCMultiServer start(){
        // gets all services
        List<String> services = server.getServices()
                // converts to stream to enable stream java api for functional programming
                .stream()
                // maps all the services of type ssdefinition and get their descriptor
                .map(ServerServiceDefinition::getServiceDescriptor)
                // get all the names of services from their descriptor
                .map(ServiceDescriptor::getName)
                .toList();

        try{
            server.start();
            logger.info("port : {} , services : {}" , server.getPort() , services);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void await(){
        try{
            // after start , will wait and keeps running
            server.awaitTermination();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void stop(){
        try {
            // stop the server
            server.shutdownNow();
            logger.info("server stopper");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
