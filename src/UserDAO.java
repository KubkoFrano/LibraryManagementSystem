import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UserDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public User findUserById(Long id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }

    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        em.close();
        return users;
    }
}