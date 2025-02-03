package org.fslabs.Handlers;

import com.fslabs.communications.unary.AccountBalance;
import com.fslabs.communications.unary.DepositRequest;
import io.grpc.stub.StreamObserver;
import org.fslabs.Repository.AccountRepository;
import org.fslabs.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepositRequestHandlers implements StreamObserver<DepositRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DepositRequestHandlers.class);

    // stream observer for get info from the client
    private final StreamObserver<AccountBalance> responseObserver;

    // account number to save the instance
    private int accountNumber;

    public DepositRequestHandlers(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }

    // handle the emission of data from the client
    @Override
    public void onNext(DepositRequest depositRequest) {
        // handles two types of request ( account number and the amount to be deposited
        switch (depositRequest.getRequestCase()) {
            // if account number is sent , save it in the instance
            case ACCOUNTNUMBER -> RequestValidator.validateAccount(depositRequest.getAccountNumber())
                    .ifPresentOrElse(
                            responseObserver::onError,
                            () -> this.accountNumber = depositRequest.getAccountNumber()
                    );

            // if money is sent , add it to the balance of the account from the instance
            case MONEY -> AccountRepository.addAmount(depositRequest.getMoney().getAmount(), this.accountNumber);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        logger.info("{}", throwable.getMessage());
    }

    // when the client completed the emission
    @Override
    public void onCompleted() {
        // build the request
        AccountBalance accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();

        // send it
        this.responseObserver.onNext(accountBalance);

        // stop the emission
        this.responseObserver.onCompleted();
    }
}
