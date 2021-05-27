package persistence;

import javax.persistence.*;
import java.util.List;

public class DaoFactory {
    private final EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("fx.persistence");
    private final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public User findUserByUsername( String name ){
        try {
            return entityManager.createQuery("from User u where u.username ='"+name+"'", User.class).getSingleResult();
        }catch (NoResultException e){
            return null ;
        }
    }
    public List<MessageToSend> messageToSends(String username){
        User user = findUserByUsername(username);
        if (user != null) {
            try {
              return  user.getMessagesToSend();
              //return entityManager.createQuery("from MessageToSend mts where mts.receiver='"
                //       +user+"'",MessageToSend.class).getResultList();
            }catch (Exception e){
                return null ;
            }
        } else return null ;

    }
    public void deleteMessage(MessageToSend message){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(message);
            entityManager.getTransaction().commit();
        }catch (Exception e){}
    }
    public boolean addUser(String username ){
        User user = findUserByUsername(username);
        if (user == null) {
            EntityTransaction tr = entityManager.getTransaction();
            tr.begin();
            entityManager.persist(new User(username,null));
            tr.commit();
            return false;
        }
        else return true;
    }
    public void registerMsgForAUser(User receiver ,String sender, String msg){
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(new MessageToSend(receiver,sender,msg));
            entityManager.getTransaction().commit();
        }catch (Exception e){
            return;
        }
    }
}
