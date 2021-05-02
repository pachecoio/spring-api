package dev.thiagopacheco.springbootapi.repositories;


import dev.thiagopacheco.springbootapi.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
