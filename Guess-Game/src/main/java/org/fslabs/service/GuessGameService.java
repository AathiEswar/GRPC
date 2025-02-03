package org.fslabs.service;

import com.fslabs.guess_game.GuessGame;
import com.fslabs.guess_game.GuessNumberGrpc;
import com.fslabs.guess_game.GuessRequest;
import com.fslabs.guess_game.GuessResponse;
import io.grpc.stub.StreamObserver;
import org.fslabs.handler.GuessGameResponse;

public class GuessGameService extends GuessNumberGrpc.GuessNumberImplBase {
    @Override
    public StreamObserver<GuessRequest> makeGuess(StreamObserver<GuessResponse> responseObserver) {
        return new GuessGameResponse(responseObserver);
    }
}
