package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegionRepository extends JpaRepository<Region, Long> {

    boolean existsRegionByName(String name);

    boolean existsRegionByIdAndDeleteFlag(Long regionId, boolean status);



}
