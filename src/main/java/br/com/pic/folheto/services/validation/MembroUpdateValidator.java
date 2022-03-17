package br.com.pic.folheto.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.pic.folheto.controllers.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pic.folheto.dto.MembroDTO;
import br.com.pic.folheto.entidades.Membro;
import br.com.pic.folheto.respositories.MembroRepository;

public class MembroUpdateValidator implements ConstraintValidator<MembroUpdate, MembroDTO>{

	@Autowired
	private MembroRepository repo;
	
	@Override
	public void initialize(MembroUpdate ann) {
	}

	@Override
	public boolean isValid(MembroDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		Membro aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "E-mail "+ objDto.getEmail()+", já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
