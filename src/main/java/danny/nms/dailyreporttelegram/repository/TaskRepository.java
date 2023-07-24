package danny.nms.dailyreporttelegram.repository;

import danny.nms.dailyreporttelegram.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select * from task t where t.status = 0", nativeQuery = true)
    List<Task> findAllWithCondition();
}
