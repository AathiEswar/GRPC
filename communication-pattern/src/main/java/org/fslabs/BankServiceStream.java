package org.fslabs;

import com.fslabs.communications.unary.AccountBalance;
import com.fslabs.communications.unary.BalanceCheckRequest;
import com.fslabs.communications.unary.BankServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.fslabs.Repository.AccountRepository;

public class BankServiceStream extends BankServiceGrpc.BankServiceImplBase  {

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        // request for method access from proto services
        int accountNumber = request.getAccountNumber();
        int bankBalance = AccountRepository.getBalance(accountNumber);
        AccountBalance accountBalance = AccountBalance.newBuilder()
                .setBalance(bankBalance)
                .setAccountNumber(accountNumber)
                .build();

        // send the value in the stream
        responseObserver.onNext(accountBalance);

        // the execution is completed and no stream of data will be sent again
        responseObserver.onCompleted();

    }
}
