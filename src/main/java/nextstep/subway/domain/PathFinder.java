package nextstep.subway.domain;

import nextstep.subway.exception.PathOriginSameAsTargetException;
import nextstep.subway.exception.PathTargetNotLinkedException;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.List;

public class PathFinder {

    private final WeightedMultigraph<Station, DefaultWeightedEdge> graph;

    public PathFinder(final List<Line> lines) {
        graph = new WeightedMultigraph<>(DefaultWeightedEdge.class);
        drawGraph(lines, graph);
    }

    private void drawGraph(final List<Line> lines, final WeightedMultigraph<Station, DefaultWeightedEdge> graph) {
        for (Line line : lines) {
            line.getAllStations().forEach(graph::addVertex);
            line.getSections().forEach(section -> graph.setEdgeWeight(
                    graph.addEdge(section.getUpStation(), section.getDownStation()), section.getDistance())
            );
        }
    }

    /**
     * 다익스트라 알고리즘을 사용하여 최단 경로 정보를 조회합니다.
     *
     * @param source 출발지 역
     * @param target 도착지 역
     * @return 최단 경로 정보 반환
     */
    public ShortestPath findShortestPathWithDijkstra(final Station source, final Station target) {
        DijkstraShortestPath<Station, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        if (source.equals(target)) {
            throw new PathOriginSameAsTargetException();
        }

        try {
            return ShortestPath.from(dijkstraShortestPath.getPath(source, target));
        } catch (IllegalArgumentException e) {
            throw new PathTargetNotLinkedException();
        }
    }

    public List<GraphPath> findKShortestPath(
            final int pathCount,
            final Station source,
            final Station target
    ) {
        return new KShortestPaths(graph, pathCount).getPaths(source, target);
    }
}
