package danny.nms.dailyreporttelegram.payload;

import lombok.Data;

@Data
public class CustomResponse<T> {
    private int code;
    private T data;

    public CustomResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }
}