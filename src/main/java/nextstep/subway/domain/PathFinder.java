package nextstep.subway.domain;

import java.util.List;

public interface PathFinder {
    ShortestPath findShortestPath(List<Line> lines, Station source, Station target);
}
