package org.fslabs.Handlers;

import com.fslabs.communications.flow.OutputResponse;
import com.fslabs.communications.flow.RequestSize;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

// implement the request
public class FlowControlRequestHandlers implements StreamObserver<RequestSize> {

    private static final Logger logger = LoggerFactory.getLogger(FlowControlRequestHandlers.class);

    private static Integer emittedValue;
    // initialize the response
    private final StreamObserver<OutputResponse> responseStreamObserver;

    public FlowControlRequestHandlers(StreamObserver<OutputResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
        emittedValue = 0;
    }


    @Override
    public void onNext(RequestSize requestSize) {
        // send the stream of response based on the request size of the client
        IntStream.rangeClosed((emittedValue + 1), 100)
                .limit(requestSize.getSize())
                .forEach(value ->
                        {
                            logger.info("emitted value : {}", value);
                            responseStreamObserver.onNext(outputResponseBuilder(value));
                        }
                );

        // update the total emissioned values till now
        emittedValue += requestSize.getSize();

        // if the limit is reached then stop
        if (emittedValue >= 100) {
            logger.info("Emission of Values is completed");
            this.responseStreamObserver.onCompleted();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        this.responseStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        logger.info("Server completed Coz Client completed");
        this.responseStreamObserver.onCompleted();
    }

    public OutputResponse outputResponseBuilder(int value){
        return OutputResponse.newBuilder().setValue(value).build();
    }
}
