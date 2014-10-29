package com.onlinebanking.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.onlinebanking.helpers.Constants.TransactionType;
import com.onlinebanking.helpers.Response;
import com.onlinebanking.helpers.URLHelper;
import com.onlinebanking.helpers.ValidationHelper;
import com.onlinebanking.helpers.ValidationStatus;
import com.onlinebanking.models.User;
import com.onlinebanking.services.AccountService;
import com.onlinebanking.services.CaptchaService;
import com.onlinebanking.services.TransactionService;
import com.onlinebanking.services.UserService;

@Controller
public class UserController {
	private UserService userService;
	private CaptchaService captchaService;
	private AccountService accountService;
	private TransactionService transactionService;

	@Autowired(required = true)
	@Qualifier(value = "captchaService")
	public void setCaptchaService(CaptchaService captchaService) {
		this.captchaService = captchaService;
	}

	@Autowired(required = true)
	@Qualifier(value = "transactionService")
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Autowired(required = true)
	@Qualifier(value = "accountService")
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService ps) {
		this.userService = ps;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String handleSuccessFullAuthRequest(Model model) {
		return "login";
	}

	@RequestMapping(value = "/user/home", method = RequestMethod.GET)
	public String handleRequest(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		URLHelper.logRequest(request);
		HttpSession session = request.getSession();
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		User u = this.userService.getUserByEmailId(auth.getName());
		session.setAttribute("userId", u.getUserId());
		session.setAttribute("emailId", u.getEmailId());
		model.addAttribute("accounts",
				this.accountService.getUserAccounts(u.getUserId()));
		model.addAttribute("fname", u.getFname());
		return "user/home";
	}

	@RequestMapping(value = { "/user/*", "/user/*/*", "/user/*/*/*" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public String handleDashboardRequest(Model model,
			HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes attributes) {
		URLHelper.logRequest(request);

		HashMap<String, String> urls = URLHelper.analyseRequest(request);
		HttpSession session = request.getSession();
		ValidationStatus status;
		
		int account_id = 0;
		// Always check if the user has selected account id before going to next page.
		if (urls.get("url_3") != null
				&& !urls.get("url_3").toString().equals("")) {
			account_id = Integer.parseInt(urls.get("url_3"));
			session.setAttribute("account_id", account_id);
		} else if (session.getAttribute("account_id") == null) {
			attributes.addFlashAttribute("response", new Response(
					"error", "Please select an account to proceed!!"));
			return "redirect:/user/home";
		}
		
		account_id = (Integer) session.getAttribute("account_id");
		// Now that user has an account id check if its a valid account of user.
		status = this.userService.isValidUserAccount(account_id, session.getAttribute("userId").toString());
		
		if (!status.getStatus()) {
			attributes.addFlashAttribute("response", new Response(
					"error", status.getMessage()));
			return "redirect:/user/home";
		}
		
		// Handle all post requests
		if (URLHelper.isPOSTRequest(request)) {
			if (urls.get("url_2").toString().equals("transfer")) {
				// TODO: User name and emailId to validate receiver

				String name = request.getParameter("name").toString();
				String toEmailId = request.getParameter("emailId").toString();
				String toAccount = request.getParameter("account_to").toString();
				String fromAccount = session.getAttribute("account_id").toString();
				
				status = this.userService.isValidAccount(Integer.parseInt(toAccount));
				if(!status.getStatus()) {
					attributes.addFlashAttribute("response", new Response(
							"error", status.getMessage()));
					return "redirect:/user/transfer";
				}
				String toUserId = this.accountService.getAccountById(Integer.parseInt(toAccount)).getUser().getUserId();
				User toUser = this.userService.getUserById(toUserId);
				
				// Validating the to account & user details
				if (!toUser.getEmailId().equalsIgnoreCase(toEmailId)
						|| !toUser.getFname().concat(" " + toUser.getLname()).equals(name)) {
					attributes.addFlashAttribute("response", new Response(
							"error", "Incorrect account details!!"));
					return "redirect:/user/transfer";
				}
				
				// Validating amount
				status = ValidationHelper.validateAmount(request.getParameter("amount"));
				if (!status.getStatus()) {
					attributes.addFlashAttribute("response", new Response("error", status.getMessage()));
					return "redirect:/user/transfer";
				}
				
				double amount = Double.parseDouble(request.getParameter("amount"));
				
				if (toAccount.equals(fromAccount)) {
					attributes.addFlashAttribute("response", new Response(
							"error",
							"Cannot transfer to currently selected account!!"));
					return "redirect:/user/transfer";
				}

				this.transactionService.createTransaction(fromAccount,
						toAccount, amount, TransactionType.TRANSFER);
				attributes.addFlashAttribute("response", new Response(
						"success", "Transfer successfully!!"));
				return "redirect:/user/transfer";
			} else if (urls.get("url_2").toString().equals("credit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				int amount = Integer.parseInt(request.getParameter("amount")
						.toString());
				this.transactionService.createTransaction(fromAccount,
						fromAccount, amount, TransactionType.CREDIT);
				attributes.addFlashAttribute("response", new Response(
						"success", "Account credited successfully!!"));
				return "redirect:/user/credit";
			} else if (urls.get("url_2").toString().equals("debit")) {
				String fromAccount = session.getAttribute("account_id")
						.toString();
				int amount = Integer.parseInt(request.getParameter("amount")
						.toString());
				this.transactionService.createTransaction(fromAccount,
						fromAccount, amount, TransactionType.DEBIT);
				attributes.addFlashAttribute("response", new Response(
						"success", "Debit credited successfully!!"));
				return "redirect:/user/debit";
			} else if (urls.get("url_2").toString().equals("payment")) {
				// TODO: Accept or Decline flow.
				return "redirect:/user/payment";
			} else if (urls.get("url_2").toString().equals("authorize")) {
				// TODO: Authorize flow.
				return "redirect:/user/authorize";
			}
		}
		
		// Handle all get requests
		if (urls.get("url_2").toString().equals("transfer")) {
			model.addAttribute("contentView", "transfer");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("credit")) {
			model.addAttribute("contentView", "credit");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("debit")) {
			model.addAttribute("contentView", "debit");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("payment")) {
			// TODO: Need to decide how to store and retrieve merchant payment
			// requests
			account_id = (Integer) session.getAttribute("account_id");
			System.out.println("Payment Requests for Account: " + account_id);
			model.addAttribute("transactions", null);
			model.addAttribute("contentView", "payment");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("transactions")) {
			account_id = (Integer) session.getAttribute("account_id");
			model.addAttribute("transactions", this.transactionService
					.getAllTransactionsForAccountId(account_id));
			model.addAttribute("contentView", "transactions");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("authorize")) {
			System.out.println("Authorize Requests");
			model.addAttribute("requests", null);
			model.addAttribute("contentView", "authorize");
			return "user/template";
		} else if (urls.get("url_2").toString().equals("profile")) {
			model.addAttribute("contentView", "profile");
			model.addAttribute("user", this.userService
					.getUserByEmailId((String) session.getAttribute("emailId")));
			return "user/template";
		} else {
			attributes.addFlashAttribute("response", new Response(
					"error", "Please select an account to proceed!!"));
			return "redirect:/user/home";
		}
	}

	@RequestMapping(value = "/user/profile/edit")
	public String userEdit(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		model.addAttribute("contentView", "editprofile");
		model.addAttribute("user", this.userService
				.getUserByEmailId((String) session.getAttribute("emailId")));

		return "user/template";
	}

	// For add and update person both
	@RequestMapping(value = "/user/profile/update", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String addUserProfile(@ModelAttribute("user") User p,
			HttpServletRequest request, final RedirectAttributes attributes) {

		// get the responses from the user
		String challenge = request.getParameter("recaptcha_challenge_field");
		String uresponse = request.getParameter("recaptcha_response_field");
		String remoteAddress = request.getRemoteAddr();
		// verify Captcha
		Boolean verifyStatus = this.captchaService.verifyCaptcha(challenge,
				uresponse, remoteAddress);
		// redirect logic
		if (verifyStatus == true) {
			if (this.userService.getUserById(p.getUserId()) == null) {
				// new person, add it
				this.userService.addUser(p);
			} else {
				// existing person, call update
				this.userService.updateUser(p);
			}
		}
		// Wrong Captcha
		else {
			attributes.addFlashAttribute("response", new Response("error",
					"Wrong captcha, please try again!"));
			return "redirect:/user/profile/edit";
		}
		attributes.addFlashAttribute("response", new Response("success",
				"Profile edited successflly!"));
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		return "login";
	}

	@RequestMapping(value = "/logout")
	public String logout() {
		return "logout";
	}

	@RequestMapping(value = "/denied")
	public String denied() {
		return "denied";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String listUsers(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", this.userService.listUsers());
		return "registration";
	}

	// For add and update person both
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") User p, final RedirectAttributes attributes) {

		if (this.userService.getUserById(p.getUserId()) == null) {
			// new person, add it
			this.userService.addUser(p);
		} else {
			// existing person, call update
			this.userService.updateUser(p);
		}
		
		attributes.addFlashAttribute("response", new Response(
				"success", "Account registration successful!!"));
		return "redirect:/registration";

	}

	@RequestMapping("/remove/{id}")
	public String removeUser(@PathVariable("id") String id) {
		this.userService.removeUser(id);
		return "redirect:/registration";
	}

	@RequestMapping(value = "/header")
	public String header(HttpServletRequest request, Model model) {
		return "header";
	}
}
