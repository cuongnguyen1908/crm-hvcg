package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.entity.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM avatar WHERE id = :imageId")
    void deleteImage(@Param("imageId") Long id);
}
