package com.secu_com.Secu_com.Securite;

import com.secu_com.Secu_com.Modeles.Collaborateur;
import com.secu_com.Secu_com.Modeles.Role;
import com.secu_com.Secu_com.Repository.CollaborateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {
    private CollaborateurRepository collaborateurRepository;
    public CustomUserDetailsService(CollaborateurRepository collaborateurRepository){
        this.collaborateurRepository=collaborateurRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String nomutilisateurOrEmail) throws UsernameNotFoundException {

        Collaborateur collaborateur = collaborateurRepository.findByNomutilisateurOrEmail(nomutilisateurOrEmail, nomutilisateurOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email:" + nomutilisateurOrEmail));
        return new org.springframework.security.core.userdetails.User(collaborateur.getEmail(),
                collaborateur.getPassword(), mapRolesToAuthorities(collaborateur.getRoles()));
    }
    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
