package org.fslabs.Handlers;

import com.fslabs.communications.unary.AccountBalance;
import com.fslabs.communications.unary.TransferDetailsRequest;
import com.fslabs.communications.unary.TransferStatus;
import com.fslabs.communications.unary.TransferStatusResponse;
import io.grpc.stub.StreamObserver;
import org.fslabs.Repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// implement request interface
public class TransferRequestHandlers implements StreamObserver<TransferDetailsRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DepositRequestHandlers.class);

    // initialize response
    private final StreamObserver<TransferStatusResponse> requestStreamObserver;

    public TransferRequestHandlers(StreamObserver<TransferStatusResponse> requestStreamObserver) {
        this.requestStreamObserver = requestStreamObserver;
    }

    @Override
    public void onNext(TransferDetailsRequest transferDetailsRequest) {
        // build the request and send in next

        TransferStatus status = this.transferStatus(transferDetailsRequest);

        // build the response
        TransferStatusResponse response = TransferStatusResponse.newBuilder()
                // build the AccountBalance from the transfer request of from_account
                .setAccountFromBalance(this.toAccountBalance(transferDetailsRequest.getAccountFromTransfer()))
                // build the Account Balance from the transfer request of to_account
                .setAccountToBalance(this.toAccountBalance(transferDetailsRequest.getAccountToTransfer()))
                // build the status
                .setStatus(status)
                .build();

        this.requestStreamObserver.onNext(response);
    }

    @Override
    public void onError(Throwable throwable) {
        this.requestStreamObserver.onError(throwable);
    }

    @Override
    public void onCompleted() {
        this.requestStreamObserver.onCompleted();
    }

    public TransferStatus transferStatus(TransferDetailsRequest request) {
        // get the details from the request
        int accountFrom = request.getAccountFromTransfer();
        int accountTo = request.getAccountToTransfer();
        int amountToTransfer = request.getAmountToTransfer();
        logger.info("from : {} , to : {} , amount : {}", accountFrom, accountTo, amountToTransfer);
        // set status
        TransferStatus status = TransferStatus.REJECTED;

        // check if its not the same account and if there is enough balance to take money from
        if ((accountTo != accountFrom) && (amountToTransfer <= AccountRepository.getBalance(accountFrom))) {
            AccountRepository.deductAmount(amountToTransfer, accountFrom);
            AccountRepository.addAmount(amountToTransfer, accountTo);
            status = TransferStatus.COMPLETED;
            logger.info("Transfering Amount from {} to {}", accountFrom, accountTo);
        }

        // return the status
        return status;
    }

    public AccountBalance toAccountBalance(int accountNumber) {
        return AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(AccountRepository.getBalance(accountNumber))
                .build();
    }
}
