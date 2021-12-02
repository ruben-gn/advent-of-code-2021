package nl.grootnibbelink.advent2021.day1;

import lombok.extern.slf4j.Slf4j;
import nl.grootnibbelink.advent2021.util.ResourceLoader;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Day1 {

    public static void main(String[] args) throws IOException {
        var input = ResourceLoader.getLines("day1/input.txt");
        var depths = input.stream()
                .map(Integer::parseInt)
                .toList();

        var naive = naive(depths);
        log.info("part1 (naive): {}", naive);

        var naive2 = naive2(depths);
        log.info("part2 (naive): {}", naive2);
    }

    private static int naive2(List<Integer> depths) {
        var total = 0;

        for (int i = 3; i < depths.size(); i++) {
            var g1 = sum(depths.subList(i - 3, i));
            var g2 = sum(depths.subList(i - 2, i + 1));

            if (g1 < g2) {
                total++;
            }
        }

        return total;
    }

    private static int sum(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    private static int naive(List<Integer> depths) {
        var total = 0;
        for (int i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                total++;
            }
        }
        return total;
    }
}
