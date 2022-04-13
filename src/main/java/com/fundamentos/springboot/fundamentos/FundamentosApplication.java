package com.fundamentos.springboot.fundamentos;

import com.fundamentos.springboot.fundamentos.bean.MyBean;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentos.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentos.springboot.fundamentos.component.ComponentDepenedency;
import com.fundamentos.springboot.fundamentos.entity.User;
import com.fundamentos.springboot.fundamentos.pojo.UserPojo;
import com.fundamentos.springboot.fundamentos.repository.UserRepository;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);

	private ComponentDepenedency componentDepenedency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;

	public FundamentosApplication(@Qualifier("componentImplementTwo") ComponentDepenedency componentDepenedency, MyBean myBean,
								  MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties,
								  UserPojo userPojo, UserRepository userRepository) {
		this.componentDepenedency = componentDepenedency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//ejemplosAnteriores();
		saveUserInDataBase();
		getInformationJpqlFromUser();
	}

	private void saveUserInDataBase(){
		User user1 = new User("Carlos", "carlos@gmail.com", LocalDate.of(1999, 6, 6));
		User user2 = new User("Carlos", "andres@gmail.com", LocalDate.of(1999, 7, 7));
		User user3 = new User("Obando", "obando@gmail.com", LocalDate.of(1999, 8, 8));
		User user4 = new User("Casiano", "casiano@gmail.com", LocalDate.of(1999, 9, 9));
		User user5 = new User("Elvira", "elvira@gmail.com", LocalDate.of(1999, 10, 10));
		User user6 = new User("Lavado", "lavado@gmail.com", LocalDate.of(1999, 11, 11));
		User user7 = new User("Daniel", "daniel@gmail.com", LocalDate.of(1999, 12, 12));
		User user8 = new User("Steven", "steven@gmail.com", LocalDate.of(1999, 1, 13));
		User user9 = new User("Garzon", "garzon@gmail.com", LocalDate.of(1999, 2, 14));
		User user10 = new User("Luque", "luque@gmail.com", LocalDate.of(1999, 3, 15));

		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);
		list.forEach(userRepository::save);
		//userRepository.saveAll(list);

	}

	private void getInformationJpqlFromUser(){
		LOGGER.info("El usuario con el metodo findByUserEmail: " + userRepository.findByUserEmail("elvira@gmail.com")
				.orElseThrow(()-> new RuntimeException("No existe el usuario")));

		userRepository.findAndSort("C", Sort.by("id").descending())
				.forEach(LOGGER::info);

		userRepository.findByName("Carlos")
				.forEach(user -> LOGGER.info("El usuario con query method: " + user));

		LOGGER.info("El usuario con query method findByEmailAndName: " + userRepository.findByEmailAndName("andres@gmail.com","Carlos")
				.orElseThrow(()-> new RuntimeException("Usuario no encontrado")));
	}

	private void ejemplosAnteriores(){
		componentDepenedency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword()+"-"+userPojo.getAge());
		try{
			int value = 10/0;
			LOGGER.debug("Mi valor: "+value);
		}catch (Exception e) {
			LOGGER.error("Esto es un error al dividir por cero", e);
		}
	}
}
