package com.steph.authentification.entity;
import java.util.Collection;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

	@Entity
	@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
	public class User {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;
		
		@Column(name = "first_name")
		private String firstName;
		
		@Column(name = "last_name")
		private String lastName;
		
		@Column(length = 3)
		private int age;
		
		private String phone;
		
		private String confirmPassword;
		
		private String email;
		
		
		private String password;
		
		@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		@JoinTable(
				name = "users_roles",
				joinColumns = @JoinColumn(
						name = "user_id",referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(
						name = "role_id", referencedColumnName = "id")
				)
		private Collection<Role> roles;
		
		public User(String firstName, String lastName, int age, String phone, String email,
				String password,String confirmPassword, Collection<Role> roles) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.phone = phone;
			this.confirmPassword = confirmPassword;
			this.email = email;
			this.password = password;
			this.roles = roles;
		}

		public Long getId() {
			return id;
		}

		public User() {}

		

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}

		public String getConfirmPassword() {
			return confirmPassword;
		}

		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public Collection<Role> getRoles() {
			return roles;
		}

		public void setRoles(Collection<Role> roles) {
			this.roles = roles;
		}

		
	}



