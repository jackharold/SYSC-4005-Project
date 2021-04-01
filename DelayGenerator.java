import java.util.Random;

public class DelayGenerator {

    Random pureGenerator = new Random();

    // Format: [Full length, Number of Intervals, Average Value]
    final double[] component1Values = {80, 16, 10.35791};
    final double[] component2Values = {120, 16, 15.5369};
    final double[] component3Values = {110, 16, 20.63276};
    final double[] workstation1Values = {30, 15, 4.604417};
    final double[] workstation2Values = {60, 15, 11.09261};
    final double[] workstation3Values = {60, 15, 8.79558};

    public DelayGenerator() {}

    // Generate new Delay based on Delay Value Array
    public double getNewDelay(double[] delayValues){
        double pureValue = pureGenerator.nextDouble();
        double intervalLength = delayValues[0]/delayValues[1];
        
        // For each calculated interval probability, check if it contains the random value
        int intervalValue = 0;
        for (int i = 0; i < delayValues[1]; i++) {
            if (pureValue <= ( 1-Math.exp(-1/delayValues[2] * (i*intervalLength)) ) ) {
                intervalValue = i;
                break;
            }
        }

        // Return the value at the center of the selected interval
        return (intervalValue*intervalLength) + intervalLength/2;
    }

}

