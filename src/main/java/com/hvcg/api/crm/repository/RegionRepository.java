package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {

    Optional<Region> findByIdAndDeleteFlag(Long id, boolean status);

    boolean existsRegionByName(String name);

    boolean existsRegionByIdAndDeleteFlag(Long regionId, boolean status);



}
