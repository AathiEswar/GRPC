package org.fslabs.validator;

import com.fslabs.communications.unary.ClientValidationStatus;
import com.fslabs.communications.unary.ErrorMessage;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;

import java.util.Optional;

// Validator Utility
public class RequestValidator {

    private static final Metadata.Key<ErrorMessage> KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

    public static Optional<StatusRuntimeException> validateAccount(int accountNumber) {
        if (accountNumber > 0 && accountNumber < 11) {
            // avoids object creation for success
            return Optional.empty();
        }
        Metadata metadata = buildMetaData(ClientValidationStatus.INVALID_ACCOUNT);
        return Optional.of(
                Status.INVALID_ARGUMENT
                        .withDescription("")
                        .asRuntimeException(metadata)
        );
    }

    public static Optional<StatusRuntimeException> isAmountDivisibleBy10(int amount) {
        if (amount > 0 && amount % 10 == 0) {
            return Optional.empty();
        }

        Metadata metadata = buildMetaData(ClientValidationStatus.INVALID_ARGUMENT);
        return Optional.of(
                Status.INVALID_ARGUMENT
                        .withDescription("Amount should be in the denomination of 10")
                        .asRuntimeException(metadata)
        );
    }

    public static Optional<StatusRuntimeException> hasBalance(int amount, int balance) {
        if (amount <= balance) {
            return Optional.empty();
        }
        Metadata metadata = buildMetaData(ClientValidationStatus.INSUFFICIENT_BALANCE);
        return Optional.of(
                Status.INVALID_ARGUMENT
                        .withDescription("Insufficient Balance")
                        .asRuntimeException(metadata)
        );
    }

    private static Metadata buildMetaData(ClientValidationStatus code){
        Metadata metadata = new Metadata();
        ErrorMessage errorMessage = ErrorMessage
                .newBuilder()
                .setValidationCode(code)
                .build();
        metadata.put(KEY , errorMessage);
        metadata.put(Metadata.Key.of("trailer" , Metadata.ASCII_STRING_MARSHALLER ) , code.toString());
        return metadata;
    }
}
