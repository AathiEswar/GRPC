package org.fslabs.Client;

import com.fslabs.communications.unary.AccountBalance;
import com.fslabs.communications.unary.BalanceCheckRequest;
import com.fslabs.communications.unary.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.fslabs.BankServiceStream;
import org.fslabs.Server.GRPCMultiServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class GRPCClient {
    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);
    public static void main(String[] args) throws InterruptedException {
        // Channel for communication with server from client
        ManagedChannel channel = ManagedChannelBuilder
                // info of the server
                .forAddress("localhost", 6969)
                // SSL passthrough
                .usePlaintext()
                .build();

        // Client side object
        BankServiceGrpc.BankServiceStub stub =  BankServiceGrpc.newStub(channel);

        // Synchronous , waits for the response , blocks the flow , only supports unary and server streaming
        // BankServiceGrpc.BankServiceStub stub =  BankServiceGrpc.newBlockingStub(channel);

        // Java 5 feature , only supports unary and server streaming
        // BankServiceGrpc.BankServiceStub stub =  BankServiceGrpc.newFutureStub(channel);

        // invoke from client to server through channel from stub
        stub.getAccountBalance(BalanceCheckRequest.newBuilder().setAccountNumber(9).build(), new StreamObserver<AccountBalance>() {
            @Override
            public void onNext(AccountBalance accountBalance) {
                logger.info("{}" , accountBalance);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                logger.info("server stream observer emitting completed");
            }
        });

        // stub invokes main thread and does not wait for response
        // some other thread will receive the data

        Thread.sleep(Duration.ofSeconds(1));


    }
}
