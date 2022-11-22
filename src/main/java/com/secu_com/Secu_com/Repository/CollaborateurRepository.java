package com.secu_com.Secu_com.Repository;

import com.secu_com.Secu_com.Modeles.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    Optional<Collaborateur> findByNomutilisateurOrEmail(String nomutilisateur, String email);
    Optional<Collaborateur> findByNomutilisateur(String nomutilisateur);
    Boolean existsByNomutilisateur(String nomutilisateur);
    Boolean existsByEmail(String email);
}
