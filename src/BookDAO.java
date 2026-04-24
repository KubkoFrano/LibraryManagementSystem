import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class BookDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");

    public void save(Book book) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (book.getId() == null) {
            em.persist(book);
        } else {
            em.merge(book);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Book> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        em.close();
        return books;
    }

    public List<Book> findAvailable() {
        EntityManager em = emf.createEntityManager();
        List<Book> books = em.createQuery("SELECT b FROM Book b WHERE b.available = true", Book.class).getResultList();
        em.close();
        return books;
    }
}