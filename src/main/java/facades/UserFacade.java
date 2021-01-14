package facades;

import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }
    
    
    public User addUser(String name, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, name);
            if (user != null) {
                throw new AuthenticationException("Username already exist, try different one");
            } else {
                user = new User(name, password);
                user.addRole(em.find(Role.class, "user"));
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
        return user;
    }
    
    
     public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            // Query query = em.createQuery("SELECT u from users u". User.class);

            List<User> allUsers = em.createQuery("SELECT u.user_name from users u", User.class)
                    .getResultList();
            return allUsers;
        } finally {
            em.close();
        }

    }
    

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

}
