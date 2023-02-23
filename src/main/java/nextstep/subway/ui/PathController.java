package nextstep.subway.ui;

import nextstep.subway.applicaion.PathService;
import nextstep.subway.applicaion.dto.PathResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paths")
public class PathController {

    private final PathService pathService;

    public PathController(final PathService pathService) {
        this.pathService = pathService;
    }

    @GetMapping
    ResponseEntity<PathResponse> findShortestPath(
            @RequestParam(value = "source") final Long sourceId,
            @RequestParam(value = "target") final Long targetId
    ) {
        return ResponseEntity.ok().body(pathService.findShortestPath(sourceId, targetId));
    }
}
