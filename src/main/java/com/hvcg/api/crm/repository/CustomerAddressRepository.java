package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.entity.CustomerAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long>{
    @Query(value = "SELECT new com.hvcg.api.crm.dto.CustomerAddressDTO(c.id, c.address, c.contactPerson, c" +
            ".contactPhone , c.description) FROM customer_address c WHERE customer.id = :customerId " +
            "AND deleteFlag = " +
            ":status")
    Page<CustomerAddressDTO> findAllCustomerAddressByCustomerId(Pageable pageable, @Param("customerId") Long customerId,
                                                                @Param("status") boolean status);

    @Query(value = "SELECT (COUNT(c) > 0) FROM customer_address c " +
            "WHERE id = :customerAddressId AND customer.id = :customerId AND deleteFlag = :status")
    boolean existsCustomerAddressByIdAndCustomerIdAndDeleteFlag(@Param("customerAddressId") Long customerAddressId, @Param("customerId") Long customerId, @Param("status") boolean status);


    @Query(value = "SELECT (COUNT(c) > 0) FROM customer_address c " +
            "WHERE id = :customerAddressId AND deleteFlag = :status")
    boolean existsCustomerAddressByIdAndDeleteFlag(@Param("customerAddressId") Long customerAddressId, @Param("status") boolean status);

    @Modifying
    @Transactional
    @Query(value = "UPDATE customer_address SET deleteFlag = :status WHERE customer.id = :customerId")
    void deleteAllCustomerAddressByCustomerId(@Param("customerId") Long customerId, @Param("status") boolean status);


    @Modifying
    @Transactional
    @Query(value = "UPDATE customer_address SET deleteFlag = :status WHERE id = :customerId")
    void deleteCustomerAddressById(@Param("customerId") Long customerId, @Param("status") boolean status);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO customer_address(address ,contact_person, contact_phone, description, customer_id, delete_flg, created_by)" +
            " VALUES(:address, :contactPerson, :contactPhone, :description, :customerId, :status, :createdBy)", nativeQuery = true)
    void createCustomerAddress(@Param("address") String address, @Param("contactPerson") String contactPerson, @Param("contactPhone") String contactPhone,
                               @Param("description") String description,
                               @Param("customerId") Long customerId, @Param("status") boolean status, @Param("createdBy") String createdBy);


}