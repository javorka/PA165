package cz.muni.pa165.pneuservis.mvc.security;

import cz.muni.pa165.pneuservis.api.dto.UserDTO;
import cz.muni.pa165.pneuservis.api.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Michal Krajcovic <mkrajcovic@mail.muni.cz>
 */
@Service
public class UserSecurity implements UserDetailsService {

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDTO userDTO = userFacade.findByEmail(s);

        if (userDTO == null) {
            throw new UsernameNotFoundException("username not found");
        }

        List<GrantedAuthority> authorities = userDTO.getRoles().stream()
                .map(u -> new SimpleGrantedAuthority(u.toString())).collect(Collectors.toList());
        return new User(userDTO.getEmail(), userDTO.getPassword(), authorities);
    }
}
