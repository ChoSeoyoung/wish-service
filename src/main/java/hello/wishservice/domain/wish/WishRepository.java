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
    public void update(Long id, Wish updateParam) {
        Wish findWish = findById(id);
        findWish.setTitle(updateParam.getTitle());
        findWish.setPeriod(updateParam.getPeriod());
        findWish.setCost(updateParam.getCost());
    }
    public void delete(Long id){
        store.remove(id);
        for(Long i=id+1;i<=sequence;i++){
            Wish tempWish = store.get(i);
            tempWish.setId(i-1);
        }
        sequence-=1;
    }
    public void clearStore() {
        store.clear();
    }
}
