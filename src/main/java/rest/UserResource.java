package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.User;
import errorhandling.API_Exception;
import facades.UserFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;


@Path("info")
public class UserResource {
    
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(String jsonString) throws AuthenticationException, API_Exception {
        String username;
        String password;
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        User user = USER_FACADE.addUser(username, password);
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", username);
        responseJson.addProperty("msg", "Welcome on board!");

        return Response.ok(new Gson().toJson(responseJson)).build();

    }

    //Just to verify if the database is setup
    
    
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getAllUsers() {
        List<User> allUsers = USER_FACADE.getAllUsers();
        return allUsers;
    }
    
    
    
   
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("amount")
    public String getUserAmount() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
}