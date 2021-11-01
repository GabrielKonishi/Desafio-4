package com.trilha.back.finacys.util;

import com.trilha.back.finacys.entity.Lancamento;
import com.trilha.back.finacys.response.CategoriaResponse;
import com.trilha.back.finacys.response.LancamentoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperUtil {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Lancamento.class, LancamentoResponse.class)
                .<CategoriaResponse>addMapping(src -> src.getCategoria(),
                        (dest, value) -> dest.setCategoria(value));


        return modelMapper;
    }
}
