package ru.petrowich.TestTask.services;

import ru.petrowich.TestTask.models.Part;

import java.util.List;

public interface PartService {
    void addPart(Part part);

    void updatePart(Part part);

    void removePart(int id);

    Part getPartById(int id);

    List<Part> findAll();

    List<Integer> getPageList();

    void setCurrentPage(String currentPage);

    int getNumberOfRecordsPerPage();

    void setNumberOfRecordsPerPage(int numberOfRecordsPerPage);

    int getCurrentPage();

    void setFilter(String filter);

    void setOrderBy(String orderBy);

    String getOrderBy();

    void setDesc(String desc);

    boolean isDesc();

    String maxPcNumber();
}
