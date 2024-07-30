package by.aston.bank.repository;

import by.aston.bank.model.User;

public class UserRepository extends BaseRepository<Long, User> {

    public UserRepository() {
        super(User.class);
    }

}
