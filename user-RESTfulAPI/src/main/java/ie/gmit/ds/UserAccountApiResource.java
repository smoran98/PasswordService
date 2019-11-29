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

    private HashMap<Integer, User> userAccounts = new HashMap<Integer, User>();

    public UserAccountApiResource(){
        User testUser1 = new User(01, "qwe", "qwe@qwe.com", "qwe", null, null);
        User testUser2 = new User(02, "asd", "asd@asd.com", "asd", null, null);
        userAccounts.put(testUser1.userID, testUser1);
        userAccounts.put(testUser2.userID, testUser2);
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginUser(User acc) {
        // Confirm if User Exists in DB - Not Found Error if false
        int providedID = acc.getUserID();
        if(userAccounts.get(providedID) == null){
            return Response.status(Status.NOT_FOUND)
                    .entity("Error! Please Try Again...").build();
        }

        String password = acc.getPassword();
        User userLogin = userAccounts.get(providedID);
        System.out.println("User "+userLogin.getUserName()+" found");
        System.out.println("Validating... ");
        boolean validLogin = new UserClient().validate(password, userLogin.getHashedPassword(), userLogin.getSalt());
        System.out.println("Validate "+validLogin);

        // Logged in
        if(validLogin){
            return Response.status(Status.OK).entity("VALID DATA").build();
        }
        // Try again
        else{
            return  Response.status(Status.BAD_REQUEST).entity("Login Error, Incorrect data, TRY AGAIN ").build();
        }
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
    public Response addUserAccount(User acc){

        UserClient client  = UserClient.getInstance();
        HashResult result = client.sendHashRequest(acc.getUserID(), acc.getPassword());
        System.out.println(result.getHashedPw()+""+result.getSalt());
        //make a new user with second constructor            
        if(userAccounts.get(acc.userID) == null){
        }
        userAccounts.put(acc.userID, acc);
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

    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUserAccount(User acc){

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
