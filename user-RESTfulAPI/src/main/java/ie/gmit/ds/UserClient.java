package ie.gmit.ds;

import com.google.protobuf.BoolValue;
import com.google.protobuf.ByteString;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import javassist.bytecode.ByteArray;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import ie.gmit.ds.PasswordServiceGrpc;

public class UserClient{

    private static UserClient instance;
    private UserClient (){
        channel = ManagedChannelBuilder
                .forAddress("localhost", 50551)
                .usePlaintext()
                .build();
        syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
        asyncPasswordService = PasswordServiceGrpc.newStub(channel);

       
    }

    public static UserClient getInstance(){
        if(instance == null){
            instance = new UserClient();
        }
        return instance;
    }

    private static final Logger logger =
            Logger.getLogger(UserClient.class.getName());
    private final ManagedChannel channel;
    private final PasswordServiceGrpc.PasswordServiceStub asyncPasswordService;
    private final PasswordServiceGrpc.PasswordServiceBlockingStub syncPasswordService;

    // User Input
    Scanner userInput = new Scanner(System.in);

    // Member variables to hold user input
    private String userPassword;
    private ByteString hashedPassword;
    private ByteString salt;
    private int userId;


    // public UserClient(String host, int port) {
    //     channel = ManagedChannelBuilder
    //             .forAddress(host, port)
    //             .usePlaintext()
    //             .build();
    //     syncPasswordService = PasswordServiceGrpc.newBlockingStub(channel);
    //     asyncPasswordService = PasswordServiceGrpc.newStub(channel);
    // }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    // public void getUserInput(){
    //     System.out.println("Enter ID:");
    //     userId = userInput.nextInt();
    //     System.out.println("Enter Password:");
    //     userPassword = userInput.next();
    // }

    public HashResult sendHashRequest(int userId, String userPassword){
        // getUserInput();

        // Build HashRequest
        System.out.println("Sending Hash Req");
        HashRequest hashRequest = HashRequest.newBuilder().setUserId(userId)
                                    .setPassword(userPassword).build();
        // HashResponse
        HashResponse hashResponse;

        // try {
            hashResponse = syncPasswordService.hash(hashRequest);
            hashedPassword = hashResponse.getHashedPassword();
            salt = hashResponse.getSalt();
        // }catch (StatusRuntimeException ex){
            // logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return new HashResult(hashedPassword.toByteArray(), salt.toByteArray());
        // }
    }

    public boolean validate(String userPassword, byte[] hashedPassword, byte[] salt){
        return syncPasswordService.validate(ValidationRequest.newBuilder().setPassword(userPassword)
                    .setHashedPassword(ByteString.copyFrom(hashedPassword) )
                    .setSalt(ByteString.copyFrom(salt)).build()).getValue();
           
    }

    public void sendValidationRequest(){
        StreamObserver<BoolValue> responseObserver = new StreamObserver<BoolValue>(){
            @Override
            public void onNext(BoolValue value) {
                if(value.getValue()){
                    System.out.println("Auth Successful");
                }
                else{
                    System.out.println("Username or Password is incorrect");
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("An Error has occurred.. "+t.getLocalizedMessage());
            }

            @Override
            public void onCompleted() {
                System.exit(0);
            }
        };
        try {
            asyncPasswordService.validate(ValidationRequest.newBuilder().setPassword(userPassword)
                    .setHashedPassword(hashedPassword)
                    .setSalt(salt).build(), responseObserver);
        } catch (StatusRuntimeException ex) {
            logger.log(Level.WARNING, "RPC failed: {0}", ex.getStatus());
            return;
        }
    }


}