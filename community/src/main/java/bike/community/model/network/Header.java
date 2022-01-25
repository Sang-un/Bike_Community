package bike.community.model.network;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class Header<T> {

    private T data;

    private LocalDateTime transaction_time;
    private String status;
    private String description;


    // OK
    public static <T> Header<T> OK() {
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status("OK")
                .description("OK desc")
                .build();
    }


    // DATA OK
    public static <T> Header<T> OK(T data) {
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status("DATA-OK")
                .description("DATA-OK desc")
                .data(data)
                .build();
    }

    // ERROR
    public static <T> Header<T> ERROR(){
        return (Header<T>) Header.builder()
                .transaction_time(LocalDateTime.now())
                .status("ERROR")
                .description("OK desc")
                .build();
    }


}
