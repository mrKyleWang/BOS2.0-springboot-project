package top.kylewang.bos.domain.transit;

import javax.persistence.*;
import java.util.Date;

/**
 * @description: 签收信息
 */
@Entity
@Table(name = "T_SIGN_INFO")
public class SignInfo {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id;

	@Column(name = "C_SIGN_NAME")
	private String signName;

	@Column(name = "C_SIGN_TIME")
	private Date signTime;

	@Column(name = "C_SIGN_TYPE")
	private String signType;

	@Column(name = "C_ERROR_REMARK")
	private String errorRemark;

	@Column(name = "C_DESCRIPTION")
	private String description; // 描述

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Date getSignTime() {
		return signTime;
	}

	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getErrorRemark() {
		return errorRemark;
	}

	public void setErrorRemark(String errorRemark) {
		this.errorRemark = errorRemark;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
