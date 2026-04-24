import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;

public class LoanDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibraryPU");

    public void processLoan(User user, Book book) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            User managedUser = em.merge(user);
            Book managedBook = em.merge(book);

            if (managedBook.isAvailable()) {
                Loan loan = new Loan();
                loan.setUser(managedUser);
                loan.setBook(managedBook);
                loan.setDueDate(Date.valueOf(LocalDate.now().plusDays(14)));

                managedBook.setAvailable(false);

                em.persist(loan);
                em.getTransaction().commit();
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}