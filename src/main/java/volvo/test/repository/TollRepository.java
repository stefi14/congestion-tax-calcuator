package volvo.test.repository;

import org.springframework.data.repository.CrudRepository;
import volvo.test.model.TollPrices;

public interface TollRepository extends CrudRepository<TollPrices, Long> {
}
