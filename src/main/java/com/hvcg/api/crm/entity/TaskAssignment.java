package com.hvcg.api.crm.entity;


import javax.persistence.*;
import java.util.List;

@Entity(name = "task_assignment")
@Table(name = "task_assignment")
public class TaskAssignment{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;



    @Column(name= "delete_flg", columnDefinition = "bit default 0", nullable = false)
    private boolean deleteFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

}
