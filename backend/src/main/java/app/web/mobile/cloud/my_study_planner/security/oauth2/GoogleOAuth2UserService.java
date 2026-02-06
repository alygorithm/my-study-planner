package app.web.mobile.cloud.my_study_planner.security.oauth2;

import app.web.mobile.cloud.my_study_planner.model.AuthProvider;
import app.web.mobile.cloud.my_study_planner.model.User;
import app.web.mobile.cloud.my_study_planner.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class GoogleOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public GoogleOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // Estrai informazioni da Google
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");
        String googleId = oAuth2User.getAttribute("sub"); // Google ID univoco

        // Cerca o crea utente
        User user = userRepository.findByProviderAndProviderId(AuthProvider.GOOGLE, googleId)
                .orElseGet(() -> {
                    // Se non esiste con Google ID, cerca per email
                    return userRepository.findByEmail(email)
                            .map(existingUser -> {
                                // Utente esistente con email, aggiorna provider a GOOGLE
                                existingUser.setProvider(AuthProvider.GOOGLE);
                                existingUser.setProviderId(googleId);
                                return existingUser;
                            })
                            .orElseGet(() -> {
                                // Nuovo utente con Google
                                User newUser = new User();
                                newUser.setUsername(name);
                                newUser.setEmail(email);
                                newUser.setProvider(AuthProvider.GOOGLE);
                                newUser.setProviderId(googleId);
                                newUser.setPassword(null); // Password non necessaria per OAuth
                                return newUser;
                            });
                });

        // Salva l'utente nel database
        userRepository.save(user);

        return oAuth2User;
    }
}