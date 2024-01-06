package net.thisislaz.angularwithspringboot.repositories.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thisislaz.angularwithspringboot.exceptions.ApiException;
import net.thisislaz.angularwithspringboot.models.Role;
import net.thisislaz.angularwithspringboot.models.User;
import net.thisislaz.angularwithspringboot.repositories.RoleRepository;
import net.thisislaz.angularwithspringboot.repositories.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.UUID;

import static java.util.Map.of;
import static java.util.Objects.requireNonNull;
import static net.thisislaz.angularwithspringboot.enumeration.RoleType.*;
import static net.thisislaz.angularwithspringboot.enumeration.VerificationType.*;
import static net.thisislaz.angularwithspringboot.query.UserQueries.*;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    private final NamedParameterJdbcTemplate jdbc;
    private final RoleRepository<Role> roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");
        // save the new user
        try {
            //this gives us the id of the new user that just got saved in the database
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSQLParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder);
            // no id has been set yet when using this "user" object we received
                // here we are setting the id
            user.setId(requireNonNull(holder.getKey()).longValue());
            // setting role to the user using their id
            roleRepository.addRoleToUser(user.getId(), ROLE_USER.name());
            // send verification URL
            String verificationURL = getVerificationURL(UUID.randomUUID().toString(), ACCOUNT.getType());
            // save url in verification table
            jdbc.update(INSERT_ACCOUNT_VERIFICATION_URL_QUERY, of("user_id", user.getId(), "url", verificationURL));
            // send email to user with verification url
        /*    emailService.sendVerificationUrl(user.getFirstName(),user.getEmail(), verificationURL, ACCOUNT); */
            user.setEnabled(false);
            user.setNotLocked(true);
            // return the newly authed user
            return user;
            // if any errors, throw exception with proper message
            // using this exception specifically because we need to know when we didnt find anything
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: " + ROLE_USER.name());
        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");
        }

    }

    @Override
    public Collection<User> list(int page, int pageSize) {
        return null;
    }

    @Override
    public User read(Long id) {
        return null;
    }

    @Override
    public User update(User data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }


// -------------  private methods  -----------------

    private Integer getEmailCount(String email) {
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, of("email",email), Integer.class);
    }

    private SqlParameterSource getSQLParameterSource(User user) {
        return new MapSqlParameterSource()
                .addValue("firstName", user.getFirstName())
                .addValue("lastName", user.getLastName())
                .addValue("email", user.getEmail())
                .addValue("password", encoder.encode(user.getPassword()));
    }

    private String getVerificationURL(String verificationKey, String accountType) {
        // this is a sever url, not a frontend client url
        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/verify/"+ accountType + "/" + verificationKey).toUriString();
    }

}

