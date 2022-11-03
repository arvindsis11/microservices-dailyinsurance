package com.cognizant.walletmicroservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.walletmicroservice.exception.AuthorizationException;
import com.cognizant.walletmicroservice.feign.AuthorizeClient;
import com.cognizant.walletmicroservice.model.Transaction;
import com.cognizant.walletmicroservice.model.Wallet;
import com.cognizant.walletmicroservice.service.WalletService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/wallet")
public class WalletController {

	@Autowired
	WalletService walletService;

	@Autowired
	AuthorizeClient authorizeClient;

	private static final Logger logger = LoggerFactory.getLogger(WalletController.class);

	//for adding balance to the wallet
	@PostMapping("/add-wallet-balance")
	@ApiOperation(notes = "returns wallet object, details", value = "display the wallet")
	public Wallet addWallet(@RequestBody Wallet wobj, @RequestHeader("Authorization") String token)
			throws AuthorizationException {

		if (authorizeClient.authorize(token)) {
			logger.debug("Create Wallet");
			return walletService.createWallet(wobj);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	// used for keeping transaction history
	@GetMapping("/view-all-wallets/{user}")
	@ApiOperation(notes = "returns wallet object, details", value = "display the wallet")
	public List<Wallet> viewWalletes(@RequestHeader("Authorization") String token,@PathVariable(value = "user") String user) throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create Policy");
			return walletService.findAllWallets(user);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	// deposit and withdrawal
	@GetMapping("/total-balance/{user}")
	@ApiOperation(notes = "returns wallet object, details", value = "display the wallet")
	public double getTotalBalance(@RequestHeader("Authorization") String token,@PathVariable(value="user") String user) throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("get total balance");
			return walletService.getTotalBalance(user);
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}
	}

	//used by policy microservice
	@PostMapping("/policy-balance")
	@ApiOperation(notes = "returns wallet object, details", value = "display the wallet")
	public double policyWithdraw1(@RequestHeader("Authorization") String token, @RequestBody Transaction transObj) throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create Policy");
			return walletService.policyWithdraw(transObj.getAmount(),transObj.getUsername());
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}

	}
	//used by process-claim microservice
	@PostMapping("/claim-balance")
	@ApiOperation(notes = "returns wallet object, details", value = "display the wallet")
	public double claimDeposit(@RequestHeader("Authorization") String token, @RequestBody Transaction transObj) throws AuthorizationException {
		if (authorizeClient.authorize(token)) {
			logger.debug("Create Policy");
			return walletService.claimDeposit(transObj.getAmount(),transObj.getUsername());
		} else {
			logger.error("Access Denied");
			throw new AuthorizationException("Access Denied");
		}

	}
	//use in case of  health-check
//	@GetMapping("/health-check")
//	public ResponseEntity<?> healthCheck(){
//		return new ResponseEntity<>("Authorization Working",HttpStatus.OK);
//	}

}
