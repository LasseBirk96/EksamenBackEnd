package facades;

import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;


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

 
    
       public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
   
        try {
            
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();
            user.verifyPassword(password);
            return user;
            } finally {
                em.close();
            }
          
        }
    
    
    public User findUser(Long id) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id ", User.class);
            query.setParameter("id", id);
            User user = query.getSingleResult();
            
            return user;
        } 
        finally {
            em.close();
        }
    }
     public User updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
            
            return user;
        }
        finally {
            em.close();
        }
    }

    public User addUser(String name, String password, String email) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
                user = new User(name, password, email);
                user.addRole(em.find(Role.class, "user"));
                em.getTransaction().begin();
                em.persist(user);
                em.getTransaction().commit();
            
        } finally {
            em.close();
        }
        return user;
    }
    
   
    
    
    public User deleteUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id ", User.class);
            query.setParameter("id", userId);
            User p = query.getSingleResult();
            em.remove(p);
            em.getTransaction().commit();
            return p; 
        }
        finally{
            em.close();
        }
        
    }
     
    public List<User> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            List<User> allUsers = em.createQuery("SELECT u.userName from User u", User.class)
            .getResultList();
            return allUsers;
        } finally {
            em.close();
        }

    }
    
    
    public String getUserAmount() {
       EntityManager em = emf.createEntityManager();;
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
    
        
    }
    
    
    
    
   
}
    
}