package com.demo.hibernate.beans;

import lombok.*;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@Builder
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
    private Integer id;

    /** nullable persistent field */
    private String username;

    /** nullable persistent field */
    private String password;

    /** nullable persistent field */
    private String email;

    /** default constructor */
    public User() {
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
