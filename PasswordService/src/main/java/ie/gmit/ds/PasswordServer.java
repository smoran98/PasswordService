package ie.gmit.ds;

import ie.gmit.ds.PasswordServiceImpl;
import io.grpc.Server;

import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class PasswordServer {
    private Server server;
    private static final Logger logger = Logger.getLogger(PasswordServer.class.getName());


    public static void main(String[] args) throws IOException, InterruptedException {
        // Create Server
        final PasswordServer server = new PasswordServer();
        server.start();
        server.blockUntilShutdown();
    }


    private void start() throws IOException {
        // Port server should run
        int port = 50551;
        server = ServerBuilder.forPort(port)
                .addService(new PasswordServiceImpl())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("**** gRPC server down as JVM's shutting down ****");
                PasswordServer.this.stop();
                System.err.println("**** Server stops ****");
            }
        });
    }

    private void stop(){
        if(server!=null){
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}


