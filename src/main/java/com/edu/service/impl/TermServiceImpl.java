package com.edu.service.impl;

import com.edu.entity.Term;
import com.edu.mapper.TermMapper;
import com.edu.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TermServiceImpl implements TermService {
    @Autowired
    private TermMapper termMapper;

    @Override
    public List<Term> getAllTerm() {
        return termMapper.getAllTerm();
    }

    @Override
    public void setThisTerm(Integer termId) {
       List<Term> terms = termMapper.getAllTerm();
        for (Term term : terms) {
            term.setTermStatus(0);
            termMapper.updateById(term);
        }
        Term term = termMapper.selectTermById(termId);
        term.setTermStatus(1);
        termMapper.updateById(term);
    }

    @Override
    public Term selectTermById(Integer termId) {
        return termMapper.selectTermById(termId);
    }
}
