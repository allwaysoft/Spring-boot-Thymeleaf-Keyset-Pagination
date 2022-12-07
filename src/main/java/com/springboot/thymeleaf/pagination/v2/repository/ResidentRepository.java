package com.springboot.thymeleaf.pagination.v2.repository;

import com.springboot.thymeleaf.pagination.v2.model.Resident;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends PagingAndSortingRepository<Resident, Long> {

    @Query(value = "SELECT * FROM resident AS r WHERE r.id > ?1 ORDER BY r.id ASC LIMIT ?2",
            nativeQuery = true)
    List<Resident> fetchAllAscNext(Long id, Long limit);

    @Query(value = "SELECT * FROM resident AS r WHERE r.id < ?1 ORDER BY r.id DESC LIMIT ?2",
            nativeQuery = true)
    List<Resident> fetchAllAscPrevious(Long id, Long limit);

    @Query(value = "SELECT count(*)  FROM resident", nativeQuery = true)
    public Long fetchCount();

    @Query(value = "SELECT min(id)  FROM resident", nativeQuery = true)
    public Long fetchMinId();

    @Query(value = "SELECT max(id)  FROM resident", nativeQuery = true)
    public Long fetchMaxId();
}
