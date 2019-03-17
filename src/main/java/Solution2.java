import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

class Solution2 {

    private static Map<String, Integer> daysOfWeek = new HashMap<>();

    static {
        daysOfWeek.put("Mon", 1);
        daysOfWeek.put("Tue", 2);
        daysOfWeek.put("Wed", 3);
        daysOfWeek.put("Thu", 4);
        daysOfWeek.put("Fri", 5);
        daysOfWeek.put("Sat", 6);
        daysOfWeek.put("Sun", 7);
    }

    public int solution(String S) {
        Optional.ofNullable(S).orElseThrow(() -> new IllegalArgumentException("S cannot be null"));
        final List<Meeting> meetings = Arrays.stream(S.split("\n")).map(Meeting::new).sorted().collect(Collectors.toList());

        final LocalDateTime weekBegin = LocalDateTime.now()
                .with(ChronoField.DAY_OF_WEEK, 1).toLocalDate().atStartOfDay();
        final LocalDateTime weekEnd = LocalDateTime.now()
                .with(ChronoField.DAY_OF_WEEK, 7).withHour(23).withMinute(59).withSecond(0).withNano(0);

        // if no meetings have all week
        if (meetings.isEmpty()) return (int) ChronoUnit.MINUTES.between(weekBegin, weekEnd) + 1;

        // week begin to first meeting
        long maxInterval = ChronoUnit.MINUTES.between(weekBegin, meetings.get(0).begin) - 1;

        // if just one metting, check its end to week end
        if (meetings.size() == 1) return (int) Math.max(maxInterval, ChronoUnit.MINUTES.between(meetings.get(0).end, weekEnd) + 1);

        // N meetings, so scan between them
        final int limit = meetings.size() - 1;
        for (int i = 0; i < limit; i++) {
            maxInterval = Math.max(maxInterval, ChronoUnit.MINUTES.between(meetings.get(i).end, meetings.get(i + 1).begin) - 1);
        }

        // last meeting to week end
        maxInterval = Math.max(maxInterval, ChronoUnit.MINUTES.between(meetings.get(limit).end, weekEnd)) + 1;

        return (int) maxInterval;
    }

    private class Meeting implements Comparable {
        final String original;
        final LocalDateTime begin;
        final LocalDateTime end;

        Meeting(final String original) {
            this.original = original;
            try {
                final int dayOfWeek = daysOfWeek.get(original.substring(0, 3));

                final int beginHour = Integer.parseInt(original.substring(4, 6));
                final int beginMinute = Math.min(59, Integer.parseInt(original.substring(7, 9)));

                int endHour = Integer.parseInt(original.substring(10, 12));
                int endMinute = Math.min(59, Integer.parseInt(original.substring(13, 15)));
                // TODO: fix hour 24, this approach was due running out of assignment time
                if (endHour > 23) {
                    endHour = 23;
                    endMinute = 59;
                }

                begin = LocalDateTime.now().with(ChronoField.DAY_OF_WEEK, dayOfWeek)
                        .withHour(beginHour)
                        .withMinute(beginMinute)
                        .withSecond(0).withNano(0);

                end = LocalDateTime.now().with(ChronoField.DAY_OF_WEEK, dayOfWeek)
                        .withHour(endHour)
                        .withMinute(endMinute)
                        .withSecond(0).withNano(0);
            } catch (DateTimeException | NullPointerException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("Invalid format: " + original, e);
            }
        }

        @Override
        public String toString() {
            return String.format("\n%s %s %s", original, begin, end);
        }

        @Override
        public int compareTo(Object o) {
            return (o instanceof Meeting ? this.begin.compareTo(((Meeting) o).begin) : 0);
        }
    }

}