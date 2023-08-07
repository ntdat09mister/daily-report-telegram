package danny.nms.dailyreporttelegram.repository;

import danny.nms.dailyreporttelegram.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    @Query(value = ":query", nativeQuery = true)
//    List<Message> findByCustomQuery(@Param("query") String query);
}
