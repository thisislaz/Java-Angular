package net.thisislaz.angularwithspringboot.repositories.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thisislaz.angularwithspringboot.exceptions.ApiException;
import net.thisislaz.angularwithspringboot.models.User;
import net.thisislaz.angularwithspringboot.repositories.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository<User> {

    public final NamedParameterJdbcTemplate jdbc;
    private final JdbcClient jdbcClient;
    private static final String COUNT_USER_EMAIL_QUERY = "";

    @Override
    public User create(User user) {
        // check the email is unique
        if(getEmailCount(user.getEmail().trim().toLowerCase()) > 0) throw new ApiException("Email already in use. Please use a different email and try again.");
        // save the new user
        try {
            //this gives us the id of the new user that just got saved in the database
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSQLParameterSource(user);
            jdbc.update(INSERT_USER_QUERY, parameters, holder)
        } catch (EmptyResultDataAccessException) {} catch (Exception exception) {}
        // add role to the user
        // send verification URL
        // save url in verification table
        // send email to user with verification url
        // return the newly authed user
        // if any errors, throw exception with proper message
        return null;
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
        return jdbc.queryForObject(COUNT_USER_EMAIL_QUERY, Map.of("email",email), Integer.class);
    }

    private SqlParameterSource getSQLParameterSource(User user) {
    }

}

