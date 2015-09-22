package com.webapp.validator;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.webapp.command.MemberCommand;

public class MemberCommandValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberCommand.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		MemberCommand member = (MemberCommand) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "required");
		
		/*
		 * Email Length Check 64 이하 StringUtils.
		 */
		if (member.getEmail().matches("^.{65,}$")) {
			errors.rejectValue("email", "length", new Object[] {64}, null);
		}
		
		/*
		 * Email Pattern Check xxx@yyy.co.kr
		 * Grouping ()
		 */
		//String pattern = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]+\\.[A-Za-z]{2,3}$";
		String pattern = "^[A-Za-z0-9_\\-]+@([A-Za-z0-9]+\\.){1,2}[A-Za-z]{2,3}$";
		if (!member.getEmail().matches(pattern)) {
			errors.rejectValue("email", "pattern");
		}
		
		/*
		 * Password Length Check 8자리 이상 64 이하 StringUtils.
		 */
		if (!member.getPassword().matches("^.{8,64}$")) {
			errors.rejectValue("password", "length", new Object[] {8, 64}, null);
		}
		
		/*
		 * Password Pattern Check minimum 8자리 이상 64 이하 StringUtils.
		 * qw12er34, qwer1234 => valid
		 * qwer => invalid
		 * 1234 => invalid
		 *  match가 아니라 find
		 * 1. 영문 & 숫자 
		 * 2. 영문 연속 3자리 이상 불가 X
		 * 3. 숫자 연속 3자리 이상 불가 X
		 * 
		 */
		if (!Pattern.compile("[A-Za-z]")
				.matcher(member.getPassword())
				.find()) //Method Chain방식
			errors.rejectValue("password", "pattern");

		if (!Pattern.compile("[0-9]")
				.matcher(member.getPassword())
				.find()) //Method Chain방식
			errors.rejectValue("password", "pattern");
		
		if (Pattern.compile("[A-Za-z]{3,}")
				.matcher(member.getPassword())
				.find() && !errors.hasFieldErrors("password")) //Method Chain방식
			errors.rejectValue("password", "pattern");
		
		if (Pattern.compile("[0-9]{3,}")
				.matcher(member.getPassword())
				.find() && !errors.hasFieldErrors("password")) //Method Chain방식
			errors.rejectValue("password", "pattern");
		/*
		Pattern.compile("[0-9]");
		Pattern.compile("[A-Za-z]{3,}");
		Pattern.compile("[0-9]{3,}");
		*/
		/*
		 * Name Length Check 12 이하 StringUtils.
		 */
		if (member.getName().matches("^.{13,}$")) {
			errors.rejectValue("name", "length", new Object[] {12}, null);
		}
		
		/*
		 * Comment Length Check 255 이하 StringUtils.
		 */
		//if (member.getComment().matches("^.{256,}$")) {
		if (member.getComment().length() > 255) {
			errors.rejectValue("comment", "length", new Object[] {255}, null);
		}
		
	}

}
