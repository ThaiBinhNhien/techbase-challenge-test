package test.challenge;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import test.challenge.entity.Role;
import test.challenge.entity.User;
import test.challenge.repository.RoleRepository;
import test.challenge.repository.UserRepository;
import test.challenge.request.UserRequest;
import test.challenge.service.UserService;
import test.challenge.service.impl.UserServiceImpl;

public class TestChallengeTechbaseApplicationTests {

	@Mock
	private UserRepository mockUserRepository;

	@Mock
	private RoleRepository mockRoleRepository;

	@Mock
	private BCryptPasswordEncoder mockBCryptPasswordEndcoder;


	private UserService userServiceTest;
	private User user;


	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		initMocks(this);
		userServiceTest = new UserServiceImpl(mockUserRepository, mockBCryptPasswordEndcoder);
		
		user = User.builder()
				.userName("nhientb1")
        		.password("Abc@123456")
                .build();
		
		Mockito.when(mockUserRepository.save(any()))
        .thenReturn(user);
		Mockito.when(mockUserRepository.findByUserName(anyString()))
        .thenReturn(user);

	}

	@Test
	public void testSaveUser() throws Exception {
		// Setup
		//final String userName = "nhientb";


		//User result = userServiceTest.saveUser(userRequest);

		// Verify the results
		//assertEquals(userName, result.getUserName());
	}

	@Test
	public void testGetRole() throws Exception {
		final String roleName = "ADMIN";

		Role roleResult = mockRoleRepository.findByRole(roleName);

		assertEquals(roleName, roleResult.getRole());
	}

}
