package ru.petrowich.TestTask.dao;

import ru.petrowich.TestTask.models.Part;
import ru.petrowich.TestTask.utils.SessionUtil;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.util.List;

@Repository
public class PartDaoImpl extends SessionUtil implements PartDao {

    private int currentPage = 0;
    private int numberOfPages = 0;
    private int numberOfRecordsPerPage = 10;
    private String filter = "";
    private String orderBy = "";
    private boolean desc = false;

    public PartDaoImpl() {
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }

    public boolean isDesc() {
        return this.desc;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getNumberOfRecordsPerPage() {
        return numberOfRecordsPerPage;
    }

    public void setNumberOfRecordsPerPage(int numberOfRecordsPerPage) {
        this.numberOfRecordsPerPage = numberOfRecordsPerPage;
    }

    public void addPart(Part part) {
        Session session = openTransactionSession();

        session.persist(part);

        closeTransactionSession();
    }

    public void updatePart(Part part) {
        Session session = openTransactionSession();

        session.update(part);

        closeTransactionSession();
    }

    public void removePart(int id) {
        Session session = openTransactionSession();

        Part part = session.load(Part.class, id);
        if (part != null) {
            session.delete(part);
        }

        closeTransactionSession();
    }

    public Part getPartById(int id) {
        Session session = openSession();

        //Part part = session.load(Part.class, id);

        //session.load(Part.class, id) не работает
        //а такая фигня работает:
        Query query = session.createQuery("from Part where partId=" + id);
        List<Part> parts = query.list();

        //если session.load(Part.class, id) тут возвращает could not initialize proxy [ru.petrowich.TestTask.models.Part#35] - no Session
        session.close();

        return (parts.size()>0?parts.get(0):new Part());
    }

    public List<Part> findAll() {
        Session session = openSession();

        String conditions = "from Part" + (filter != null && filter.length() > 0 ? " where partName like '%" + filter + "%'" : ""); //начальные условия выборки
        String sorting = "";    //сортировка
        Query query;

        //если указано поле сортировки
        if (orderBy != null && orderBy.length() > 0) {
            sorting = " order by " + orderBy + " " + (desc ? "desc" : "asc");
        }

        //если корректно задано кол-во записей на странице, делаем запрос с пагинацией
        if (numberOfRecordsPerPage > 0) {
            if (currentPage <= 0) {              //нумерация страницы с единицы
                currentPage = 1;
            }
            //вычисляем значения для пагинации
            query = session.createQuery("select count(*) " + conditions); //кол-во строк с такими условяими
            int totalNumberOfRecords = ((Long) query.uniqueResult()).intValue();
            int firstResult = (currentPage - 1) * numberOfRecordsPerPage;                    //номер первой строки на странице
            firstResult = (totalNumberOfRecords > (firstResult) ? firstResult : 0);      //номер не больше, чем найдено записей

            numberOfPages = (totalNumberOfRecords / numberOfRecordsPerPage) + (totalNumberOfRecords % numberOfRecordsPerPage == 0 ? 0 : 1);

            query = session.createQuery(conditions + sorting); //запрос с ограничением пагинации
            query.setFirstResult(firstResult);
            query.setMaxResults(numberOfRecordsPerPage);
        } else {
            query = session.createQuery(conditions + sorting); //запрос без ограничений
        }

        List<Part> parts = query.list();

        session.close();

        return parts;
    }

    public Integer getMaxPcNumber() {
        Session session = openSession();

        String hql = "select min(partAmount) from Part where partRequired=true";
        Query query = session.createQuery(hql);
        Integer result = (Integer) query.list().get(0);

        session.close();

        return result;
    }

}