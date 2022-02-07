package hello.wishservice.domain.wish;

import lombok.Data;

@Data
public class Wish {
    private Long id;
    private String title;
    private String period;
    private Integer cost;

    public Wish() {
    }

    public Wish(String title, String period, Integer cost) {
        this.title = title;
        this.period = period;
        this.cost = cost;
    }
}
