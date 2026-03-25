package com.dev.tomato.hospital_management.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dev.tomato.hospital_management.entity.type.AuthProviderType;
import com.dev.tomato.hospital_management.entity.type.RoleType;
import com.dev.tomato.hospital_management.security.RolePermissionMapping;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="app_user")
public class User  implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    @Column(unique = true, nullable = false)
    // username can be of type email or any unique identifier based on the provider
    private String username;
    
    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private AuthProviderType providerType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles= new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return roles.stream()
        //     .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
        //     .collect(Collectors.toSet());

        Set<SimpleGrantedAuthority> authorities= new HashSet<>();
        roles.forEach(
            role->{
                Set<SimpleGrantedAuthority> permissions= RolePermissionMapping.getAuthoritiesForRole(role);
                authorities.addAll(permissions);
                authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));
            }
        );
        return authorities;
    }
    

}
