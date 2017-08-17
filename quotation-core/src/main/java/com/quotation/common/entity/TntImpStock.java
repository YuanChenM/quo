package com.quotation.common.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the TNT_IMP_STOCK database table.
 * 
 */
@Entity(name="TNT_IMP_STOCK")
public class TntImpStock extends com.quotation.core.base.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TNT_IMP_STOCK_IMPSTOCKID_GENERATOR", sequenceName="SEQ_TNT_IMP_STOCK", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TNT_IMP_STOCK_IMPSTOCKID_GENERATOR")
	@Column(name="IMP_STOCK_ID")
	private Integer impStockId;

	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Column(name="CREATED_DATE")
	private Timestamp createdDate;

	@Column(name="DELIVERED_QTY")
	private BigDecimal deliveredQty;

	@Column(name="IMP_IN_RACK_QTY")
	private BigDecimal impInRackQty;

	@Column(name="IMP_STOCK_QTY")
	private BigDecimal impStockQty;

	@Column(name="NG_QTY")
	private BigDecimal ngQty;

	@Column(name="OFFICE_ID")
	private Integer officeId;

	@Column(name="ONHOLD_QTY")
	private BigDecimal onholdQty;

	@Column(name="PARTS_ID")
	private Integer partsId;

	@Column(name="SUPPLIER_ID")
	private Integer supplierId;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Column(name="UPDATED_DATE")
	private Timestamp updatedDate;

	@Column(name="\"VERSION\"")
	private Integer version;

	@Column(name="WHS_ID")
	private Integer whsId;

	@Column(name="WHS_STOCK_QTY")
	private BigDecimal whsStockQty;

	public TntImpStock() {
	}

	public Integer getImpStockId() {
		return this.impStockId;
	}

	public void setImpStockId(Integer impStockId) {
		this.impStockId = impStockId;
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

	public BigDecimal getDeliveredQty() {
		return this.deliveredQty;
	}

	public void setDeliveredQty(BigDecimal deliveredQty) {
		this.deliveredQty = deliveredQty;
	}

	public BigDecimal getImpInRackQty() {
		return this.impInRackQty;
	}

	public void setImpInRackQty(BigDecimal impInRackQty) {
		this.impInRackQty = impInRackQty;
	}

	public BigDecimal getImpStockQty() {
		return this.impStockQty;
	}

	public void setImpStockQty(BigDecimal impStockQty) {
		this.impStockQty = impStockQty;
	}

	public BigDecimal getNgQty() {
		return this.ngQty;
	}

	public void setNgQty(BigDecimal ngQty) {
		this.ngQty = ngQty;
	}

	public Integer getOfficeId() {
		return this.officeId;
	}

	public void setOfficeId(Integer officeId) {
		this.officeId = officeId;
	}

	public BigDecimal getOnholdQty() {
		return this.onholdQty;
	}

	public void setOnholdQty(BigDecimal onholdQty) {
		this.onholdQty = onholdQty;
	}

	public Integer getPartsId() {
		return this.partsId;
	}

	public void setPartsId(Integer partsId) {
		this.partsId = partsId;
	}

	public Integer getSupplierId() {
		return this.supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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

	public Integer getWhsId() {
		return this.whsId;
	}

	public void setWhsId(Integer whsId) {
		this.whsId = whsId;
	}

	public BigDecimal getWhsStockQty() {
		return this.whsStockQty;
	}

	public void setWhsStockQty(BigDecimal whsStockQty) {
		this.whsStockQty = whsStockQty;
	}

}