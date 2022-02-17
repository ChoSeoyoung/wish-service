package hello.wishservice.domain.wish;

import lombok.Data;

@Data
public class Wish {
    private Long id;
    private String title;
    private String period;
    private Integer cost;

    private Boolean open; //공개 여부
    private RegionType regionType; //여행 지역
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
