package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.AvatarDTO;
import com.hvcg.api.crm.dto.CustomerDetailDTO;
import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT new com.hvcg.api.crm.dto.CustomerDTO(c.id, c.firstName, c.lastName, " +
            "c.fullName, c.email, " +
            "c.dayOfBirth, c.phone, c.gender) " +
            "FROM customer c " +
            "WHERE c.deleteFlag = :status")
    List<CustomerDTO> getAllCustomer(@Param("status") boolean status);


    boolean existsCustomerByIdAndDeleteFlag(Long customerId, boolean status);

    Optional<Customer> findCustomerByIdAndDeleteFlag(Long customerId, boolean status);

    @Query("SELECT new com.hvcg.api.crm.dto.CustomerDetailDTO(c.id, c.firstName, c.lastName, c.fullName, c.email, c" +
            ".dayOfBirth, c.phone, c.gender) " +
            "FROM customer c WHERE c.id = :customerId AND c.deleteFlag = :status")
    Optional<CustomerDetailDTO> findCustomerById(@Param("customerId") Long id, @Param("status") boolean deleteFlag);


    @Query("SELECT new com.hvcg.api.crm.dto.CustomerDTO(c.id, c.firstName, c.lastName, " +
            "c.fullName, c.email, " +
            "c.dayOfBirth, c.phone, c.gender) " +
            "FROM customer c " +
            "WHERE c.deleteFlag = :status " +
            "AND lower(c.fullName) like concat('%', :textSearch, '%')")
    Page<CustomerDTO> searchAllCustomerByFullName(Pageable pageable,
                                     @Param("status") boolean status, @Param("textSearch") String textSearch);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET avatar = :avatar  WHERE id = :customerId")
    void updateAvatar(@Param("avatar") Avatar avatar, @Param("customerId") Long id);

    @Query(value = "SELECT new com.hvcg.api.crm.dto.AvatarDTO(c.avatar.id, c.avatar.name, c.avatar.url, c.avatar.thumbUrl) " +
            "from customer c where c.id = :customerId")
    Optional<AvatarDTO> findAvatarCustomById(@Param("customerId") Long id);

    @Query(value = "SELECT c.avatar from customer c where c.id = :customerId")
    Optional<Avatar> findAvatarById(@Param("customerId") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET deleteFlag = :status WHERE id = :customerId")
    void deleteCustomerById(@Param("customerId") Long customerId, @Param("status") boolean status);



}
