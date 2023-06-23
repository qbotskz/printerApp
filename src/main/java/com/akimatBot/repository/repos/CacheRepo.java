package com.akimatBot.repository.repos;

import com.akimatBot.entity.custom.Cache;
import com.akimatBot.entity.custom.Cheque;
import com.akimatBot.entity.enums.CacheTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepo extends JpaRepository<Cache, Long> {

    Cache findByCacheType(CacheTypes cacheTypes);

}
