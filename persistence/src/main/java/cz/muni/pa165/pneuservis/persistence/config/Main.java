package cz.muni.pa165.pneuservis.persistence.config;

import cz.muni.pa165.pneuservis.persistence.repository.UserClient;
import cz.muni.pa165.pneuservis.persistence.domain.User;
import cz.muni.pa165.pneuservis.persistence.repository.UserRepository;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;



//@Context(classes = PersistenceConfiguration.class)
@ComponentScan(basePackages = "cz.muni.pa165.pneuservis")
public class Main {
//    @Autowired 
//    UserRepository ure;
    
    User loser;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static void main(String[] args) {
        
    // ApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfiguration.class);  
      ApplicationContext context = new AnnotationConfigApplicationContext("cz.muni.pa165.pneuservis"); 
       
    //   UserRepository ure = context.getBean(UserRepository.class);
        Main main = new Main();
        main.runme();
        
      //  main.homo();
        UserClient userclient = context.getBean(UserClient.class);
        UserRepository ure = context.getBean(UserRepository.class);
        User pruser = new User();
        pruser.setName("chuj");
        pruser.setEmail("mail@mail.com");
        
        try {
            System.err.println("@@@MAIN@@@");
            userclient.getRepository().save(pruser);            
            userclient.getRepository().findOne(pruser.getId());
            userclient.businessMethod();
            
        } catch (Exception e) {
             main.logger.error("EXCEPTION!"+e.getMessage());
            
        }
        try {
            System.err.println("@@@SECOND@@@");
            ure.save(pruser);
            ure.findByEmail(null);
            User temp = ure.findOne(pruser.getId());
            System.err.println(temp);
            pruser.setEmail("none");
            //ure.save(pruser); //exception
            
        } catch (Exception e) {
            main.logger.error("EXCEPTION!"+e.getMessage());
            
        }
      
    }
    @Loggable
    public void runme (){
        User pruser = new User();
        pruser.setName("chuj");
        pruser.setEmail("mail@mail.com");
      //  ure.save(pruser);
    }
    
}
