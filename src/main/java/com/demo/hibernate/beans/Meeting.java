package com.demo.hibernate.beans;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "meeting")
public class Meeting implements Serializable {
	private static final long serialVersionUID = 1L;

    /** identifier field */
    private Integer id;

    /** persistent field */
    private String sender;

    /** nullable persistent field */
    private String starttime;

    /** nullable persistent field */
    private String endtime;

    /** nullable persistent field */
    private String address;

    /** nullable persistent field */
    private String title;

    /** nullable persistent field */
    private String content;



    /** default constructor */
    public Meeting() {
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

    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStarttime() {
        return this.starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return this.endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
