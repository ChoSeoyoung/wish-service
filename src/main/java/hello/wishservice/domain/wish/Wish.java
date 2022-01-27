package hello.wishservice.domain.wish;

import lombok.Data;

@Data
public class Wish {
    private Long id;
    private String wishName;
    private String period;
    private Integer cost;

    public Wish() {
    }

    public Wish(String wishName, String period, Integer cost) {
        this.wishName = wishName;
        this.period = period;
        this.cost = cost;
    }
}
