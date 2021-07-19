package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new ConcurrentHashMap<>(); // static. 여러 스레드로 해쉬맵에 접근하는 경우 그냥 해쉬맵을 쓰면 안되고 ConcurrentHashMap을 써야함.
    private static Long sequence = 0L; //static. 상기 이유와 마찬가지로 그냥 Long을 쓰면 안되고 AtomicLong 등을 사용해야 한다.

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findbyId(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        final Item findItem = findbyId(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
