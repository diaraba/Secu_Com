package com.secu_com.Secu_com.Payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String nom;
    private String prenom;
    private String nomutilisateur;
    private String email;
    private String password;
}
