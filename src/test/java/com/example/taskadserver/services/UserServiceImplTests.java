package com.example.taskadserver.services;
import com.example.taskadserver.models.User;
import com.example.taskadserver.repositories.contracts.UserRepository;
import com.example.taskadserver.services.contracts.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
public class UserServiceImplTests {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void getAll_ShouldReturnListOfUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(new User(1L, "John", "John", "John","John"), new User(2L, "Jane","Jane","Jane","Jane"));
        when(userRepository.getAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAll();

        // Assert
        assertEquals(expectedUsers, actualUsers);
        verify(userRepository, times(1)).getAll();
    }

    @Test
    void getUserById_ShouldReturnUserWithMatchingId() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User(userId, "John", "John","John","John");
        when(userRepository.getById(userId)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.getUserById(userId);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).getById(userId);
    }

    @Test
    void getUserByUsername_ShouldReturnUserWithMatchingUsername() {
        // Arrange
        String username = "John";
        User expectedUser = new User(1L, "Jane", "Jane",username, "Jane");
        when(userRepository.getByName(username)).thenReturn(expectedUser);

        // Act
        User actualUser = userService.getUserByUsername(username);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository, times(1)).getByName(username);
    }

}
