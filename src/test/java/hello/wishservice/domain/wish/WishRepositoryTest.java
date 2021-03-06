package hello.wishservice.domain.wish;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class WishRepositoryTest {
    WishRepository wishRepository = new WishRepository();

    @AfterEach
    void afterEach(){
        wishRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Wish wish = new Wish("wish1", "2022.2.10~2022.2.11",5, true, RegionType.DOMESTIC, "ALONE");

        //when
        Wish savedWish = wishRepository.save(wish);

        //then
        assertThat(wish).isEqualTo(savedWish);
    }

    @Test
    void findAll() {
        //given
        Wish wish1 = new Wish("wish1", "2022.2.10~2022.2.11",5, true, RegionType.DOMESTIC, "ALONE");
        Wish wish2 = new Wish("wish2", "23살",20, true, RegionType.DOMESTIC, "ALONE");

        wishRepository.save(wish1);
        wishRepository.save(wish2);

        //when
        List<Wish> result = wishRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(wish1,wish2);
    }

    @Test
    void update() {
        //given
        Wish wish = new Wish("wish1", "2022.2.10~2022.2.11",5, true, RegionType.DOMESTIC, "ALONE");

        Wish savedWish = wishRepository.save(wish);
        Long wishId = savedWish.getId();

        //when
        Wish updateParam = new Wish("wish2", "23살", 20, true, RegionType.DOMESTIC, "ALONE");
        wishRepository.update(wishId, updateParam);

        Wish findWish = wishRepository.findById(wishId);
        //then
        assertThat(findWish.getTitle()).isEqualTo(updateParam.getTitle());
        assertThat(findWish.getPeriod()).isEqualTo(updateParam.getPeriod());
        assertThat(findWish.getCost()).isEqualTo(updateParam.getCost());
    }

    @Test
    void delete(){
        //given
        Wish wish = new Wish("wish1", "2022.2.10~2022.2.11",5, true, RegionType.DOMESTIC, "ALONE");
        Wish savedWish = wishRepository.save(wish);
        Long wishId = savedWish.getId();

        //when
        wishRepository.delete(wishId);

        List<Wish> result = wishRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(0);
    }
}