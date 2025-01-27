import com.fslabs.communications.unary.AccountBalance;
import com.fslabs.communications.unary.BalanceCheckRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnaryTest extends AbstractTest{
    private static final Logger logger = LoggerFactory.getLogger(UnaryTest.class);

    @Test
    public void getBalanceTest(){
        BalanceCheckRequest request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(5)
                .build();

        AccountBalance balance = this.stub.getAccountBalance(request);

        logger.info("{}" , balance);

        Assertions.assertEquals(100,balance.getBalance());

    }
}
