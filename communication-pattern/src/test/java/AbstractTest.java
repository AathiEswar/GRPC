import com.fslabs.communications.unary.BankServiceGrpc;
import common.AbstractChannelTest;
import org.fslabs.BankServiceStream;
import org.fslabs.Server.GRPCMultiServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class AbstractTest extends AbstractChannelTest {

    private final GRPCMultiServer server = GRPCMultiServer.create(new BankServiceStream());

    protected BankServiceGrpc.BankServiceBlockingStub stub;

    @BeforeAll
    public void setup(){
        this.server.start();
        this.stub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop(){
        this.server.stop();
    }
}
