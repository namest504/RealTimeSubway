package com.lim1t.realtimesubway.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class StationInfoDto {

    /**
     * Raw Json Response
     */
    @Getter
    @Setter
    public static class StationInfoResponse {
        private ErrorMessage errorMessage;
        private List<RealtimeArrivalInfo> realtimeArrivalList;
    }

    /**
     * Raw Json Response
     */
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

    /**
     * Raw Json Response
     */
    @Getter
    @Setter
    public static class RealtimeArrivalInfo {
        private String subwayId;
        private String subwayNm;
        private String updnLine;
        private String trainLineNm;
        private String subwayHeading;
        private String statnFid;
        private String statnTid;
        private String statnId;
        private String statnNm;
        private String trainCo;
        private String trnsitCo;
        private String ordkey;
        private String subwayList;
        private String statnList;
        private String btrainSttus;
        private String barvlDt;
        private String btrainNo;
        private String bstatnId;
        private String bstatnNm;
        private String recptnDt;
        private String arvlMsg2;
        private String arvlMsg3;
        private String arvlCd;
    }
}
