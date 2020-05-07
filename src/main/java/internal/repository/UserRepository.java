package internal.repository;

import internal.repository.model.ApplicationUser;

import java.util.List;

public interface UserRepository {

    List<ApplicationUser> findAll();

    void deleteAll();

    void saveUser(ApplicationUser applicationUser);

    ApplicationUser findUserByUserName(String name);

    long updateUser(ApplicationUser applicationUser);

    void deleteUserByName(String name);

}
