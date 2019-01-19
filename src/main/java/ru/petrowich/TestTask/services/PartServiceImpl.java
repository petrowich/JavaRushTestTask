package ru.petrowich.TestTask.services;

import ru.petrowich.TestTask.dao.PartDao;
import ru.petrowich.TestTask.dao.PartDaoImpl;
import ru.petrowich.TestTask.models.Part;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class PartServiceImpl implements PartService {
    private PartDao partDao = new PartDaoImpl();

    public PartServiceImpl() {
    }

    public void setPartDao(PartDao partDao) {
        this.partDao = partDao;
    }

    @Transactional
    public void addPart(Part part) {
        partDao.addPart(part);
    }

    @Transactional
    public void updatePart(Part part) {
        partDao.updatePart(part);
    }

    @Transactional
    public void removePart(int id) {
        partDao.removePart(id);
    }

    @Transactional
    public Part getPartById(int id) {
        return partDao.getPartById(id);
    }

    @Transactional
    public List<Part> findAll() {
        return partDao.findAll();
    }

    public int getCurrentPage() {
        return partDao.getCurrentPage();
    }

    public void setFilter(String filter) {
        partDao.setFilter(filter);
    }

    public void setOrderBy(String orderBy) {
        partDao.setOrderBy(orderBy);
    }

    public String getOrderBy(){
        return partDao.getOrderBy();
    }

    public void setDesc(String desc) {
        partDao.setDesc(desc != null && desc.toLowerCase().equals("desc"));
    }

    public boolean isDesc() {
        return partDao.isDesc();
    }

    public List<Integer> getPageList() {
        List<Integer> pageList = new ArrayList<Integer>(partDao.getNumberOfPages());
        for (int i = 1; i <= partDao.getNumberOfPages(); i++) {
            pageList.add(i);
        }
        return pageList;
    }

    public void setCurrentPage(String currentPage) {
        try {
            partDao.setCurrentPage(Integer.valueOf(currentPage));
        } catch (Exception e) {
            partDao.setCurrentPage(0);
        }
    }

    public int getNumberOfRecordsPerPage() {
        return partDao.getNumberOfRecordsPerPage();
    }

    public void setNumberOfRecordsPerPage(int numberOfRecordsPerPage) {
        partDao.setNumberOfRecordsPerPage(numberOfRecordsPerPage);
    }

    public String maxPcNumber() {
        Integer getMaxPcNumber = partDao.getMaxPcNumber();
        return (getMaxPcNumber==null?"дофига":getMaxPcNumber.toString());
    }
}
