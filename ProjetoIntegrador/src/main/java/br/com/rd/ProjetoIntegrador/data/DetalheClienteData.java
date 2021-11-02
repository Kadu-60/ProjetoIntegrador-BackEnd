package br.com.rd.ProjetoIntegrador.data;

import br.com.rd.ProjetoIntegrador.model.entity.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DetalheClienteData implements UserDetails {

    private final Optional<Cliente> cliente;

    public DetalheClienteData(Optional<Cliente> cliente) {
        this.cliente = cliente;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return cliente.orElse(new Cliente()).getPassword();
    }

    @Override
    public String getUsername() {
        return cliente.orElse(new Cliente()).getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
