package com.example.querydsldemo.curation.chains;

import com.example.querydsldemo.curation.CurationChain;
import com.example.querydsldemo.entity.QPlace;
import com.querydsl.core.BooleanBuilder;

import java.util.HashMap;
import java.util.List;

public class TagCuration implements CurationChain {
    private CurationChain nextChain;
    private List<String>tags;

    @Override
    public void setNextChain(CurationChain chain) {
        this.nextChain = chain;
    }

    @Override
    public void curation(HashMap<ChainType, Object> inputChains, BooleanBuilder whereClause) {
        if (inputChains.containsKey(ChainType.TAG)){
            this.tags = (List<String>)inputChains.get(ChainType.TAG);
            for( String s:tags){
                whereClause.and(QPlace.place.placeName.contains(s)
                        .or(QPlace.place.address.contains(s)) // 나중에는 분류된 카테고리에서도 검색되도록.
                );
            }
        }
        nextChain.curation(inputChains, whereClause);
    }
}
