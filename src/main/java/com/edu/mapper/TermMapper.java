package com.edu.mapper;

import com.edu.entity.Term;

import java.util.List;

public interface TermMapper {
    List<Term> getAllTerm();

    Term selectOne(Integer term_status);

    int updateById(Term term);

    Term selectTermById(Integer termId);
}
