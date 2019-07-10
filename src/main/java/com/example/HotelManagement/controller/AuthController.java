package com.example.HotelManagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

import com.example.HotelManagement.Response.ApiResponse;
import com.example.HotelManagement.Response.JwtAuthenticationResponse;
import com.example.HotelManagement.Response.UserSummary;
import com.example.HotelManagement.common.CommonMethod;
import com.example.HotelManagement.exception.AppException;
import com.example.HotelManagement.model.BillingAddress;
import com.example.HotelManagement.model.Cart;
import com.example.HotelManagement.model.Customer;
import com.example.HotelManagement.model.Role;
import com.example.HotelManagement.model.RoleName;
import com.example.HotelManagement.model.ShippingAddress;
import com.example.HotelManagement.model.User;
import com.example.HotelManagement.repository.CustomerRepository;
import com.example.HotelManagement.repository.JwtRefreshTokenRepository;

import com.example.HotelManagement.repository.RoleRepository;
import com.example.HotelManagement.repository.UserRepository;
import com.example.HotelManagement.request.ActiveRequest;
import com.example.HotelManagement.request.CustomerRequest;
import com.example.HotelManagement.request.LoginRequest;
import com.example.HotelManagement.request.ResetPasswordRequest;
import com.example.HotelManagement.request.SignUpRequest;
import com.example.HotelManagement.security.CurrentUser;
import com.example.HotelManagement.security.JwtTokenProvider;
import com.example.HotelManagement.security.UserPrincipal;
import com.example.HotelManagement.service.EmailService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	JwtRefreshTokenRepository JwtRefreshTokenRepository;

	@Autowired
	EmailService emailService;

	@Value("${app.jwtExpirationInMs}")
	private long jwtExpirationInMs;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken(authentication);
		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		UserSummary userSummary = new UserSummary(userPrincipal.getId(), userPrincipal.getUsername(),
				userPrincipal.getName());
		userSummary.setJwtAuthenticationResponse(new JwtAuthenticationResponse(jwt));
		// return ResponseEntity.ok(userSummary);
		return new ResponseEntity<Object>(new ApiResponse(true, userSummary, "Good"), HttpStatus.OK);
		// return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		try {
			Random rnd = new Random();
			int number = rnd.nextInt(999999);

			if (userRepository.existsByUsername(signUpRequest.getUsername())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Username is already exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email already in use"),
						HttpStatus.BAD_REQUEST);
			}
			User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPassword());
			String text = "'Hi '\" " + user.getUsername() + "', \\n\\n' +\r\n"
					+ "          'You just create successfull  account. \\n\\n' +\r\n"
					+ "          'Your OTP CODE to activate account: ' + OTP + ''";

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException("User Role not set."));
			user.setIsActive(false);
			// User set userID for customer

			user.setExpireDate(CommonMethod.calculateExpiryDate(CommonMethod.EXPIRATION));
			String token = String.format("%06d", number);
			user.setToken(token);
			user.setRoles(Collections.singleton(userRole));
			userRepository.save(user);
			SimpleMailMessage confirmEmail = new SimpleMailMessage();
			confirmEmail.setFrom("support@demo.com");
			confirmEmail.setTo(signUpRequest.getEmail());
			confirmEmail.setSubject("Confirm Email");
			confirmEmail.setText(text + "  " + user.getToken());
			emailService.sendEmail(confirmEmail);
			return new ResponseEntity<Object>(new ApiResponse(true, user, "User registerd successfull"),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, "Failed"), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("customer/signup")
	public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
		try {
			Random rnd = new Random();
			int number = rnd.nextInt(999999);

			if (userRepository.existsByUsername(customerRequest.getUsername())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Username is already exists"),
						HttpStatus.BAD_REQUEST);
			}

			if (userRepository.existsByEmail(customerRequest.getEmail())) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email already in use"),
						HttpStatus.BAD_REQUEST);
			}
			User user = new User(customerRequest.getName(), customerRequest.getUsername(), customerRequest.getEmail(),
					customerRequest.getPassword());
			String text = "'Hi '\" " + user.getUsername() + "', \\n\\n' +\r\n"
					+ "          'You just create successfull  account. \\n\\n' +\r\n"
					+ "          'Your OTP CODE to activate account: ' + OTP + ''";

			user.setPassword(passwordEncoder.encode(user.getPassword()));
			Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
					.orElseThrow(() -> new AppException("User Role not set."));
			user.setIsActive(false);

			user.setExpireDate(CommonMethod.calculateExpiryDate(CommonMethod.EXPIRATION));
			String token = String.format("%06d", number);
			user.setToken(token);
			user.setRoles(Collections.singleton(userRole));
			Customer customer = new Customer();
			customer.setCustomerPhone(customerRequest.getCustomerPhone());
			customer.setUsers(user);
			customerRepository.save(customer);
			SimpleMailMessage confirmEmail = new SimpleMailMessage();
			confirmEmail.setFrom("support@demo.com");
			confirmEmail.setTo(customerRequest.getEmail());
			confirmEmail.setSubject("Confirm Email");
			confirmEmail.setText(text + "  " + user.getToken());
			emailService.sendEmail(confirmEmail);
			return new ResponseEntity<Object>(new ApiResponse(true, user, "User registerd successfull"),
					HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, ex.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/activeUser")
	public ResponseEntity<?> confirmUser(@Valid @RequestBody ActiveRequest activeRequest) {
		try {

			Optional<User> foundUser = userRepository.findByUsernameOrEmail(activeRequest.getUsernameOrEmail(),
					activeRequest.getUsernameOrEmail());

			Date date = new Date();
			if (!foundUser.isPresent()) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email not found"), HttpStatus.BAD_REQUEST);
			}
			User user = foundUser.get();
			int token = Integer.parseInt(user.getToken());
			int tokenReq = Integer.parseInt(activeRequest.getToken());
			if (token != tokenReq) {
				return new ResponseEntity<Object>(new ApiResponse(false, "invalid Token"), HttpStatus.BAD_REQUEST);
			}

			Calendar cal = Calendar.getInstance();
			if ((user.getExpireDate().getTime() - cal.getTime().getTime()) <= 0) {
				return new ResponseEntity<Object>(new ApiResponse(false, "tokenExpired"), HttpStatus.BAD_REQUEST);
			}

			user.setIsActive(true);
			user.setExpireDate(date);
			userRepository.save(user);
			return new ResponseEntity<Object>(new ApiResponse(true, user, "User actived successfull"), HttpStatus.OK);
		} catch (Exception ex) {
			return null;
		}

	}

	@PostMapping("/activeCustomer")
	public ResponseEntity<?> confirmCustomer(@Valid @RequestBody ActiveRequest activeRequest) {
		try {

			Optional<User> foundUser = userRepository.findByUsernameOrEmail(activeRequest.getUsernameOrEmail(),
					activeRequest.getUsernameOrEmail());

			Date date = new Date();
			if (!foundUser.isPresent()) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email not found"), HttpStatus.BAD_REQUEST);
			}
			User user = foundUser.get();
			int token = Integer.parseInt(user.getToken());
			int tokenReq = Integer.parseInt(activeRequest.getToken());
			if (token != tokenReq) {
				return new ResponseEntity<Object>(new ApiResponse(false, "invalid Token"), HttpStatus.BAD_REQUEST);
			}

			Calendar cal = Calendar.getInstance();
			if ((user.getExpireDate().getTime() - cal.getTime().getTime()) <= 0) {
				return new ResponseEntity<Object>(new ApiResponse(false, "tokenExpired"), HttpStatus.BAD_REQUEST);
			}

			Cart cart = new Cart();
			BillingAddress ba = new BillingAddress();
			ShippingAddress sa = new ShippingAddress();
			Customer customer = user.getCustomer();
			customer.getUsers().setIsActive(true);
			customer.setCart(cart);
			customer.setBillingAddress(ba);
			customer.setShippingAddress(sa);
			cart.setCustomer(customer);

			customer.getUsers().setExpireDate(date);
			customerRepository.save(customer);

			return new ResponseEntity<Object>(new ApiResponse(true, null, "Customer actived successfull"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return null;
		}

	}

	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestParam String email) {
		try {

			Random rnd = new Random();
			int number = rnd.nextInt(999999);
			Optional<User> foundUser = userRepository.findByEmail(email);
			if (!foundUser.isPresent()) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email is not found"), HttpStatus.BAD_REQUEST);
			}

			User user = foundUser.get();
			user.setExpireDate(CommonMethod.calculateExpiryDate(CommonMethod.EXPIRATION));
			String token = String.format("%06d", number);
			user.setToken(token);
			userRepository.save(user);

			SimpleMailMessage confirmEmail = new SimpleMailMessage();
			confirmEmail.setFrom("support@demo.com");
			confirmEmail.setTo(email);
			confirmEmail.setSubject("Confirm Email");
			confirmEmail.setText("OTP to reset password" + "  " + user.getToken());
			emailService.sendEmail(confirmEmail);

			return new ResponseEntity<Object>(new ApiResponse(true, user, "Send Email successful"), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, "Failed"), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PutMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
		try {
			Optional<User> foundUser = userRepository.findByEmail(resetPasswordRequest.getEmail());

			Date date = new Date();
			if (!foundUser.isPresent()) {
				return new ResponseEntity<Object>(new ApiResponse(false, "Email not found"), HttpStatus.BAD_REQUEST);
			}
			User user = foundUser.get();
			int token = Integer.parseInt(user.getToken());
			int tokenReq = Integer.parseInt(resetPasswordRequest.getToken());
			if (token != tokenReq) {
				return new ResponseEntity<Object>(new ApiResponse(false, "invalid Token"), HttpStatus.BAD_REQUEST);
			}

			Calendar cal = Calendar.getInstance();
			if ((user.getExpireDate().getTime() - cal.getTime().getTime()) <= 0) {
				return new ResponseEntity<Object>(new ApiResponse(false, "tokenExpired"), HttpStatus.BAD_REQUEST);
			}

			user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
			user.setExpireDate(date);
			userRepository.save(user);
			return new ResponseEntity<Object>(new ApiResponse(true, user, "Change Password successfully"),
					HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<Object>(new ApiResponse(false, null, "Failed"), HttpStatus.EXPECTATION_FAILED);
		}
	}

	@GetMapping("/user/me")
	// @PreAuthorize("hasRole('USER')")
	public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
		UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(),
				currentUser.getName());
		return userSummary;
	}
}
