package ru.sberbank.coursework.bankanswer.data;

import java.util.*;

public class AnswerList {
    private static AnswerList instance = new AnswerList();

    private HashMap<String, Loan_Answer> data;

    public static AnswerList getInstance() {
        return instance;
    }

    private AnswerList() {
        data = new LinkedHashMap<>(10, (float) 0.9, false);
    }

    /**
     * Добавление записи в Map БД.
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @param la
     */
    public void add(Loan_Answer la) {
        synchronized (instance) {
            data.put(la.getLoan_request().getId(), la);
        }
    }

    /**
     * Чтение одного элемента из Map БД
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @param id - идентификатор записи
     * @return
     */
    public Loan_Answer getRec(String id) {
        Loan_Answer resStr;
        synchronized (instance) {
            resStr = data.get(id);
        }
        return resStr;
    }

    /**
     * Возвращаем массив всех значений
     * Формируем массив и заполняем его значениями из Map БД
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @return
     */
    public List<Loan_Answer> list() {
        List<Loan_Answer> ret = null;
        synchronized (instance) {
            ret = new ArrayList<Loan_Answer>(data.values());
        }
        Collections.reverse(ret);
        return ret;
    }

    /**
     * Возвращаем массив последних значений
     * Формируем массив и заполняем его значениями из Map БД
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @param size - количество записей
     * @return
     */
    public List<Loan_Answer> list(int size) {
        List<Loan_Answer> ret = null;
        List<Loan_Answer> ret1 = new ArrayList<Loan_Answer>();
        int mysize;
        synchronized (instance) {
            ret = new ArrayList<Loan_Answer>(data.values());
            mysize = data.size() < size ? ret.size() : size;
        }
        Collections.reverse(ret);
        if (mysize > 1) {
            for (int i = 0; i < mysize; ++i) {
                ret1.add(ret.get(i));
            }
        } else {
            ret1 = ret;
        }
        return ret1;
    }

    /**
     * Возвращаем количество записей
     *
     * @return
     */
    public int size() {
        int ret;
        synchronized (instance){
            ret = data.size();
        }
        return ret;
    }
}
