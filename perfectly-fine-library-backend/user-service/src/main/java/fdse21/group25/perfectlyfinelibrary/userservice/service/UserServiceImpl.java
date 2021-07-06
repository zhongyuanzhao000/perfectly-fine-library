package fdse21.group25.perfectlyfinelibrary.userservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fdse21.group25.perfectlyfinelibrary.common.dto.UserDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.userservice.client.PaymentClient;
import fdse21.group25.perfectlyfinelibrary.userservice.dto.PaymentRequestDto;
import fdse21.group25.perfectlyfinelibrary.userservice.entity.User;
import fdse21.group25.perfectlyfinelibrary.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PaymentClient paymentClient;

    public UserServiceImpl(UserRepository userRepository, PaymentClient paymentClient) {
        this.userRepository = userRepository;
        this.paymentClient = paymentClient;
    }

    @Override
    public User addNewUser(UserDto user) {
        return userRepository.save(new User(user.getUsername(), user.getRole()));
    }

    @Override
    public User findUserByUsername(String username) throws NotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new NotFoundException(username + " not found"));
    }

    @Override
    public List<User> findUsers(boolean isFined) {
        if (isFined) {
            return userRepository.findAllByFineGreaterThan(0);
        } else {
            return userRepository.findAll();
        }
    }

    @Override
    public User resetCredit(String username) throws NotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new NotFoundException(username + " not found"));
        user.setCredit(100);
        return userRepository.save(user);
    }

    @Override
    public User payFine(String username) throws NotFoundException {
        User user = userRepository.findById(username).orElseThrow(() -> new NotFoundException(username + " not found"));
        String INVOKE_ID = "se2021_25";
        PaymentRequestDto payment = new PaymentRequestDto(INVOKE_ID, username, user.getFine());
        paymentClient.pay(payment);
        user.setFine(user.getFine() - payment.getAmount());
        return userRepository.save(user);
    }
}