package org.example.repository;

import org.example.domain.Nota;
import org.example.domain.Pair;
import org.example.validation.Validator;

public class NotaRepository extends AbstractCRUDRepository<Pair<String, String>, Nota> {
    public NotaRepository(Validator<Nota> validator) {
        super(validator);
    }
}
