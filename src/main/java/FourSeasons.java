import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class FourSeasons {

    private static final String[] SEASONS = new String[]{"WINTER", "SPRING", "SUMMER", "AUTUMN"};

    public String solution(int[] T) {
        final int totalDays = T.length;
        final int daysPerSeason = totalDays / SEASONS.length;
        int maxAmplitude = 0;
        int seasonWithHigherAmplitude = 0;

        for (int seasonIndex = 0; seasonIndex < SEASONS.length; seasonIndex++) {
            final int startDay = seasonIndex * daysPerSeason;
            final int endDay = startDay + daysPerSeason;
            final int amplitude = getAmplitude(Arrays.copyOfRange(T, startDay, endDay));
            if (amplitude > maxAmplitude) {
                seasonWithHigherAmplitude = seasonIndex;
                maxAmplitude = amplitude;
            }
        }

        return SEASONS[seasonWithHigherAmplitude];
    }

    private int getAmplitude(int[] slice) {
        final List<Integer> ordered = Arrays.stream(slice).sorted().boxed().collect(Collectors.toList());
        final int min = ordered.stream().findFirst().orElse(0);
        final int max = ordered.stream().skip(Math.max(0, ordered.size() - 1)).findFirst().orElse(0);
        return max - min;
    }

}
