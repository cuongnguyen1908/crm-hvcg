package com.hvcg.api.crm.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import java.util.List;

@Entity
@Table(name = "account_type")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AccountType extends BaseEntity {

    @Column(name = "name", length = 250)
    private String name;


    @OneToMany(mappedBy = "accountType",
            cascade = {CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    private List<EmployeeAccount> employeeAccounts;

    public AccountType() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmployeeAccount> getEmployeeAccounts() {
        return employeeAccounts;
    }

    public void setEmployeeAccounts(List<EmployeeAccount> employeeAccounts) {
        this.employeeAccounts = employeeAccounts;
    }
}
