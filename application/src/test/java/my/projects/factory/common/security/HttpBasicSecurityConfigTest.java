package my.projects.factory.common.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsServiceTest {

    private final HttpBasicSecurityConfig config = new HttpBasicSecurityConfig();

    @Test
    void shouldLoadAdminUser() {
        UserDetailsService service = config.userDetailsService();

        UserDetails admin = service.loadUserByUsername("admin");

        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
        assertTrue(admin.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void shouldLoadRegularUser() {
        UserDetailsService service = config.userDetailsService();

        UserDetails user = service.loadUserByUsername("user");

        assertNotNull(user);
        assertEquals("user", user.getUsername());
        assertTrue(user.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void shouldThrowExceptionForUnknownUser() {
        UserDetailsService service = config.userDetailsService();

        assertThrows(Exception.class,
                () -> service.loadUserByUsername("unknown"));
    }
}