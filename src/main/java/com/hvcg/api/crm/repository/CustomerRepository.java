package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.CustomerDTO;
import com.hvcg.api.crm.entity.Avatar;
import com.hvcg.api.crm.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //Long id, String firstName, String lastName, String fullName,
    // String email, Date dayOfBirth,
    //                       String phone, boolean gender
    @Query("SELECT new com.hvcg.api.crm.dto.CustomerDTO(c.id, c.firstName, c.lastName, " +
            "c.fullName, c.email, " +
            "c.dayOfBirth, c.phone, c.gender) " +
            "FROM customer c " +
            "WHERE c.deleteFlag = :status")
    Page<CustomerDTO> getAllCustomer(Pageable pageable, @Param("status") boolean status);

    @Query("FROM customer c WHERE c.deleteFlag = :status")
    Page<Customer> findAll(Pageable pageable, @Param("status") boolean deleteFlag);

    boolean existsCustomerByIdAndDeleteFlag(Long customerId, boolean status);

    Optional<Customer> findCustomerByIdAndDeleteFlag(Long customerId, boolean status);

    @Query("SELECT new com.hvcg.api.crm.dto.CustomerDTO(c.id, c.firstName, c.lastName, c.fullName, c.email, c" +
            ".dayOfBirth, c.phone, c.gender) " +
            "FROM customer c WHERE c.id = :customerId AND c.deleteFlag = :status")
    Optional<CustomerDTO> findCustomerById(@Param("customerId") Long id, @Param("status") boolean deleteFlag);


    @Query("FROM customer c WHERE lower(c.fullName) like concat('%', lower(:searchValue), '%') and c.deleteFlag " +
            "=:status")
    Page<Customer> searchAllCustomer(Pageable pageable, @Param("searchValue") String searchValue,
                                     @Param("status") boolean status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer SET avatar = :avatar  WHERE id = :customerId")
    void updateAvatar(@Param("avatar") Avatar avatar, @Param("customerId") Long id);

    @Query(value = "SELECT c.avatar from customer c where c.id = :customerId")
    Optional<Avatar> findAvatarById(@Param("customerId") Long id);


}
