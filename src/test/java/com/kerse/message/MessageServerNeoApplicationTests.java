package com.kerse.message;

import com.kerse.message.model.User;
import com.kerse.message.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServerNeoApplicationTests {

	@Autowired
	UserRepository userRepository;

	@Test
	public void contextLoads() {
		User user = userRepository.findByUniqueID("abb3d530-6c44-42a1-b113-44580cf195cb");
		user.setProfilePhoto("http://192.168.0.27:9091/resources/images/abb3d530-6c44-42a1-b113-44580cf195cb/profile2.jpg");
		userRepository.save(user);
	}

}
