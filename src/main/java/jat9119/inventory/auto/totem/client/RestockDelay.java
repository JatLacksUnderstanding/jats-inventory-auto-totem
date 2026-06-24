package jat9119.inventory.auto.totem.client;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RestockDelay {
    private static final Random random = new Random();
    public static int tickDelay;

    public boolean shouldWait() {
        if (tickDelay > 0) {
            tickDelay--;
            return true;
        }
        return false;
    }

    public int reset() {
        if (Settings.mixedDistributionDelayEnabled) {
            tickDelay = RestockDelay.mixedDistributionDelay(Settings.minDelayTicks, Settings.maxDelayTicks);
        } else {
            if (Settings.maxDelayTicks > 1) {
                tickDelay = ThreadLocalRandom.current().nextInt(Settings.minDelayTicks, Settings.maxDelayTicks);
            } else {
                tickDelay = 1;
            }
        }
        return tickDelay;
    }
    public static int mixedDistributionDelay(Integer min, Integer max) {
        int lo = min, hi = max;
        if (hi <= lo) return lo;

        double roll = random.nextDouble();

        if (roll < 0.15) {
            return lo;
        } else if (roll < 0.25) {
            return hi + 1 + random.nextInt(Math.max(1, (hi - lo) * 2));
        } else {
            double u1 = 1.0 - random.nextDouble();
            double u2 = 1.0 - random.nextDouble();
            double gaussian = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);
            double mid = (lo + hi) / 2.0;
            double sigma = (hi - lo) / 3.0;
            int result = (int) Math.round(mid + gaussian * sigma);
            return Math.max(lo, Math.min(hi, result));
        }
    }
}
