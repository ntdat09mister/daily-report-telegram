package danny.nms.dailyreporttelegram.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "id_chat")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chatId;
    // 1 is userChat, 0 us groupChat
    private Boolean type;
    private String name;
}
