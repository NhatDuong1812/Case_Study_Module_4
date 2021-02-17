package com.example.demo.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String username;
    @Size(min = 6)
    private String password;


    private String full_name;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 3,max = 10)
    private String phone_number;

    @NotNull
    private String address;


    @ManyToOne
    @JoinColumn(name = "roll_id")
    private AppRole role;

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

    public AppUser(@Min(6) String name, @Email String email, @Min(6) String password, String phone, String address, boolean status, AppRole role) {
        this.username = name;
        this.email = email;
        this.password = password;
        this.phone_number = phone;
        this.address = address;
        this.role = role;
    }
    public AppUser(@Min(6) String name,@Min(6) String password){
        this.username = name;
        this.password = password;
    }
}
