package com.rnd.graphql.graphql.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Vehicle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "type", nullable = false)
	private String type;

	@Column(name = "model_code", nullable = false)
	private String modelCode;

	@Column(name = "brand_name")
	private String brandName;

	@Column(name = "launch_date")
	private LocalDate launchDate;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private User user;

	public String getFormattedDate() {
		return getLaunchDate().toString();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public LocalDate getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDate launchDate) {
		this.launchDate = launchDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", type=" + type + ", modelCode=" + modelCode + ", brandName=" + brandName
				+ ", launchDate=" + launchDate + ", user=" + user + "]";
	}

}
