package com.searchengine.yjpark.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Curation {

    private Long curationSeq;
    private String curationTitle;
    private String template;
    private String templateImg;
    private String endingTxt;
    private String tag;
    private int pcHits;
    private int mHits;
    private LocalDateTime reservationDt;
    private LocalDateTime regDt;
    private LocalDateTime upDt;
    private String status;
    private String openFl;
    private String mainFl;
    private String writer;


}
