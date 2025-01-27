package common;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

// Enables Having the same class instance for the entire lifecycle
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractChannelTest {

    protected ManagedChannel channel;

    @BeforeAll
    public void createChannel(){
        channel = ManagedChannelBuilder
                .forAddress("localhost" , 6969)
                .usePlaintext()
                .build();
    }

    @AfterAll
    public void stopChannel() throws InterruptedException {
        channel.shutdown().awaitTermination(5 , TimeUnit.SECONDS);
    }
}
