package org.fslabs.Services;

import com.fslabs.communications.unary.TransferDetailsRequest;
import com.fslabs.communications.unary.TransferServiceGrpc;
import com.fslabs.communications.unary.TransferStatusResponse;
import io.grpc.stub.StreamObserver;
import org.fslabs.Handlers.TransferRequestHandlers;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {
    @Override
    public StreamObserver<TransferDetailsRequest> transferAmountInvoke(StreamObserver<TransferStatusResponse> responseObserver) {
        return new TransferRequestHandlers(responseObserver);
    }
}
