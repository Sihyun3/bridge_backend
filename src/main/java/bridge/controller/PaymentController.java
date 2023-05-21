package bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.dto.UserDto;
import bridge.mapper.PaymentMapper;
import bridge.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentMapper paymentMapper;

	/* 공지 리스트 */
	@GetMapping("/api/payment/list")
	public ResponseEntity<List<PaymentDto>> paymentList() throws Exception {
		List<PaymentDto> paymentList = paymentService.paymentList();
		if (paymentList != null && paymentList.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(paymentList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	};

	//보유포인트
	@GetMapping("/api/payment/detail/{userId}")
    public ResponseEntity<Integer> paymentDetail(@PathVariable("userId") String userId) throws Exception {
        int abc = paymentService.paymentDetail(userId);
        return ResponseEntity.status(HttpStatus.OK).body(abc);
    }
	
	//결제 진행
	@PostMapping("/api/doPayment/{producer}")
    public void doPayment(@PathVariable("producer") String producer, @RequestBody PaymentDto paymentDto) throws Exception {
		
		paymentDto.setProducer(producer);

		paymentService.doPayment(paymentDto);		// 결제 진행 + 유저 포인트 업데이트 수행
		paymentMapper.insertPayment();				// 결제 내역 pay_list 에 기록
		paymentMapper.updatePartnerMoney();			// 결제 내역 partner_detail 에 기록
    }
	
	//관리자용 결제내역 (모든 유저/ 모든 거래)
	@GetMapping("/api/payListAll")
	public ResponseEntity<List<PayListDto>> payListAll() throws Exception {
		List<PayListDto> payListDto = paymentService.payListAll();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
		//회원간 거래내역
	@GetMapping("/api/payList/deal")
	public ResponseEntity<List<PayListDto>> payListDeal() throws Exception {
		List<PayListDto> payListDto = paymentMapper.payListDeal();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
		//충전 내역
	@GetMapping("/api/payList/charge")
	public ResponseEntity<List<PayListDto>> payListCharge() throws Exception {
		List<PayListDto> payListDto = paymentMapper.payListCharge();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
	
	//회원용 결제내역
	@GetMapping("/api/payList/{userId}")
	public ResponseEntity<List<PayListDto>> payListUser(@PathVariable("userId") String userId) throws Exception {
		List<PayListDto> payListDto = paymentService.payList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
	
	@GetMapping("/api/pay/deal/{userId}")
	public ResponseEntity<List<PayListDto>> payDeal(@PathVariable("userId") String userId) throws Exception {
		List<PayListDto> payListDto = paymentMapper.payDeal(userId);
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
		//충전 내역
	@GetMapping("/api/pay/charge/{userId}")
	public ResponseEntity<List<PayListDto>> payCharge(@PathVariable("userId") String userId) throws Exception {
		List<PayListDto> payListDto = paymentMapper.payCharge(userId);
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
	
    
}