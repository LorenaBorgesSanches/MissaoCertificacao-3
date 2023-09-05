package com.notificacoes.pje.validators;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.notificacoes.pje.model.Pessoa;

public class PessoaGroupSequenceProvider implements DefaultGroupSequenceProvider<Pessoa> {

    @Override
    public List<Class<?>> getValidationGroups(Pessoa pessoa) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(Pessoa.class);

        if (pessoa != null && pessoa.getDocumento() != null) {
            if (pessoa.getDocumento().length() == 11)
                groups.add(CpfGroup.class);
            else if (pessoa.getDocumento().length() == 14)
                groups.add(CnpjGroup.class);        
        }

        return groups;
    }

}
