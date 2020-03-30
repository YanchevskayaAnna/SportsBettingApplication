package sportsbetting.service;

import sportsbetting.model.user.User;

public interface IUserService {
    User findUserByEmail(String email);
    User findUserByUserName(String userName);
    User saveUser(User user);
    User updateUser(User user);
}
