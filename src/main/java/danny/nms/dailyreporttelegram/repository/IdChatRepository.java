package danny.nms.dailyreporttelegram.repository;

import danny.nms.dailyreporttelegram.domain.entity.IdChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdChatRepository extends JpaRepository<IdChat, Long> {
    IdChat findByChatId(String chatId);
}
