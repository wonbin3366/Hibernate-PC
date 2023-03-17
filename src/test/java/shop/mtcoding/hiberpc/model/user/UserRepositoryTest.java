package shop.mtcoding.hiberpc.model.user;

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

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");
        // when
        User userPS = userRepository.save(user);
        // then
        Assertions.assertThat(userPS.getId()).isEqualTo(1);
    }
}
