package ru.petrowich.TestTask.models;

import javax.persistence.*;

@Entity
@Table(name = "t_parts")
public class Part {
    @Id
    @Column(name = "Part_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer partId;

    @Column(name = "Part_Name")
    private String partName;

    @Column(name = "Part_Required")
    private boolean partRequired;

    @Column(name = "Part_Amount")
    private int partAmount;

    public Part() {
    }

    public Integer getPartId() {
        return partId;
    }

    public void setPartId(Integer partId) {this.partId = partId;}

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {this.partName = partName;}

    public boolean isPartRequired() {
        return partRequired;
    }

    public void setPartRequired(boolean partRequired) {this.partRequired = partRequired;}

    public int getPartAmount() {
        return partAmount;
    }

    public void setPartAmount(int partAmount) {this.partAmount = partAmount;}

    @Override
    public String toString() {
        return "Part{" +
                "partId=" + partId +
                ", partName='" + partName + '\'' +
                ", partRequired=" + partRequired +
                ", partAmount=" + partAmount +
                '}';
    }
}
