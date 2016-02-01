package com.bits.protocolanalyzer.analyzer;

import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

import org.apache.commons.math3.stat.StatUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.google.common.math.DoubleMath;

/**
 * 
 * @author crygnus
 *
 */

@Getter
@Service
@Configurable
@Log4j
public class TimeSeriesAnalyzer {

    private Timestamp meanTimeofArrival = new Timestamp(0);
    private Timestamp standardDeviation = new Timestamp(0);
    private long packetCount;

    public void updateStats(List<PacketWrapper> packetWrappers) {

        double[] packetTimestamps = new double[packetWrappers.size()];
        this.packetCount = 0L;
        for (PacketWrapper packetWrapper : packetWrappers) {

            packetTimestamps[(int) this.getPacketCount()] = (double) packetWrapper
                    .getPacketTimestamp().getTime();
            this.increasePacketCount();
        }

        double mean = StatUtils.mean(packetTimestamps);
        this.meanTimeofArrival.setTime(DoubleMath.roundToLong(mean,
                RoundingMode.HALF_EVEN));

        double standardDeviation = Math.sqrt(StatUtils.populationVariance(
                packetTimestamps, mean));

        this.standardDeviation.setTime(DoubleMath.roundToLong(
                standardDeviation, RoundingMode.HALF_EVEN));
    }

    private void increasePacketCount() {
        this.packetCount++;
    }

}
