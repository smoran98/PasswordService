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
public class UserApiResource {

    private HashMap<Integer, User> userAccounts = new HashMap<Integer, User>();

    public UserApiResource(){
    	User testUser1 = new User(01, "qwe", "qwe@qwe.com", "qwe");
    	User testUser2 = new User(02, "asd", "asd@asd.com", "asd");
        userAccounts.put(testUser1.userID, testUser1);
        userAccounts.put(testUser2.userID, testUser2);
    }

    @GET
    public Collection<User> getUsers() {
        return userAccounts.values();
    }

    @GET
    @Path("{userID}")
    public User getUserById(@PathParam("userID") int userID) {
        return userAccounts.get(userID);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUserAccount(User acc){

        // if(userAccounts.get(acc.userID) == null){
            userAccounts.put(acc.userID, acc);
        // }
        

        return Response.status(Status.CREATED).type(MediaType.TEXT_PLAIN).entity("UserAccount Created for "+acc.getUserName()+".").build();
        
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUserAccount(User acc){

        
        // if(userAccounts.get(acc.userID) != null){
            userAccounts.remove(acc.getUserID());
        // }
        

        return Response.status(Status.ACCEPTED).type(MediaType.TEXT_PLAIN).entity("User "+acc.getUserID()+" Removed").build();
        
    }
}