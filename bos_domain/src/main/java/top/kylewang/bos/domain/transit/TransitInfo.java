package top.kylewang.bos.domain.transit;

import top.kylewang.bos.domain.take_delivery.WayBill;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 运输配送信息
 */
@Entity
@Table(name = "T_TRANSIT_INFO")
public class TransitInfo {
	@Id
	@GeneratedValue
	@Column(name = "C_ID")
	private Integer id;

	@OneToOne
	@JoinColumn(name = "C_WAYBILL_ID")
	private WayBill wayBill;

	@OneToMany
	@JoinColumn(name = "C_TRANSIT_INFO_ID")
	@OrderColumn(name = "C_IN_OUT_INDEX")
	private List<InOutStorageInfo> inOutStorageInfos = new ArrayList<InOutStorageInfo>();

	@OneToOne
	@JoinColumn(name = "C_DELIVERY_INFO_ID")
	private DeliveryInfo deliveryInfo;

	@OneToOne
	@JoinColumn(name = "C_SIGN_INFO_ID")
	private SignInfo signInfo;

	@Column(name = "C_STATUS")
	// 出入库中转、到达网点、开始配送、正常签收、异常
	private String status;

	@Column(name = "C_OUTLET_ADDRESS")
	private String outletAddress;

	@Transient
	public String getTransferInfo(){
		StringBuffer stringBuffer = new StringBuffer();
		// 出入库信息
		for (InOutStorageInfo inOutStorageInfo : inOutStorageInfos) {
			stringBuffer.append(inOutStorageInfo.getDescription()+"<br/>");
		}
		// 配送信息
		if(deliveryInfo!=null){
			stringBuffer.append(deliveryInfo.getDescription()+"<br/>");
		}
		// 签收信息
		if(signInfo!=null){
			stringBuffer.append(signInfo.getDescription()+"<br/>");
		}
		return stringBuffer.toString();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public WayBill getWayBill() {
		return wayBill;
	}

	public void setWayBill(WayBill wayBill) {
		this.wayBill = wayBill;
	}

	public List<InOutStorageInfo> getInOutStorageInfos() {
		return inOutStorageInfos;
	}

	public void setInOutStorageInfos(List<InOutStorageInfo> inOutStorageInfos) {
		this.inOutStorageInfos = inOutStorageInfos;
	}

	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public SignInfo getSignInfo() {
		return signInfo;
	}

	public void setSignInfo(SignInfo signInfo) {
		this.signInfo = signInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOutletAddress() {
		return outletAddress;
	}

	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}

}
