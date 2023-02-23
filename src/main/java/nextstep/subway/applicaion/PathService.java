package nextstep.subway.applicaion;

import nextstep.subway.applicaion.dto.PathResponse;
import nextstep.subway.domain.Line;
import nextstep.subway.domain.PathFinder;
import nextstep.subway.domain.Station;
import nextstep.subway.exception.PathOriginSameAsTargetException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathService {

    private final LineService lineService;
    private final StationService stationService;
    private final PathFinder pathFinder;

    public PathService(final LineService lineService, final StationService stationService, final PathFinder pathFinder) {
        this.lineService = lineService;
        this.stationService = stationService;
        this.pathFinder = pathFinder;
    }

    public PathResponse findShortestPath(final Long sourceId, final Long targetId) {
        if (sourceId.equals(targetId)) {
            throw new PathOriginSameAsTargetException();
        }

        Station sourceStation = stationService.findById(sourceId);
        Station targetStation = stationService.findById(targetId);
        List<Line> lines = lineService.findAllLines();

        return PathResponse.from(pathFinder.findShortestPath(lines, sourceStation, targetStation));
    }
}
