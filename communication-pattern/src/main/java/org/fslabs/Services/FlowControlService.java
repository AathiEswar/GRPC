package org.fslabs.Services;

import com.fslabs.communications.flow.FlowControlServiceGrpc;
import com.fslabs.communications.flow.FlowControlServiceOuterClass;
import com.fslabs.communications.flow.OutputResponse;
import com.fslabs.communications.flow.RequestSize;
import io.grpc.stub.StreamObserver;
import org.fslabs.Handlers.FlowControlRequestHandlers;

public class FlowControlService extends FlowControlServiceGrpc.FlowControlServiceImplBase {
    @Override
    public StreamObserver<RequestSize> getValue(StreamObserver<OutputResponse> responseObserver) {
        return new FlowControlRequestHandlers(responseObserver);
    }
}
