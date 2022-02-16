package hello.wishservice.domain.wish;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * ALONE: 혼자
 * FAMILY: 가족
 * COUPLE: 커플
 * FRIEND: 친구
 */

@Data
@AllArgsConstructor
public class TravelType {
    private String code;
    private String displayName;
}
