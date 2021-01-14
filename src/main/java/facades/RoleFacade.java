package facades;

import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;


public class RoleFacade {

    private static EntityManagerFactory emf;
    private static RoleFacade instance;

    private RoleFacade() {
    }

    public static RoleFacade getRoleFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RoleFacade();
        }
        return instance;
    }
 
    public List<Role> getAllRoles() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Role> allRoles = em.createQuery("SELECT u.roleName from Role u", Role.class)
            .getResultList();
            return allRoles;
        } finally {
            em.close();
        }

    }
}