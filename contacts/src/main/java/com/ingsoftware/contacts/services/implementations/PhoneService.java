package com.ingsoftware.contacts.services.implementations;

import com.ingsoftware.contacts.models.entities.User;
import com.ingsoftware.contacts.repositories.UserRepository;
import com.twilio.Twilio;
import com.twilio.exception.TwilioException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {

  private final UserRepository userRepository;
  private static final String TWILIO_ACCOUNT_SID = "ACfb89d0bfa9631b5439c43613d413e4dd";
  private static final String TWILIO_AUTH_TOKEN = "b2e673f2c59487d500ec216afb3d5198";

  private static final String VERIFICATION_SERVICE_ID = "VA653f706f04ea5032a28da191f2c91565";
  public PhoneService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public ResponseEntity generateVerificationCode(long tsid) {
    User user = userRepository.findByTsid(tsid);

    if (user.isPhoneVerified()) {
      return new ResponseEntity<>("That phone number has already been verified.", HttpStatus.BAD_REQUEST);
    }

    Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    String phoneNumber = user.getPhoneNumber();

    try {
    Verification verification =
        Verification.creator(VERIFICATION_SERVICE_ID, phoneNumber, "sms").create();
    } catch (Exception e) {
      return new ResponseEntity<>("Failed to send the generated code.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Code has been sent to your phone.", HttpStatus.OK);
  }

  public ResponseEntity verifyCode(long tsid, String code) {
    User user = userRepository.findByTsid(tsid);
    String phoneNumber = user.getPhoneNumber();

    Twilio.init(TWILIO_ACCOUNT_SID, TWILIO_AUTH_TOKEN);
    try {

      VerificationCheck verificationCheck =
          VerificationCheck.creator(VERIFICATION_SERVICE_ID)
              .setTo(phoneNumber)
              .setCode(code)
              .create();

      user.setPhoneVerified(true);
      userRepository.save(user);
    } catch (Exception e) {
      return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(
        "This user's phone verification has been completed successfully", HttpStatus.OK);
  }
}
