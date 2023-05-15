package bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.dto.UserDto;
import bridge.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentMapper paymentMapper;
    
    @Override
    public List<PaymentDto> paymentList() throws Exception {
        return paymentMapper.paymentList();
    }

    @Override
    public void insertPayment(PaymentDto paymentDto) throws Exception {
        paymentMapper.insertPayment(paymentDto);
        
    }

    @Override
    public int paymentDetail(String userId) throws Exception {
        return paymentMapper.paymentDetail(userId);
    }

	@Override
	public void doPayment(PaymentDto paymentDto) {
		paymentMapper.doPayment(paymentDto);
		paymentMapper.updatePoint(paymentDto.getUsepoint());
	}

	@Override
	public List<PayListDto> payListAll() {
		return paymentMapper.payListAll();
	}

	@Override
	public List<PayListDto> payList(String userId) {
		return paymentMapper.payList(userId);
	}

}