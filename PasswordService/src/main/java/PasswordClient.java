import ie.gmit.ds.PasswordServiceGrpc;
import ie.gmit.ds.Passwords;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

// Client used to connect to PasswordServer
// ***Multiple errors
public class PasswordClient {
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.passwordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.passwordServiceBlockingStub syncPasswordService;

    public PasswordClient(String host, int port) {
        channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);

        hashPassword();

    }

    public static void main(String[] args){
        PasswordClient testClient = new PasswordClient("localHost", 2104);
    }

    public void hashPassword(){
        String password = Passwords.generateRandomPassword(5);


        PasswordService.Credentials credentials = PasswordService.Credentials.newBuilder()
                .setId(123)
                .setPassword(password)
                .build();

        byte[] hashedPassword = syncPasswordService.hash(credentials).getHashedPassword().toByteArray();
        System.out.println("Password sent : "+password+" Hashed value : "+hashedPassword);
    }

    public void hashResponse(){

    }

    public void validatePassword(){

    }
}