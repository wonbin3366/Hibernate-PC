package shop.mtcoding.hiberpc.model.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    // 업데이트는 영속화된거를 집어넣어야함?
    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else { // 더티체킹 할거여서 쓸일없음!
            User userPS = em.find(User.class, user.getId());
            if (userPS != null) {
                em.merge(user);
            } else {
                System.out.println("잘못된 머지를 하셨어요!");
            }
        }

        return user;
    }

    public void delete(User user) {
        em.remove(user);
    }

}
