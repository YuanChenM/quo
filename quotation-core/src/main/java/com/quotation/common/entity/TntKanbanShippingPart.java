package com.quotation.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * The persistent class for the TNT_KANBAN_SHIPPING_PARTS database table.
 * 
 */
@Entity(name = "TNT_KANBAN_SHIPPING_PARTS")
public class TntKanbanShippingPart extends com.quotation.core.base.BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "TNT_KANBAN_SHIPPING_PARTS_KSPID_GENERATOR",
        sequenceName = "SEQ_TNT_KANBAN_SHIPPING_PARTS",
        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
        generator = "TNT_KANBAN_SHIPPING_PARTS_KSPID_GENERATOR")
    @Column(name = "KSP_ID")
    private Integer kspId;

    @Column(name = "CREATED_BY")
    private Integer createdBy;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "KANBAN_QTY")
    private BigDecimal kanbanQty;

    @Column(name = "PARTS_ID")
    private Integer partsId;

    private BigDecimal qty;

    private BigDecimal spq;

    @Column(name = "UPDATED_BY")
    private Integer updatedBy;

    @Column(name = "UPDATED_DATE")
    private Timestamp updatedDate;

    @Column(name = "\"VERSION\"")
    private Integer version;

    @Column(name = "KANBAN_SHIPPING_ID")
    private Integer kanbanShippingId;

    public TntKanbanShippingPart() {}

    public Integer getKspId() {
        return this.kspId;
    }

    public void setKspId(Integer kspId) {
        this.kspId = kspId;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getKanbanQty() {
        return this.kanbanQty;
    }

    public void setKanbanQty(BigDecimal kanbanQty) {
        this.kanbanQty = kanbanQty;
    }

    public Integer getPartsId() {
        return this.partsId;
    }

    public void setPartsId(Integer partsId) {
        this.partsId = partsId;
    }

    public BigDecimal getQty() {
        return this.qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getSpq() {
        return this.spq;
    }

    public void setSpq(BigDecimal spq) {
        this.spq = spq;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getKanbanShippingId() {
        return this.kanbanShippingId;
    }

    public void setKanbanShippingId(Integer kanbanShippingId) {
        this.kanbanShippingId = kanbanShippingId;
    }

}