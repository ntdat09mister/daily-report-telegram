package danny.nms.dailyreporttelegram.repository;

import danny.nms.dailyreporttelegram.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
