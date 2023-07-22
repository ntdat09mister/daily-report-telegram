package danny.nms.dailyreporttelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DailyReportTelegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyReportTelegramApplication.class, args);
	}

}
