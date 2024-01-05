package net.thisislaz.angularwithspringboot.repositories;

import net.thisislaz.angularwithspringboot.models.User;

import java.util.Collection;

public interface UserRepository<T extends User>{
    /*   Basic CRUD operations    */

    T create(T data);
    Collection<T> list(int page, int pageSize);
    T read(Long id);
    T update(T data);
    Boolean delete(Long id);

    /* More complex operations */
}
