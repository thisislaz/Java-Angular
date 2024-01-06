package net.thisislaz.angularwithspringboot.repositories.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.thisislaz.angularwithspringboot.exceptions.ApiException;
import net.thisislaz.angularwithspringboot.models.Role;
import net.thisislaz.angularwithspringboot.repositories.RoleRepository;
import net.thisislaz.angularwithspringboot.rowmapper.RoleRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static net.thisislaz.angularwithspringboot.enumeration.RoleType.ROLE_USER;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RoleRepositoryImpl implements RoleRepository<Role> {

    private static final String INSERT_ROLE_TO_USER_QUERY = "";
    private static final String SELECT_ROLE_BY_NAME_QUERY = "";
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Role create(Role data) {
        return null;
    }

    @Override
    public Collection<Role> list(int page, int pageSize) {
        return null;
    }

    @Override
    public Role read(Long id) {
        return null;
    }

    @Override
    public Role update(Role data) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public void addRoleToUser(Long userId, String roleName) {
        log.info("Adding role {} to suer id: {}", roleName, userId);
        try {
            // the rolerowmapper is being sued to map the results into a java object
            Role role = jdbc.queryForObject(SELECT_ROLE_BY_NAME_QUERY, Map.of("roleName", roleName), new RoleRowMapper());
            // injecting user by userid inside the roles
            jdbc.update(INSERT_ROLE_TO_USER_QUERY, Map.of("userId", userId, "roleId", requireNonNull(role).getId()));
        } catch (EmptyResultDataAccessException exception) {
            throw new ApiException("No role found by name: "+ ROLE_USER.name());

        } catch (Exception exception) {
            throw new ApiException("An error occurred. Please try again.");

        }

    }

    @Override
    public Role getRoleByUserId(Long userId) {
        return null;
    }

    @Override
    public Role getRoleByUserEmail(String email) {
        return null;
    }

    @Override
    public void updateUserRole(Long userId, String roleName) {

    }
}
