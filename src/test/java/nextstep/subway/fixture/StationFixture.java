package nextstep.subway.fixture;


import nextstep.subway.domain.Station;

import java.util.HashMap;
import java.util.Map;

import static nextstep.subway.fixture.FieldFixture.역_이름;

public enum StationFixture {
    강남역,
    역삼역,
    선릉역,
    서울대입구역,
    범계역,
    ;

    public String 역_이름() {
        return name();
    }

    public Map<String, String> 요청_데이터_생성() {
        Map<String, String> params = new HashMap<>();
        params.put(역_이름.필드명(), 역_이름());
        return params;
    }

    public Station 엔티티_생성() {
        return new Station(역_이름());
    }
}