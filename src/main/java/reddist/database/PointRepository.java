package reddist.database;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<HitsEntity, Integer> {

    @Transactional
    void deleteAllByOwner(UserEntity owner);

    List<HitsEntity> findAllByOwner(UserEntity owner);
}
