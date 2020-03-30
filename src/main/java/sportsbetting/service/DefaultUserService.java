package sportsbetting.service;

import sportsbetting.dao.UserRepository;
import sportsbetting.model.user.Player;
import sportsbetting.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultUserService implements IUserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public DefaultUserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user instanceof Player) {
            Player userFromDb = (Player) userRepository.findById(user.getId()).get();
            userFromDb.setActive(true);
            userFromDb.setEmail(user.getEmail());
            userFromDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userFromDb.setUserName(user.getUserName());
            userFromDb.setDateOfBirth(((Player) user).getDateOfBirth());
            userFromDb.setAccountNumber(((Player) user).getAccountNumber());
            userFromDb.setCurrency(((Player) user).getCurrency());
            return userRepository.save(userFromDb);
        }
        else {
            User userFromDb = userRepository.findById(user.getId()).get();
            userFromDb.setActive(user.getActive());
            userFromDb.setEmail(user.getEmail());
            userFromDb.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userFromDb.setUserName(user.getUserName());
            return userRepository.save(userFromDb);
        }
    }

}
