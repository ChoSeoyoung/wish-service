package hello.wishservice.domain.wish;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishRepository {
    private static final Map<Long, Wish> store = new HashMap<>(); //static 사용
    private static long sequence = 0L; //static 사용

    public Wish save(Wish wish) {
        wish.setId(++sequence);
        store.put(wish.getId(), wish);
        return wish;
    }
    public Wish findById(Long id) {
        return store.get(id);
    }
    public List<Wish> findAll() {
        return new ArrayList<>(store.values());
    }
    public void update(Long wishId, Wish updateParam) {
        Wish findWish = findById(wishId);
        findWish.setWishName(updateParam.getWishName());
        findWish.setPeriod(updateParam.getPeriod());
        findWish.setCost(updateParam.getCost());
    }
    public void clearStore() {
        store.clear();
    }
}
