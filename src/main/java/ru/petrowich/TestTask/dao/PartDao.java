package ru.petrowich.TestTask.dao;

import ru.petrowich.TestTask.models.Part;

import java.util.List;

public interface PartDao {

    void addPart(Part part);

    void updatePart(Part part);

    void removePart(int id);

    Part getPartById(int id);

    List<Part> findAll();

    int getNumberOfPages();

    int getCurrentPage();

    void setCurrentPage(int currentPage);

    int getNumberOfRecordsPerPage();

    void setNumberOfRecordsPerPage(int numberOfRecordsPerPage);

    void setFilter(String filter);

    void setOrderBy(String orderBy);

    String getOrderBy();

    void setDesc(boolean desc);

    boolean isDesc();

    Integer getMaxPcNumber();
}
