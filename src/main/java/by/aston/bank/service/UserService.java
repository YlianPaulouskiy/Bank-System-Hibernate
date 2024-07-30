package by.aston.bank.service;

import by.aston.bank.repository.UserRepository;
import by.aston.bank.service.dto.UserDto;
import by.aston.bank.service.dto.UserWriteDto;
import by.aston.bank.service.mapper.UserMapper;
import by.aston.bank.service.mapper.UserMapperImpl;
import by.aston.bank.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final UserMapper userMapper = new UserMapperImpl();

    public Optional<UserDto> findById(Long id) {
        try (var session = HibernateUtil.open()) {
            return Optional.ofNullable(
                    userMapper.toUserDto(
                            userRepository.findById(session, id).orElse(null)));
        }
    }

    public List<UserDto> findAll() {
        try (var session = HibernateUtil.open()) {
            return userRepository.findAll(session)
                    .stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    public Optional<UserDto> create(UserWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            Optional<UserDto> optUserDto = Optional.empty();
            if (writeDto.startWork() != null) {
                optUserDto = Optional.ofNullable(
                        userMapper.toUserDto(
                                userRepository.save(session, userMapper.toManagerEntity(writeDto))
                                        .orElse(null)));
            } else {
                optUserDto = Optional.ofNullable(
                        userMapper.toUserDto(
                                userRepository.save(session, userMapper.toClientEntity(writeDto))
                                        .orElse(null)));
            }
            session.getTransaction().commit();
            return optUserDto;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public Optional<UserDto> update(Long id, UserWriteDto writeDto) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var user = userRepository.findById(session, id);
            Optional<UserDto> optUserDto = Optional.empty();
            if (user.isPresent()) {
                if (user.get().getUserType().equals('M')) {
                    optUserDto = Optional.ofNullable(
                            userMapper.toUserDto(
                                    userRepository.update(session, id, userMapper.toManagerEntity(writeDto))
                                            .orElse(null)));
                } else if (user.get().getUserType().equals('C')) {
                    optUserDto = Optional.ofNullable(
                            userMapper.toUserDto(
                                    userRepository.update(session, id, userMapper.toClientEntity(writeDto))
                                            .orElse(null)));
                }
                session.getTransaction().commit();
            }
            return optUserDto;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    public boolean delete(Long id) {
        var session = HibernateUtil.open();
        try {
            session.beginTransaction();
            var deleted =  userRepository.delete(session, id);
            session.getTransaction().commit();
            return deleted;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

}
