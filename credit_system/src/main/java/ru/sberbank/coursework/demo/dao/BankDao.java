package ru.sberbank.coursework.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sberbank.coursework.demo.domain.Bank;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BankDao {
    private final SessionFactory sessionFactory;

    public BankDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean createBank(Bank bank) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(bank);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Insert Bank fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List getAllBanks() {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            List allBanks = session.createQuery("from Bank").list();
            transaction.commit();
            return allBanks;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Get all Banks info fail: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Bank getBank(int banktId) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Bank bank = session.get(Bank.class, banktId);
            transaction.commit();
            return bank;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Get bank info fail: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateBank(Bank bank) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(bank);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            System.out.println("Update bank info fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBank(int bankId) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Bank bank = session.get(Bank.class, bankId);
            session.delete(bank);
            transaction.commit();
            return true;
        } catch (Exception e) {
//            if (Objects.nonNull(transaction)) {
//                transaction.rollback();
//            }
            System.out.println("Delete client fail: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
