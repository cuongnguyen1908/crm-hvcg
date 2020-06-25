package com.hvcg.api.crm.repository;

import com.hvcg.api.crm.dto.CustomerAddressDTO;
import com.hvcg.api.crm.entity.CustomerAddress;
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
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long>,
        PagingAndSortingRepository<CustomerAddress, Long> {
    @Query(value = "SELECT new com.hvcg.api.crm.dto.CustomerAddressDTO(c.id, c.address, c.contactPerson, c" +
            ".contactPhone , c.description, c.customer.id) FROM customer_address c WHERE customer.id = :customerId " +
            "AND deleteFlag = " +
            ":status")
    Page<CustomerAddressDTO> findAllCustomerAddressByCustomerId(Pageable pageable, @Param("customerId") Long customerId,
                                                                @Param("status") boolean status);

    Optional<CustomerAddress> findCustomerAddressByIdAndDeleteFlag(Long addressId, boolean status);


    @Modifying
    @Transactional
    @Query(value = "UPDATE customer_address SET deleteFlag = :status WHERE customer.id = :customerId")
    void deleteAllByCustomerId(@Param("customerId") Long customerId, @Param("status") boolean status);


    @Query(value = "UPDATE customer_address SET deleteFlag = :status WHERE id = :customerAddressId")
    void deleteCustomerAddressesById(@Param("customerAddressId") Long customerId, @Param("status") boolean status);

    @Query(value = "INSERT INTO customer_address(address, contactPerson, contactPhone, description) VALUES(:address, " +
            ":contactPerson, :contactPhone, :desciption)", nativeQuery = true)
    void createCustomerAddress(@Param("address") String address, @Param("contactPerson") String contactPerson,
                               @Param("contactPhone") String contactPhone, @Param("desciption") String description);

}