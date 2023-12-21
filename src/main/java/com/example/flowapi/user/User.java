package com.example.flowapi.user;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="email")
    private String email;
    @Column(name="role")
    private String role;
    @Column(name="dni")
    private String dni;
    @Column(name="name")
    private String name;
    @Column(name="lastname_main")
    private String lastnameMain;
    @Column(name="lastname_secondary")
    private String lastnameSecondary;
    @Column(name="address")
    private String address;
    @Column(name="created_at")
    private Timestamp createdAt;
    @Column(name="state")
    private String state;
    @Column(name="deleted_at")
    private Timestamp deletedAt;

    public User() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastnameMain() {
        return lastnameMain;
    }

    public void setLastnameMain(String lastnameMain) {
        this.lastnameMain = lastnameMain;
    }

    public String getLastnameSecondary() {
        return lastnameSecondary;
    }

    public void setLastnameSecondary(String lastnameSecondary) {
        this.lastnameSecondary = lastnameSecondary;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Timestamp getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Timestamp deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", dni='" + dni + '\'' +
                ", name='" + name + '\'' +
                ", lastnameMain='" + lastnameMain + '\'' +
                ", lastnameSecondary='" + lastnameSecondary + '\'' +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                ", state='" + state + '\'' +
                ", deletedAt=" + deletedAt +
                '}';
    }
}
