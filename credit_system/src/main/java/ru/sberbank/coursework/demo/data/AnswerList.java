package ru.sberbank.coursework.demo.data;


import java.util.ArrayDeque;

public class AnswerList {
    private static AnswerList instance = new AnswerList();

    private ArrayDeque <AnswerData> data;

    public static AnswerList getInstance() {
        return instance;
    }

    private AnswerList() {
        data = new ArrayDeque<AnswerData>();
    }

    /**
     * Добавление записи в Map БД.
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @param la
     */
    public void add(AnswerData la) {
        synchronized (instance) {
            data.push(la);
        }
    }

    /**
     * Чтение одного элемента из Map БД
     * Map не является потокозащищенной, поэтому используем синхронизацию
     *
     * @param id - идентификатор записи
     * @return
     */
    public AnswerData getRec() {
        AnswerData resStr;
        synchronized (instance) {
            resStr = data.pop();
        }
        return resStr;
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
