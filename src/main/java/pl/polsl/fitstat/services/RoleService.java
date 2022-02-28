package pl.polsl.fitstat.services;

import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.errors.ResourceNotFoundException;
import pl.polsl.fitstat.models.RoleEntity;
import pl.polsl.fitstat.repositories.RoleRepository;
import pl.polsl.fitstat.security.KeyCloakClientConfig;

import java.util.ArrayList;
import java.util.List;

@Service
class RoleService {

    private final RoleRepository roleRepository;
    private final KeyCloakClientConfig keycloak;

    public RoleService(RoleRepository roleRepository, KeyCloakClientConfig keycloak) {
        this.roleRepository = roleRepository;
        this.keycloak = keycloak;
    }

    public RoleEntity getRoleByName(String name) {
        return roleRepository.findByRole(name).orElseThrow(() -> new ResourceNotFoundException("Role doesnt exist!"));
    }

    public List<RoleRepresentation> getAllRoleToRoleRepresentation() {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();

        for (RoleEntity role : roleRepository.findAll()) {
            roleRepresentations.add(
                    keycloak
                            .getInstance()
                            .realm(keycloak.getRealm())
                            .roles()
                            .get(role.getRole())
                            .toRepresentation());
        }

        return roleRepresentations;
    }

}