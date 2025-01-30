package org.fslabs;

import com.fslabs.communications.unary.*;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import org.fslabs.Handlers.DepositRequestHandlers;
import org.fslabs.Repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class BankServiceStream extends BankServiceGrpc.BankServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(BankServiceStream.class);

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

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
        // get data from repo
        // build the response data
        // send it using observer

        List<AccountBalance> account = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(
                        e -> AccountBalance.newBuilder()
                                .setAccountNumber(e.getKey())
                                .setBalance(e.getValue())
                                .build()
                )
                .toList();

        AllAccountsResponse response = AllAccountsResponse.newBuilder().addAllAccountBalance(account).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void debitAmount(DebitRequest request, StreamObserver<AmountResponse> responseObserver) {
        int accountNumber = request.getAccountNumber();
        int requestAmount = request.getAmountToBeDebited();

        int accountBalance = AccountRepository.getBalance(accountNumber);

        if (requestAmount > accountBalance) {
            responseObserver.onCompleted();
            return;
        }

        for (int i = 0; i < (requestAmount / 10); i++) {
            AmountResponse money = AmountResponse.newBuilder().setAmount(10).build();
            responseObserver.onNext(money);
            log.info("{}", money);
            AccountRepository.deductAmount(accountNumber , 10);
            // no need to extend exception like threads
            Uninterruptibles.sleepUninterruptibly(1 , TimeUnit.SECONDS);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> depositAmountInvoke(StreamObserver<AccountBalance> responseObserver) {
        return new DepositRequestHandlers(responseObserver);
    }
}
