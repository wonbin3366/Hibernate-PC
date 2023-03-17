package shop.mtcoding.hiberpc.model.board;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;
import shop.mtcoding.hiberpc.model.user.User;
import shop.mtcoding.hiberpc.model.user.UserRepository;

@Import({ BoardRepository.class, UserRepository.class })
@DataJpaTest
public class BoardRepositoryTest extends MyDummyEntity {

    @Autowired // 테스트는 무조건 autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE board_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2
        Board board = newBoard("제목1", userPS);

        // when
        Board boardPS = boardRepository.save(board);
        System.out.println("테스트 : " + boardPS);

        // then
        Assertions.assertThat(boardPS.getId()).isEqualTo(1);
        Assertions.assertThat(boardPS.getUser().getId()).isEqualTo(1);
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

    @Test
    public void findByid_test() {
        // given 1 - DB에 영속화
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2
        int id = 1;

        // when
        userPS = userRepository.findById(id);

        // then
        Assertions.assertThat(userPS.getUsername()).isEqualTo("ssar");

    }

    @Test
    public void findAll_test() {
        // given
        List<User> userList = Arrays.asList(newUser("ssar"), newUser("cos"));
        userList.stream().forEach((user) -> {
            userRepository.save(user);
        });

        // when
        List<User> userListPS = userRepository.findAll();
        // System.out.println("테스트 :" + userListPS);

        // then
        Assertions.assertThat(userListPS.size()).isEqualTo(2);

    }

}
