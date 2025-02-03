package org.fslabs.handler;

import com.fslabs.guess_game.GuessRequest;
import com.fslabs.guess_game.GuessResponse;
import com.fslabs.guess_game.Result;
import io.grpc.stub.StreamObserver;

/* ITERATION - 1
 *  - Get the request from client
 *  - Evaluate the value and respond back the status
 *  - Complete if the client finds out the number or if the client wants to complete the session
 *  - Throw back error when needed
 *
 *  ITERATION - 2
 *  - Only have 3 attempts
 * */

public class GuessGameResponse implements StreamObserver<GuessRequest> {

    private final StreamObserver<GuessResponse> responseStreamObserver;
    private final int GuessValue;
    private int attemptValue;

    public GuessGameResponse(StreamObserver<GuessResponse> responseStreamObserver) {
        this.responseStreamObserver = responseStreamObserver;
        this.GuessValue = 10;
        this.attemptValue = 3;
    }

    @Override
    public void onNext(GuessRequest guessRequest) {
        // get the value from the request
        int value = guessRequest.getGuess();

        // evaluate it
        Result result = evaluteResult(value);

        // build the response
        GuessResponse response = GuessResponse
                .newBuilder()
                .setAttempt(attemptValue)
                .setResult(result)
                .build();

        attemptValue-=1;

        // return the response with completion
        this.responseStreamObserver.onNext(response);

        if(attemptValue == 0){
            this.responseStreamObserver.onCompleted();
            return;
        }

        if(result.equals(Result.CORRECT)){
            this.responseStreamObserver.onCompleted();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        // throw error when it happen
        this.responseStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        // complete when the client wants to complete the session
        this.responseStreamObserver.onCompleted();
    }

    public Result evaluteResult(int value) {

        // evaluate the value and return the result ( TOO LOW , TOO HIGH , CORRECT , NOT IN RANGE)
        if (value < GuessValue) {
            return Result.TOO_LOW;
        }
        if (value > GuessValue) {
            return Result.TOO_HIGH;
        }
        return Result.CORRECT;
    }
}
