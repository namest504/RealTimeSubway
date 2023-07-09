package com.lim1t.realtimesubway.dto;

import lombok.*;

import java.util.List;

public class SubwayInfoDto {

    @Getter
    @Setter
    public static class SubwayInfoResponse {
        private ErrorMessage errorMessage;
        private List<RealtimePosition> realtimePositionList;
    }

    @Getter
    @Setter
    public static class ErrorMessage {
        private int status;
        private String code;
        private String message;
        private String link;
        private String developerMessage;
        private int total;
    }

    @Getter
    @Setter
    public static class RealtimePosition {
        private Integer beginRow;
        private Integer endRow;
        private Integer curPage;
        private Integer pageRow;
        private int totalCount;
        private int rowNum;
        private int selectedCount;
        private String subwayId;
        private String subwayNm;
        private String statnId;
        private String statnNm;
        private String trainNo;
        private String lastRecptnDt;
        private String recptnDt;
        private String updnLine;
        private String statnTid;
        private String statnTnm;
        private String trainSttus;
        private String directAt;
        private String lstcarAt;
    }
}
