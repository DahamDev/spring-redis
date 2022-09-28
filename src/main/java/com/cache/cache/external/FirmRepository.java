package com.cache.cache.external;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirmRepository{
    Firm getFirm(Integer id);
    void saveFirm(Firm firm);


}
