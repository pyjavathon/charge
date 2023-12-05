package com.evstation.charge.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.evstation.charge.config.auth.User;

@Component
public class ValidationGroups {

	public interface NotEmptyCheckGroup {};
	public interface PatternCheckGroup {};
	public interface SizeCheckGroup {};

}
