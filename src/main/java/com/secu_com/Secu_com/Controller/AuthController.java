package com.secu_com.Secu_com.Controller;

import com.secu_com.Secu_com.Modeles.Collaborateur;
import com.secu_com.Secu_com.Modeles.Role;
import com.secu_com.Secu_com.Payload.LoginDto;
import com.secu_com.Secu_com.Payload.SignUpDto;
import com.secu_com.Secu_com.Repository.CollaborateurRepository;
import com.secu_com.Secu_com.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateCollaborateur(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getNomutilisateurOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerCollaborateur(@RequestBody SignUpDto signUpDto){

        // add check for username exists in a DB
        if(collaborateurRepository.existsByNomutilisateur(signUpDto.getNomutilisateur())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(collaborateurRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setNom(signUpDto.getNom());
        collaborateur.setPrenom(signUpDto.getPrenom());
        collaborateur.setNomutilisateur(signUpDto.getNomutilisateur());
        collaborateur.setEmail(signUpDto.getEmail());
        collaborateur.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        collaborateur.setRoles(Collections.singleton(roles));

        collaborateurRepository.save(collaborateur);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }



}
