package at.samegger.dataaccess;

import at.samegger.domain.User;

import java.sql.Connection;
import java.util.List;

public class UserDAO implements DAOInterface<User> {

    private Connection con;

    @Override
    public User findByID(Integer id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public void save(User o) {

    }

    @Override
    public void update(User o) {

    }

    @Override
    public void delete(Integer id) {

    }
}
