package com.onlinebanking.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.onlinebanking.models.Account;
import com.onlinebanking.models.AccountAppModel;
import com.onlinebanking.models.User;
import com.onlinebanking.models.UserAppModel;
import com.onlinebanking.models.UserRegistrationModel;
import com.onlinebanking.models.UserRequest;

public class ValidationHelper {

	public static int generateRandomNumber(int min, int max) {
		Random randomGenerator = new Random();
		int randomNum = randomGenerator.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static Response validateAmount(String amt) {
		try {
			double amountD = Double.parseDouble(amt);
			if (amountD < 1) {
				return new Response("error", "Amount should be greater than 0.");
			}
		} catch (NumberFormatException e) {
			return new Response("error", "Amount is not valid.");
		}
		return new Response("success", "Amount is valid");
	}

	// Properties not set here are assumed to be never editable.
	// Please refrain from adding them here.
	public static User getUserFromUserAppModel(UserAppModel a, User u) {
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		return u;
	}

	public static User getUserFromUserRegistrationModel(UserRegistrationModel a, User u) {
		u.setEmailId(a.getEmailId());
		u.setFname(a.getFname());
		u.setLname(a.getLname());
		u.setDob(a.getDob());
		u.setAddress(a.getAddress());
		u.setCity(a.getCity());
		u.setState(a.getState());
		u.setPhoneno(a.getPhoneno());
		u.setZipcode(a.getZipcode());
		u.setPassword(a.getPassword());
		u.setSsn(a.getSsn());
		u.setRole(a.getRole());
		u.setQues1(a.getQues1());
		u.setQues2(a.getQues2());
		u.setQues3(a.getQues3());
		u.setAnswer1(a.getAnswer1());
		u.setAnswer2(a.getAnswer2());
		u.setAnswer3(a.getAnswer3());
		return u;
	}

	// Properties not set here are assumed to be never editable.
	// Please refrain from adding them here.
	public static Account getAccountFromAccountAppModel(AccountAppModel acc,
			Account a) {
		a.setAmount(acc.getAmount());
		return a;
	}

	public static List<AccountAppModel> getAccountAppModelListFromAccountList(
			List<Account> acclist) {
		List<AccountAppModel> list = new ArrayList<AccountAppModel>();

		for (Account acc : acclist) {
			list.add(new AccountAppModel(acc));
		}

		return list;
	}
	
	public static Response validateUserRequest(UserRequest userRequest, User toUser)
	{
		try
		{
			if (userRequest.getFname() == null || userRequest.getFname() == "") {
				return new Response("error", "first name not entered");
			}

			if (userRequest.getLname() == null || userRequest.getLname() == "") {
				return new Response("error", "last name not entered");
			}
			if (userRequest.getEmailId() == null
					|| userRequest.getEmailId() == "") {
				return new Response("error", "last name not entered");
			}
			if(toUser == null)
			{
				return new Response("error", "user email id not correct");
			}
			if (!toUser.getFname().equalsIgnoreCase(userRequest.getFname()) || !toUser.getLname().equalsIgnoreCase(userRequest.getLname())) {
				return new Response("error", "user details not correct");
			}
			return new Response("success", "");
			
		} catch (Exception e) {
			return new Response("error",
					"Exception occurred. Could not complete request");
		}
	}
}
