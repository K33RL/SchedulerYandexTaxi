package com.myproject.scheduleryandextaxi.repository;

import com.myproject.scheduleryandextaxi.model.MomentPrice;
import io.micrometer.core.annotation.Timed;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface PriceRepository extends CrudRepository<MomentPrice, Long> {
    @Timed("gettingAllPrices")
    List<MomentPrice> findAll();
}
