package com.ysy.payment.sdk.qrcode;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ysy.payment.sdk.dto.ExternalQrcodeVO;
import com.ysy.payment.sdk.util.HttpsClientUtil;
import com.ysy.payment.sdk.util.JSONUtils;
import com.ysy.payment.sdk.util.ReflectUtils;
import com.ysy.payment.sdk.util.SignUtil;

public class QrcodeController {

	static Logger log = LoggerFactory.getLogger(QrcodeController.class);
	
	//下单url
	private String tran_url="http://120.24.13.203:9001/services/pay/gateway/api/backTransReq";
	
	//查询订单url
	private String query_url="http://120.24.13.203:9001/services/pay/gateway/api/queryTran";
	
	//加签密钥
	private String merchKey = "83763B6487FB8E910A5BB1ADAB2732D6";
	
	/**
	 * 发起订单交易
	 * @return
	 * @throws Exception 
	 */
	public String backTransRequest() throws Exception{
		ExternalQrcodeVO qrcodeVO = new ExternalQrcodeVO();
		qrcodeVO.setMerchno("200440348120003");
		qrcodeVO.setGoodsName("安致兰德");
		qrcodeVO.setTraceno("20180112155856");
		qrcodeVO.setAmount(new BigDecimal(1).setScale(2).doubleValue());
		qrcodeVO.setCallbackUrl("");
		qrcodeVO.setNotifyUrl("");
		qrcodeVO.setPayType(2);
		qrcodeVO.setAttach("");
		
		Map<String, String> param = ReflectUtils.convertToMaps(qrcodeVO);
		String sign = SignUtil.genSign(merchKey, SignUtil.createLinkString(SignUtil.paraFilter(param)));
		qrcodeVO.setSign(sign);
		
		String postStr = (String)JSONUtils.obj2json(qrcodeVO);
		log.info("请求参数:{}",postStr);
		//HttpsClientUtil.sendRequest(tran_url, postStr, "application/json");
		
		String result = HttpsClientUtil.sendRequest(tran_url, postStr, "application/json");
		log.info("响应结果:{}",result);
		
		return result;
	}
	
	/**
	 * 发起小程序支付订单交易
	 * @return
	 * @throws Exception 
	 */
	public String miniTransRequest() throws Exception{
		ExternalQrcodeVO qrcodeVO = new ExternalQrcodeVO();
		qrcodeVO.setMerchno("200440357220002");
		qrcodeVO.setTraceno("TL"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		qrcodeVO.setAmount(new BigDecimal(1).setScale(2).doubleValue());
		qrcodeVO.setOpenid("ohgYk0Zxx_BXJGYAgLCP79xGYgCc");
		qrcodeVO.setPayType(2);
		
		Map<String, String> param = ReflectUtils.convertToMaps(qrcodeVO);
		String sign = SignUtil.genSign("410502F860CCAC24A971771CB9B1CD27", SignUtil.createLinkString(SignUtil.paraFilter(param)));
		qrcodeVO.setSign(sign);
		
		String postStr = (String)JSONUtils.obj2json(qrcodeVO);
		log.info("请求参数:{}",postStr);
		
		String result = HttpsClientUtil.sendRequest(tran_url, postStr, "application/json");
		log.info("响应结果:{}",result);
		
		return result;
	}
	
	/**
	 * 发起查询订单交易
	 * @return
	 * @throws Exception 
	 */
	public String queryTransRequest() throws Exception{
		ExternalQrcodeVO qrcodeVO = new ExternalQrcodeVO();
		qrcodeVO.setMerchno("200541100000461");
		qrcodeVO.setTraceno("20180112155854");
		
		Map<String, String> param = ReflectUtils.convertToMaps(qrcodeVO);
		String sign = SignUtil.genSign(merchKey, SignUtil.createLinkString(SignUtil.paraFilter(param)));
		qrcodeVO.setSign(sign);
		
		String postStr = (String)JSONUtils.obj2json(qrcodeVO);
		log.info("请求参数:{}",postStr);
		
		String result = HttpsClientUtil.sendRequest(query_url, postStr, "application/json");
		log.info("响应结果:{}",result);
		
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		new QrcodeController().miniTransRequest();
		//new TranController().queryTransRequest();
	}
}
