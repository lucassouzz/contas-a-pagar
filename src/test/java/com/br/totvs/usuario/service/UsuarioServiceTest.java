package com.br.totvs.usuario.service;

import com.br.totvs.core.exceptions.UnauthorizedException;
import com.br.totvs.usuario.Usuario;
import com.br.totvs.usuario.exceptions.UsuarioNotFoundException;
import com.br.totvs.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.br.totvs.usuario.UsuarioTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UsuarioServiceTest {

    public static final Usuario entity = createUsuario();

    @Autowired
    private UsuarioService service;

    @MockBean
    private UsuarioRepository repository;

    @Test
    void shouldLogin() throws Exception {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(entity));

        String token = service.login(EMAIL, PASSWORD);

        verify(repository).findByEmail(anyString());

        assertNotNull(token);
    }

    @Test
    void shouldThrowUnauthorizedExceptionWhenLogin() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(entity));

        assertThrows(UnauthorizedException.class, () -> service.login(EMAIL, "123@Test"));
    }

    @Test
    void shouldFindByEmail() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.of(entity));

        Usuario usuario = service.findByEmail(EMAIL);

        verify(repository).findByEmail(anyString());

        assertEquals(usuario.getId(), entity.getId());
        assertEquals(usuario.getEmail(), entity.getEmail());
        assertEquals(usuario.getPassword(), entity.getPassword());
    }

    @Test
    void shouldThrowUsuarioNotFoundExceptionFindByEmail() {
        when(repository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        assertThrows(UsuarioNotFoundException.class, () -> service.findByEmail(EMAIL));
    }
}
