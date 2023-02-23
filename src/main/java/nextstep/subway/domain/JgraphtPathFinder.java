package nextstep.subway.domain;

import nextstep.subway.exception.PathTargetNotLinkedException;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JgraphtPathFinder implements PathFinder {

    /**
     * 다익스트라 알고리즘을 사용하여 최단 경로 정보를 조회합니다.
     *
     * @param source 출발지 역
     * @param target 도착지 역
     * @return 최단 경로 정보 반환
     */
    @Override
    public ShortestPath findShortestPath(final List<Line> lines, final Station source, final Station target) {
        WeightedMultigraph<Station, DefaultWeightedEdge> graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        drawGraph(lines, graph);

        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        try {
            return ShortestPath.from(dijkstraShortestPath.getPath(source, target));
        } catch (IllegalArgumentException e) {
            throw new PathTargetNotLinkedException();
        }
    }

    private void drawGraph(final List<Line> lines, final WeightedMultigraph<Station, DefaultWeightedEdge> graph) {
        for (Line line : lines) {
            line.getAllStations().forEach(graph::addVertex);
            line.getSections().forEach(section -> graph.setEdgeWeight(
                    graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
            );
        }
    }
}
