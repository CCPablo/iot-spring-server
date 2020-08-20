package internal.service;

import lombok.Data;

@Data
public class PeriodData {
    private Long originOfPeriod;
    private Long endOfPeriod;
    private Long intervalDuration;
    private Integer numberOfIntervals;

    public PeriodData (Long intervalDuration, Integer numberOfIntervals) {
        this.intervalDuration = intervalDuration;
        this.numberOfIntervals = numberOfIntervals;
        this.endOfPeriod = System.currentTimeMillis();
        this.originOfPeriod = this.endOfPeriod - intervalDuration*numberOfIntervals;
    }

    public PeriodData (Integer numberOfIntervals, Long originOfPeriod, Long endOfPeriod) {
        this.numberOfIntervals = numberOfIntervals;
        this.originOfPeriod = originOfPeriod;
        this.endOfPeriod = endOfPeriod;
        this.intervalDuration = getPeriodDuration()/numberOfIntervals;
    }

    public Long getPeriodDuration() {
        return this.endOfPeriod - this.originOfPeriod;
    }

    public boolean includesTimeValue(Long value) {
        return  originOfPeriod <= value && value < endOfPeriod;
    }

    public Integer getIntervalIndex(Long value) {
        return (int) (((double)getPeriodDuration() - ((double)endOfPeriod - (double)value)) / ( (double)intervalDuration));
    }

    public Long getIntervalTimestamp(Integer index) {
        return endOfPeriod - intervalDuration*(numberOfIntervals-index);
    }
}
