package ru.sberbank.coursework.demo.dao;

import com.google.common.eventbus.DeadEvent;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sberbank.coursework.demo.domain.Client;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClientDao {
    private final SessionFactory sessionFactory;

    public ClientDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean create(Client client) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Insert client info fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Client> getAllClients(){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List<Client> allClients = session.createQuery("from Client").list();
            transaction.commit();
            return allClients;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Get all client info fail: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Client getClient(int clientId){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            transaction.commit();
            return client;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Get all client info fail: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateClient(Client client){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(client);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Update client info fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteClient(int clientId){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Client client = session.get(Client.class, clientId);
            session.delete(client);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Delete client fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}
