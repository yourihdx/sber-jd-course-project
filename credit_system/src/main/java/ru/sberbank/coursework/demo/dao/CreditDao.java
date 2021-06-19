package ru.sberbank.coursework.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sberbank.coursework.demo.domain.Credit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CreditDao {
    private final SessionFactory sessionFactory;

    public CreditDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean createCredit(Credit credit) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(credit);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Insert credit info fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public List<Credit> getAllCredits(){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List allCredits = session.createQuery("from Credit").list();
            transaction.commit();
            return allCredits;
        } catch (Exception e) {
//            if (Objects.nonNull(transaction)) {
//                transaction.rollback();
//            }
            System.out.println("Get all credit info fail: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Credit getCredit(int creditId){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Credit credit = session.get(Credit.class, creditId);
            transaction.commit();
            return credit;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Get all credit info fail: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCredit(Credit credit){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(credit);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Update credit info fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCredit(int creditId){
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Credit credit = session.get(Credit.class, creditId);
            session.delete(credit);
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
