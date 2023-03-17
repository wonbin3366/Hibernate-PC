package shop.mtcoding.hiberpc.model.user;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest extends MyDummyEntity {

    @Autowired // 테스트는 무조건 autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");
        // when
        User userPS = userRepository.save(user);
        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
    }

    @Test
    public void update_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - request 데이터
        String password = "5678";
        String email = "ssar@gmail.com";

        // when
        userPS.update(password, email);
        User updateUserPS = userRepository.save(userPS);

        // then
        Assertions.assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    public void update_dutty_checking_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - request 데이터
        String password = "5678";
        String email = "ssar@gmail.com";

        // when
        userPS.update(password, email);
        em.flush();

        // then
        User updateUserPS = userRepository.findById(1);
        Assertions.assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    public void delete_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - request 데이터
        int id = 1;
        User findUserPS = userRepository.findById(id);

        // when
        userRepository.delete(findUserPS);

        // then
        User deleteUserPS = userRepository.findById(1);
        Assertions.assertThat(deleteUserPS).isNull();

    }
}
