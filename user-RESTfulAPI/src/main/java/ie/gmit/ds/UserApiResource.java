package ie.gmit.ds;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;
import java.util.HashMap;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserAccountApiResource {

    private HashMap<Integer, UserAccount> userAccounts = new HashMap<Integer, UserAccount>();

    public UserAccountApiResource(){
        UserAccount testUser1 = new UserAccount(01, "qwe", "qwe@qwe.com", "qwe");
        UserAccount testUser2 = new UserAccount(02, "asd", "asd@asd.com", "asd");
        userAccounts.put(testUser1.userID, testUser1);
        userAccounts.put(testUser2.userID, testUser2);
    }

    @GET
    public Collection<UserAccount> getUsers() {
        return userAccounts.values();
    }

    @GET
    @Path("{userID}")
    public UserAccount getUserById(@PathParam("userID") int userID) {
        return userAccounts.get(userID);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserAccount(UserAccount acc){

        UserClient client  = UserClient.getInstance();
        HashResult result = client.sendHashRequest(acc.getUserID(), acc.getPassword());
        System.out.println(result.getHashedPw());
        // if(userAccounts.get(acc.userID) == null){
        userAccounts.put(acc.userID, acc);
        // }


        return Response.status(Status.CREATED).type(MediaType.TEXT_PLAIN).entity("UserAccount Created for "+acc.getUserName()+".").build();

    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUserAccount(UserAccount acc){


        // if(userAccounts.get(acc.userID) != null){
        userAccounts.remove(acc.getUserID());
        // }


        return Response.status(Status.ACCEPTED).type(MediaType.TEXT_PLAIN).entity("User "+acc.getUserID()+" Removed").build();

    }

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserAccount(UserAccount acc){

        String err;

        if(userAccounts.containsKey(acc.userID)){
            err = "Updated User Details";
        }
        else{
            err = "Unable to complete update";
        }


        return Response.status(Status.ACCEPTED).type(MediaType.TEXT_PLAIN).entity(err).build();

    }
}
