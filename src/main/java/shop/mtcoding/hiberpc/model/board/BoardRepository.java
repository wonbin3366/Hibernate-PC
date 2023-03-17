package shop.mtcoding.hiberpc.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public Board findById(int id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    // 업데이트는 영속화된거를 집어넣어야함?
    public Board save(Board board) {
        if (board.getId() == null) {
            em.persist(board);
        } else { // 더티체킹 할거여서 쓸일없음!
            Board boardPS = em.find(Board.class, board.getId());
            if (boardPS != null) {
                em.merge(board);
            } else {
                System.out.println("잘못된 머지를 하셨어요!");
            }
        }

        return board;
    }

    public void delete(Board board) {
        em.remove(board);
    }
}
