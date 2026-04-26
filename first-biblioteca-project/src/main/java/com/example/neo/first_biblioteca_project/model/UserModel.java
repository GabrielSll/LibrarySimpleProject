package com.example.neo.first_biblioteca_project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name= "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String email;

    private String password;

    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
        // Retorna as permissões/roles do usuário
        // O Spring Security usa isso para verificar autorização
        // SimpleGrantedAuthority é a implementação mais simples de GrantedAuthority
    }

    @Override
    public String getPassword() {
        return password;
        // O Spring Security chama esse método para pegar a senha embaralhada
        // e comparar com a senha que o usuário digitou no login
    }

    @Override
    public String getUsername() {
        return email;
        // Retorna o identificador único do usuário
        // Aqui usamos o email, mas poderia ser um username também
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        // Retorna se a conta está expirada
        // true → conta válida, não expirada
        // Em um sistema real, você poderia ter uma lógica aqui
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        // Retorna se a conta está bloqueada
        // true → conta não está bloqueada
        // Ex: bloquear após 5 tentativas de login erradas
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        // Retorna se as credenciais (senha) estão expiradas
        // true → senha ainda válida
        // Ex: sistemas que forçam troca de senha a cada 90 dias
    }

    @Override
    public boolean isEnabled() {
        return true;
        // Retorna se o usuário está ativo
        // true → usuário ativo
        // Ex: desativar usuário sem deletar do banco
    }
}
