package rest;

import entities.Role;
import facades.RoleFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import utils.EMF_Creator;

@Path("roles")
public class RoleResource {

   
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final RoleFacade Role_FACADE = RoleFacade.getRoleFacade(EMF);

    @Context
    private UriInfo context;

    
    public RoleResource() {
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Role> getAllRoles() {
        List<Role> allRoles = Role_FACADE.getAllRoles();
        return allRoles;
    }
    
    
}