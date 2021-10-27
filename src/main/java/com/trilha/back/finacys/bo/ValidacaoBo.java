package com.trilha.back.finacys.bo;

import com.trilha.back.finacys.exception.ValidateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@Component
public class ValidacaoBo {

 

    private String STRING_CAMPO_OBRIGATORIO = "O campo {0} é obrigatório.";

 

    public void validarObrigatoriedade(String value, String campo) {
        if (StringUtils.isBlank(value)) {
            exeception(campo);
        }
    }

 

    public void validarObrigatoriedade(Enum<?> value, String campo) {
        if (value == null) {
            exeception(campo);
        }
    }


    public void validarObrigatoriedade(Long value, String campo) {
        if (value == null) {
            exeception(campo);
        }

    }
    
    public void validarObrigatoriedade(Integer value, String campo) {
        if (value == null) {
            exeception(campo);
        }
    }
    
    public void validarObrigatoriedade(LocalDateTime value, String campo) {
        if (value == null) {
            exeception(campo);
        }
    }
    
    public void validarObrigatoriedade(LocalDate value, String campo) {
    	if (value == null) {
            exeception(campo);
        }
    }
    
    public void validarObrigatoriedade(BigDecimal value, String campo) {
        if (value == null) {
            exeception(campo);
        }
    }
    
    public void validarObrigatoriedade(Collection<?> value, String campo) {
        if (value.isEmpty()) {
            exeception(campo);
        }
    }

    public void validarObrigatoriedade(byte[] value, String campo) {
        if(value == null) {
            exeception(campo);
        }
    }
    
    private void exeception(String campo) {
        throw new ValidateException(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), MessageFormat.format(STRING_CAMPO_OBRIGATORIO, campo));
    }

 

}