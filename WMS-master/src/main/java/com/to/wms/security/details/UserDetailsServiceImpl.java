package com.to.wms.security.details;

import com.to.wms.model.User;
import com.to.wms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userAuthRepository) {
        this.userRepository = userAuthRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByLogin(username.toLowerCase(Locale.ROOT));

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Not found user with email: " + username);
        }

        return new UserDetailsImpl(user.get());
    }

}
