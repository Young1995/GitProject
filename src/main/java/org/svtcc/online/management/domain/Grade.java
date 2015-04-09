package org.svtcc.online.management.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Created by hanrenwei on 2/9/15.
 */
@SuppressWarnings("serial")
@Entity(name = "Grade")
@Table(name = "svtcc_grade")
public class Grade implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "grade_name")
    private String name;
    
    @Column(name = "create_time")
   	@Temporal(TemporalType.TIMESTAMP)
   	private Date createTime;
   	
   	@Column(name = "update_time")
   	@Temporal(TemporalType.TIMESTAMP)
   	private Date updateTime;
   	
   	@Column(name = "create_user")
   	private String createUser;
   	
   	@Column(name = "update_user")
   	private String updateUser;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
