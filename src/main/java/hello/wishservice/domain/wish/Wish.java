package hello.wishservice.domain.wish;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Wish {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String period;

    @NotNull
    @Range(min=0,max=9999)
    private Integer cost;

    private Boolean open; //공개 여부

    @NotNull
    private RegionType regionType; //여행 지역

    @NotNull
    private String travelType; //여행 타입

    public Wish() {
    }

    /*
    public Wish(String title, String period, Integer cost, Boolean open, RegionType regionType) {
        this.title = title;
        this.period = period;
        this.cost = cost;
        this.open = open;
        this.regionType = regionType;
    }*/

    public Wish(String title, String period, Integer cost, Boolean open, RegionType regionType, String travelType) {
        this.title = title;
        this.period = period;
        this.cost = cost;
        this.open = open;
        this.regionType = regionType;
        this.travelType = travelType;
    }
}
